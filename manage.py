#!/usr/bin/env python3
import asyncio
import sys
from argparse import ArgumentParser, ArgumentTypeError
from textwrap import dedent

import asyncpg
import uvicorn
from faker import Faker

from backend.config import get_settings


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
    uvicorn.run('backend.app:app', host=host, port=port)


def command_migrate(opts):
    print('Not yet implemented', file=sys.stderr)
    return 1


def make_contact(faker, contact_types):
    first_name = faker.first_name()
    last_name = faker.last_name()
    type_id = faker.random.choice(contact_types)
    if faker.random.random() < 0.4:
        phone_number = faker.phone_number()
    else:
        phone_number = None
    if faker.random.random() < 0.7:
        email = faker.email()
    else:
        email = None
    return [first_name, last_name, type_id, phone_number, email]


async def make_data(seed=0, num_contacts=10):
    settings = get_settings()
    params = settings.connect_params()
    faker = Faker(seed)
    conn = await asyncpg.connect(**params)

    sql = dedent("""\
        SELECT type_id FROM contact_types ORDER BY type_id
    """)
    contact_types = await conn.fetch(sql)

    sql = dedent("""\
        INSERT INTO contacts (
            first_name, last_name, type_id, phone_number, email
        ) VALUES (
            ($1), ($2), ($3), ($4), ($5)
        )
    """)
    for _ in range(num_contacts):
        contact_data = make_contact(faker, contact_types)
        await conn.execute(sql, *contact_data)
        print('.')


def command_makedata(opts):
    # we will support an alternative environment file later on
    asyncio.run(make_data(opts.seed, opts.contacts))
    return 1


def create_parser(prog_name):
    parser = ArgumentParser(
        prog=prog_name,
        description='Run webapp and commands similar to Django'
    )

    sp = parser.add_subparsers(dest='command')

    scmd = sp.add_parser('runserver', help='Run webapp')
    scmd.set_defaults(func=command_runserver)
    scmd.add_argument('host', default='127.0.0.1:8000')

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
