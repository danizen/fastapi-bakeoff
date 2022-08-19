from functools import lru_cache
from pydantic import BaseSettings, PostgresDsn, conint


class Settings(BaseSettings):
    database_url: PostgresDsn
    pool_min_size: conint(ge=0) = 0
    pool_max_size: conint(gt=0) = 2
    # pool_max_queries: Optional[conint(gt=0)] = None

    def connect_params(self):
        port = self.database_url.port
        if port is None:
            port = 5432
        database = 'postgres'
        path = self.database_url.path
        if path and path[0] == '/':
            path = path[1:]
        if path:
            database = path
        return {
            'host': self.database_url.host,
            'port': port,
            'database': database,
            'user': self.database_url.user,
            'password': self.database_url.password,
        }

    class Config:
        env_file = '.env'


@lru_cache
def get_settings():
    return Settings()
