from sqlalchemy.ext import asyncio as sqlalchemy_async
from .config import Settings


def create_async_engine(settings: Settings):
    dsn = 'postgresql+asyncpg://{user}:{password}@{host}:{port}/{name}'
    dsn = dsn.format(
        user=settings.db_user,
        password=settings.db_password,
        host=settings.db_host,
        port=settings.db_port,
        name=settings.db_name
    )
    return sqlalchemy_async.create_async_engine(
        dsn,
        echo=settings.echo_sql,
        # pool_size=settings.pool_min_size,
        # max_overflow=settings.pool_max_size - settings.pool_min_size
    )
