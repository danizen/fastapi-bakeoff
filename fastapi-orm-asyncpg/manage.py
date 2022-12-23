#!/usr/bin/env python3
import asyncio
import os
import sys
from argparse import ArgumentParser, ArgumentTypeError
from urllib.parse import quote_plus

import uvicorn
from sqlalchemy import (
    create_mock_engine,
    create_engine,
    insert,
    select
)
from sqlalchemy.engine.url import URL
from faker import Faker
from progress.bar import Bar

from backend.config import get_settings
from backend.orm import (
    create_async_engine,
    Base,
    ContactType,
    Contact,
    ContactPhone,
    ContactEmail
)


def positive_int_type(rawvalue):
    if rawvalue is None:
        return rawvalue
    try:
        value = int(rawvalue)
    except ValueError:
        raise ArgumentTypeError('should be a positive integer')
    if value <= 0:
        raise ArgumentTypeError('should be a positive integer')
    return value


def command_runserver(opts):
    host_and_port = opts.host.split(':', maxsplit=1)
    if len(host_and_port) == 2:
        host, port = host_and_port
        port = int(port)
    else:
        host = host_and_port[0]
        port = 8000
    if opts.reload:
        uvicorn.run('backend.app:app', host=host, port=port, reload=True)
    else:
        uvicorn.run('backend.app:app', host=host, port=port, workers=5)


def command_sqlmigrate(opts):
    engine = create_mock_engine(
        'postgresql://localhost/mock',
        executor=lambda sql: print(sql.compile(dialect=engine.dialect))
    )
    Base.metadata.create_all(engine)


def command_migrate(opts):
    settings = get_settings()
    host_or_path = settings.pgdb_host
    # SQL Alchemy decision to make it all an URL combined with CloudRun
    # decision to use domain sockets means we get this.
    query = {
        'host': host_or_path
    }
    url = URL.create(
        drivername='postgresql',
        username=settings.pgdb_username,
        password=settings.pgdb_password,
        database=settings.pgdb_database,
        query=query
    )
    engine = create_engine(url)
    Base.metadata.create_all(engine)
    with engine.connect() as conn:
        with conn.begin() as transaction:
            for name in ['Friends', 'Relatives', 'Coworkers']:
                conn.execute(
                    insert(ContactType).values(type_name=name)
                )
            transaction.commit()


async def make_data(seed=0, num_contacts=10):
    settings = get_settings()
    engine = create_async_engine(settings)
    Faker.seed(seed)
    faker = Faker()
    bar = Bar(max=num_contacts)

    # get the contact_types
    async with engine.connect() as conn:
        result = await conn.execute(
            select(ContactType.type_id)
        )
        contact_types = [row[0] for row in result.all()]

    async with engine.connect() as conn:
        for _ in range(num_contacts):
            async with conn.begin() as transaction:
                # make contact
                stmt = insert(Contact).values(
                    first_name=faker.first_name(),
                    last_name=faker.last_name(),
                    type_id=faker.random.choice(contact_types)
                )
                result = await conn.execute(stmt)
                contact_id = result.inserted_primary_key[0]

                # make phone numbers
                chance = 0.4
                while faker.random.random() < chance:
                    stmt = insert(ContactPhone).values(
                        phone_number=faker.phone_number(),
                        contact_id=contact_id
                    )
                    await conn.execute(stmt)
                    chance = 0.2

                # make emails
                chance = 0.7
                if faker.random.random() < chance:
                    stmt = insert(ContactEmail).values(
                        email_address=faker.email(),
                        contact_id=contact_id
                    )
                    await conn.execute(stmt)
                    chance = 0.3

                # commit this contact
                await transaction.commit()
                bar.next()
    bar.finish()
    await engine.dispose()


def command_makedata(opts):
    # we will support an alternative environment file later on
    asyncio.run(make_data(opts.seed, opts.contacts))
    return 0


def create_parser(prog_name):
    parser = ArgumentParser(
        prog=prog_name,
        description='Run webapp and commands similar to Django'
    )

    sp = parser.add_subparsers(dest='command')

    scmd = sp.add_parser('runserver', help='Run webapp')
    scmd.set_defaults(func=command_runserver)
    scmd.add_argument('host', default='127.0.0.1:8000')
    scmd.add_argument('--reload', action='store_true', default=False,
                      help='Run application in development mode')

    scmd = sp.add_parser('sqlmigrate', help='Print SQL DDL via SQL Alchemy')
    scmd.set_defaults(func=command_sqlmigrate)

    scmd = sp.add_parser('migrate', help='Custom management command')
    scmd.set_defaults(func=command_migrate)

    scmd = sp.add_parser('makedata', help='Make sample data')
    scmd.set_defaults(func=command_makedata)
    scmd.add_argument('--env', metavar='PATH', default=None,
                      help='Path to environment file')
    scmd.add_argument('--contacts', metavar='COUNT',
                      type=positive_int_type, default=100,
                      help='How many contacts to make')
    scmd.add_argument('--seed', metavar='SEED',
                      type=positive_int_type, default=None,
                      help='Seed for random number generator')

    return parser


def main(args=None):
    if args is None:
        args = sys.argv
    parser = create_parser(args[0])
    opts = parser.parse_args(args[1:])
    if not hasattr(opts, 'func'):
        parser.print_help()
        raise SystemExit(1)
    rc = (opts.func)(opts)
    if rc:
        raise SystemExit(int(rc))


if __name__ == '__main__':
    main()
