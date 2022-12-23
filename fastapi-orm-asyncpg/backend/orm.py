from sqlalchemy import (
    Column,
    ForeignKey,
    Integer,
    String
)
from sqlalchemy.engine.url import URL
from sqlalchemy.orm import (
    declarative_base,
    relationship,
    sessionmaker
)

from sqlalchemy.ext.asyncio import (
    AsyncEngine,
    AsyncSession,
    create_async_engine as base_create_async_engine
)

from .config import Settings


def create_async_engine(settings: Settings):
    url = URL.create(
        drivername='postgresql+asyncpg',
        username=settings.pgdb_username,
        password=settings.pgdb_password,
        database=settings.pgdb_database,
        query={
            'host': settings.pgdb_host
        }
    )
    return base_create_async_engine(
        url,
        echo=settings.echo_sql,
        # pool_size=settings.pool_min_size,
        # max_overflow=settings.pool_max_size - settings.pool_min_size
    )


def create_session_maker(engine: AsyncEngine):
    return sessionmaker(
        engine,
        expire_on_commit=False,
        class_=AsyncSession
    )


Base = declarative_base()


class ContactType(Base):
    __tablename__ = 'contact_types'

    type_id = Column(Integer, primary_key=True)
    type_name = Column(String(30), nullable=False)


class Contact(Base):
    __tablename__ = 'contacts'

    # needed because of async - this means it will load
    # all relationships, causing all the problems you
    # expect from an ORM.
    __mapper_args__ = {"eager_defaults": True}

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
