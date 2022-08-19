from functools import lru_cache
from pydantic import BaseSettings, PostgresDsn, conint


class Settings(BaseSettings):
    database_url: PostgresDsn
    pool_min_size: conint(ge=0) = 1
    pool_max_size: conint(gt=0) = 2
    echo_sql: bool = False                  # sets the echo parameter
    # pool_max_queries: Optional[conint(gt=0)] = None

    @property
    def db_port(self):
        port = self.database_url.port
        if port is None:
            port = 5432
        return port

    @property
    def db_name(self):
        database = 'postgres'
        path = self.database_url.path
        if path and path[0] == '/':
            path = path[1:]
        if path:
            database = path
        return database

    @property
    def db_host(self):
        return self.database_url.host

    @property
    def db_user(self):
        return self.database_url.user

    @property
    def db_password(self):
        return self.database_url.password

    class Config:
        env_file = '.env'


@lru_cache
def get_settings():
    return Settings()
