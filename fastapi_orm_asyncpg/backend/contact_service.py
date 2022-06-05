from sqlalchemy.ext.asyncio import AsyncEngine
from sqlalchemy import select

from .database import contacts, contact_types
from .schema import Contact, ContactType


class ContactsService:
    def __init__(self, engine: AsyncEngine):
        self.engine = AsyncEngine

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
        async with self.engine.connect() as conn:
            result = await conn.execute(select(contact_types))
            return [ContactType(type_id=r[0], type_name=r[1]) for r in result.]

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
                ct.type_name,
                c.phone_number,
                c.email
            FROM contacts c
            JOIN contact_types ct ON (c.type_id = ct.type_id)
            {where_clause} LIMIT $1 OFFSET $2
        """)
        records = await self.conn.fetch(sql, *params)
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
