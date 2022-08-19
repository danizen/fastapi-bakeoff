from collections import defaultdict
from typing import Optional

from pydantic import conint, PositiveInt
from sqlalchemy import select, func, column
from sqlalchemy.ext.asyncio import AsyncSession

from . import orm
from .schema import (
    ContactType,
    ContactTypesResponse,
    Contact,
    ContactsResponse
)


class ContactsService:
    def __init__(self, session: AsyncSession):
        self.session = session

    async def list_types(self):
        result = await self.session.execute(
            select(orm.ContactType)
        )
        results = [
            ContactType.from_orm(ct)
            for ct in result.scalars()
        ]
        return ContactTypesResponse(
            count=len(results),
            results=results
        )

    async def list_contacts(self,
                            limit: conint(gt=0, lt=200) = 100,
                            offset: conint(ge=0) = 0,
                            starts: Optional[str] = None):

        # count stmt
        count_stmt = select(
            func.count(orm.Contact.contact_id)
        )

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
            count_stmt = count_stmt.where(
                orm.Contact.last_name.ilike(starts)
            )

        # count total results
        result = await self.session.execute(count_stmt)
        count, = result.one()

        # apply limit and offset
        stmt = stmt.limit(limit).offset(offset)

        # get and marshall results
        result = await self.session.execute(stmt)
        contacts = [
            Contact.from_orm(c) for c in result.scalars()
        ]

        # get their ids to get phone and email
        contact_ids = [
            c.contact_id for c in contacts
        ]

        # get phones
        stmt = select(
            orm.ContactPhone
        ).where(
            column('contact_id').in_(contact_ids)
        )
        result = await self.session.execute(stmt)
        phones = defaultdict(list)
        for ph in result.scalars():
            phones[ph.contact_id].append(ph.phone_number)

        # get emails
        stmt = select(
            orm.ContactEmail
        ).where(
            column('contact_id').in_(contact_ids)
        )
        result = await self.session.execute(stmt)
        emails = defaultdict(list)
        for em in result.scalars():
            emails[em.contact_id].append(em.email_address)

        # apply to contacts
        for contact in contacts:
            contact.phone_number = phones.get(contact.contact_id)
            contact.email = emails.get(contact.contact_id)

        return ContactsResponse(
            count=count,
            results=contacts
        )

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

        # get the contact
        result = await self.session.execute(stmt)
        orm_contacts = [
            Contact.from_orm(c) for c in result.scalars()
        ]
        contact = Contact.from_orm(orm_contacts[0])

        # add their phones
        stmt = select(
            orm.ContactPhone.phone_number
        ).where(
            orm.ContactPhone.contact_id == contact_id
        )
        result = await self.session.execute(stmt)
        contact.phone_number = [
            phone for phone in result.scalars()
        ]

        # add their emails
        stmt = select(
            orm.ContactEmail.email_address
        ).where(
            orm.ContactEmail.contact_id == contact_id
        )
        result = await self.session.execute(stmt)
        contact.email = [
            email for email in result.scalars()
        ]

        return contact
