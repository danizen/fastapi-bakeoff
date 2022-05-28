#!/usr/bin/env python3
import os
import sys
from argparse import ArgumentParser, ArgumentTypeError

import uvicorn


src_path = os.path.join(__file__, 'src')
sys.path.append(src_path)


def command_runserver(opts):
    host_and_port = opts.host.split(':', maxsplit=1)
    if len(host_and_port) == 2:
        host, port = host_and_port
    else:
        host = host_and_port[0]
        port = 8000
    uvicorn.run('app:app', host=host, port=port)


def command_migrate(opts):
    print('Not yet implemented', file=sys.stderr)
    return 1


def create_parser(prog_name):
    parser = ArgumentParser(
        prog=prog_name,
        description='Run webapp and commands similar to Django'
    )

    sp = parser.add_subparsers(dest='command')

    scmd = sp.add_parser('runserver', help='Run webapp')
    scmd.set_defaults(func=command_runserver)
    scmd.add_argument('host', required=False, default='127.0.0.1:8000')

    scmd = sp.add_parser('migrate', help='Custom management command')
    scmd.set_defaults(func=command_migrate)

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

