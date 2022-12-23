from functools import lru_cache
from typing import Optional
from pydantic import BaseSettings, conint


class Settings(BaseSettings):
    pgdb_host: str
    pgdb_database: Optional[str]
    pgdb_port: Optional[conint(gt=0)]
    pgdb_username: str
    pgdb_password: str
    pool_min_size: conint(ge=0) = 0
    pool_max_size: conint(gt=0) = 2
    # pool_max_queries: Optional[conint(gt=0)] = None

    def connect_params(self):
        port = self.pgdb_port
        if port is None:
            port = 5432
        database = self.pgdb_database
        if database is None:
            database = 'postgres'
        return {
            'host': self.pgdb_host,
            'port': port,
            'database': database,
            'user': self.pgdb_username,
            'password': self.pgdb_password
        }

    class Config:
        env_file = '.env'


@lru_cache
def get_settings():
    return Settings()
