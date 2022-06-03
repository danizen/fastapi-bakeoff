from textwrap import dedent

import asyncpg
from pydantic import NonNegativeInt, conint

from .schema import Contact, ContactType


class ContactsService:
    def __init__(self, connection: asyncpg.Connection):
        self.conn = connection

    @staticmethod
    def marshall(record: asyncpg.Record):
        contact_type = ContactType(
            type_id=record['type_id'],
            type_name=record['type_name']
        )
        contact = Contact(
            contact_id=record['contact_id'],
            first_name=record['first_name'],
            last_name=record['last_name'],
            contact_type=contact_type,
            phone_number=record['phone_number'],
            email=record['email']
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
                            offset: conint(ge=0) = 0):
        sql = dedent("""\
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
            LIMIT $1 OFFSET $2
        """)
        records = await self.conn.fetch(sql, limit, offset)
        return [self.marshall(record) for record in records]

    async def get_contact(self,
                          contact_id: NonNegativeInt):
        # Because of the join, there's a bit of marshalling here
        sql = dedent("""\
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
            WHERE c.contact_id = $1
        """)
        record = await self.conn.fetchrow(sql, contact_id)
        return self.marshall(record)
