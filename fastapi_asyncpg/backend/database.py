from collections import defaultdict
from textwrap import dedent
from typing import Optional

import asyncpg
from pydantic import NonNegativeInt, conint

from .schema import (
    Contact,
    ContactsResponse,
    ContactType,
    ContactTypesResponse,
)


class ContactsService:
    def __init__(self, connection: asyncpg.Connection):
        self.conn = connection

    @staticmethod
    def marshall(record: asyncpg.Record,
                 phones: Optional[dict] = None,
                 emails: Optional[dict] = None):
        contact_type = ContactType(
            type_id=record['type_id'],
            type_name=record['type_name']
        )
        contact = Contact.construct(
            contact_id=record['contact_id'],
            first_name=record['first_name'],
            last_name=record['last_name'],
            contact_type=contact_type
        )
        if phones:
            contact.phone_number = phones.get(record['contact_id'])
        if emails:
            contact.email = emails.get(record['contact_id'])
        return contact

    async def list_types(self):
        sql = dedent("""\
            SELECT type_id, type_name FROM contact_types ORDER BY type_id
        """)
        records = await self.conn.fetch(sql)
        return ContactTypesResponse.construct(
            count=len(records),
            results=[
                ContactType(type_id=r[0], type_name=r[1])
                for r in records
            ]
        )

    async def fetch_contacts(self,
                             limit: conint(gt=0, lt=200) = 100,
                             offset: conint(ge=0) = 0,
                             starts: Optional[str] = None):
        where_clause = ''
        params = [limit, offset]
        count_params = [1, 0]
        if starts:
            like = starts.lower() + '%'
            params.append(like)
            count_params.append(like)
            where_clause = "WHERE lower(c.last_name) LIKE $3"
        async with self.conn.transaction():
            sql = dedent(f"""\
                SELECT COUNT(*) 
                FROM contacts c
                {where_clause} LIMIT $1 OFFSET $2
            """)
            record = await self.conn.fetchrow(sql, *count_params)
            count = record[0]
            sql = dedent(f"""\
                SELECT
                    c.contact_id,
                    c.first_name,
                    c.last_name,
                    ct.type_id,
                    ct.type_name
                FROM contacts c
                JOIN contact_types ct ON (c.type_id = ct.type_id)
                {where_clause} LIMIT $1 OFFSET $2
            """)
            records = await self.conn.fetch(sql, *params)
            in_clause = ', '.join(str(r['contact_id']) for r in records)

            sql = dedent(f"""\
                SELECT contact_id, phone_number
                FROM contact_phones
                WHERE contact_id = ANY(ARRAY[{in_clause}])
            """)
            phone_recs = await self.conn.fetch(sql)
            phones = defaultdict(list)
            for contact_id, phone_number in phone_recs:
                phones[contact_id].append(phone_number)

            sql = dedent(f"""\
                SELECT contact_id, email_address
                FROM contact_emails
                WHERE contact_id = ANY(ARRAY[{in_clause}])
            """)
            email_recs = await self.conn.fetch(sql)
            emails = defaultdict(list)
            for contact_id, email_address in email_recs:
                emails[contact_id].append(email_address)

            return ContactsResponse.construct(
                count=count,
                results=[
                    self.marshall(record, phones, emails) 
                    for record in records
                ]
            )

    async def get_contact(self,
                          contact_id: NonNegativeInt):
        # Because of the join, there's a bit of marshalling here
        sql = dedent("""\
            SELECT
                c.contact_id,
                c.first_name,
                c.last_name,
                ct.type_id,
                ct.type_name
            FROM contacts c
            JOIN contact_types ct ON (c.type_id = ct.type_id)
            WHERE c.contact_id = $1
        """)
        record = await self.conn.fetchrow(sql, contact_id)
        if record is None:
            return None

        sql = dedent("""\
            SELECT phone_number
            FROM contact_phones
            WHERE contact_id = $1
        """)
        records = await self.conn.fetch(sql, contact_id)
        phones = [rec[0] for rec in records]

        sql = dedent("""\
            SELECT email_address
            FROM contact_emails
            WHERE contact_id = $1
        """)
        records = await self.conn.fetch(sql, contact_id)
        emails = [rec[0] for rec in records]

        contact = self.marshall(record)
        contact.phone_number = phones if phones else None
        contact.email = emails if emails else None
        return contact
