from typing import Optional

from pydantic import conint, PositiveInt
from sqlalchemy import select, func
from sqlalchemy.ext.asyncio import AsyncSession

from . import orm
from .schema import ContactType, Contact


class ContactsService:
    def __init__(self, session: AsyncSession):
        self.session = session

    async def list_types(self):
        result = await self.session.execute(
            select(orm.ContactType)
        )
        return [
            ContactType.from_orm(ct)
            for ct in result.scalars()
        ]

    async def list_contacts(self,
                            limit: conint(gt=0, lt=200) = 100,
                            offset: conint(ge=0) = 0,
                            starts: Optional[str] = None):
        # NOTE - how much time did I spend on SQL Alchemy
        # to learn to deal with the ORM like this?

        # basic select
        stmt = select(
            orm.Contact, orm.ContactType
        ).order_by(
            orm.Contact.contact_id
        ).join(
            orm.ContactType
        )

        # add where clause
        if starts:
            starts = starts.lower() + '%'
            stmt = stmt.where(
                orm.Contact.last_name.ilike(starts)
            )

        # apply limit and offset
        stmt = stmt.limit(limit).offset(offset)

        # get and marshall results
        result = await self.session.execute(stmt)
        return [
            Contact.from_orm(c) for c in result.scalars()
        ]

    async def get_contact(self,
                          contact_id: PositiveInt):
        # basic select
        stmt = select(
            orm.Contact, orm.ContactType
        ).join(
            orm.ContactType
        ).where(
            orm.Contact.contact_id == contact_id
        )

        # get and marshall results
        result = await self.session.execute(stmt)
        return Contact.from_orm(result.scalars().first())
