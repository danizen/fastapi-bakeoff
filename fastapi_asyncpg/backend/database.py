import asyncio
from collections import defaultdict
from textwrap import dedent
from typing import Optional

import asyncpg
from pydantic import NonNegativeInt, conint

from .schema import Contact, ContactType


class ContactsService:
    def __init__(self, connection: asyncpg.Connection):
        self.conn = connection

    @staticmethod
    def marshall(record: asyncpg.Record, phones: dict, emails: dict):
        contact_type = ContactType(
            type_id=record['type_id'],
            type_name=record['type_name']
        )
        contact = Contact(
            contact_id=record['contact_id'],
            first_name=record['first_name'],
            last_name=record['last_name'],
            contact_type=contact_type,
            phone_number=phones.get(record['contact_id']),
            email=emails.get(record['contact_id'])
        )
        return contact

    async def list_types(self):
        sql = dedent("""\
            SELECT type_id, type_name FROM contact_types ORDER BY type_id
        """)
        records = await self.conn.fetch(sql)
        return [ContactType(type_id=r[0], type_name=r[1]) for r in records]

    async def list_contacts(self,
                            limit: conint(gt=0, lt=200) = 100,
                            offset: conint(ge=0) = 0,
                            starts: Optional[str] = None):
        where_clause = ''
        params = [limit, offset]
        if starts:
            params.append(starts.lower() + '%')
            where_clause = "WHERE lower(c.last_name) LIKE $3"
        sql = dedent(f"""\
            SELECT
                c.contact_id,
                c.first_name,
                c.last_name,
                ct.type_id,
                ct.type_name,
                c.phone_number,
                c.email
            FROM contacts c
            JOIN contact_types ct ON (c.type_id = ct.type_id)
            {where_clause} LIMIT $1 OFFSET $2
        """)
        async with self.conn.transaction():
            results = []
            async for record in self.conn.cursor(sql, *params):
                results.append(self.marshall(record))
            return results

    async def fetch_contacts(self,
                             limit: conint(gt=0, lt=200) = 100,
                             offset: conint(ge=0) = 0,
                             starts: Optional[str] = None):
        where_clause = ''
        params = [limit, offset]
        if starts:
            params.append(starts.lower() + '%')
            where_clause = "WHERE lower(c.last_name) LIKE $3"
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

        return [
            self.marshall(record, phones, emails) 
            for record in records
        ]

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

        phone_sql = dedent("""\
            SELECT phone_number
            FROM contact_phones
            WHERE contact_id = $1
        """)

        email_sql = dedent("""\
            SELECT email_address
            FROM contact_emails
            WHERE contact_id = $1
        """)

        results = await asyncio.gather([
            self.conf.fetchrows(sql, contact_id),
            self.conf.fetchrows(phone_sql, contact_id),
            self.conn.fetchrows(email_sql, contact_id)
        ])
        contact = self.marshall(record)
        contact.emails = []
