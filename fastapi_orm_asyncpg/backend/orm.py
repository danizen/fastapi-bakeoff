from sqlalchemy import (
    Column,
    ForeignKey,
    Integer,
    String
)
from sqlalchemy.orm import (
    declarative_base,
    relationship
)

from sqlalchemy.ext.asyncio import create_async_engine

from .config import Settings


def create_engine(settings: Settings):
    dsn_format = 'postgresql+asyncpg://{user}:{password}@{host}:{port}/{name}'
    dsn = dsn_format.format(
        user=settings.db_user,
        password=settings.db_password,
        host=settings.db_host,
        port=settings.db_port,
        name=settings.db_name
    )
    return create_async_engine(
        dsn,
        echo=settings.echo_sql,
        # pool_size=settings.pool_min_size,
        # max_overflow=settings.pool_max_size - settings.pool_min_size
    )


Base = declarative_base()


class ContactType(Base):
    __tablename__ = 'contact_types'

    type_id = Column(Integer, primary_key=True)
    type_name = Column(String(30), nullable=False)


class Contact(Base):
    __tablename__ = 'contacts'

    contact_id = Column(Integer, primary_key=True)
    first_name = Column(String(100), nullable=False)
    last_name = Column(String(100), nullable=False)
    type_id = Column(
        ForeignKey('contact_types.type_id'),
        nullable=False
    )

    contact_type = relationship('ContactType')
    phones = relationship('ContactPhone')
    emails = relationship('ContactEmail')


class ContactPhone(Base):
    __tablename__ = 'contact_phones'

    phone_id = Column(Integer, primary_key=True)
    phone_number = Column(String(30), nullable=False)
    contact_id = Column(
        Integer,
        ForeignKey('contacts.contact_id', ondelete='CASCADE'),
        nullable=False
    )


class ContactEmail(Base):
    __tablename__ = 'contact_emails'

    email_id = Column(Integer, primary_key=True)
    email_address = Column(String(256), nullable=False)
    contact_id = Column(
        Integer,
        ForeignKey('contacts.contact_id', ondelete='CASCADE'),
        nullable=False
    )
