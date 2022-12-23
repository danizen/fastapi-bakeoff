from functools import lru_cache
from pydantic import BaseSettings, conint
from typing import Optional


class Settings(BaseSettings):
    pgdb_host: str
    pgdb_database: Optional[str]
    pgdb_username: str
    pgdb_password: str
    pool_min_size: conint(ge=0) = 1
    pool_max_size: conint(gt=0) = 2
    echo_sql: bool = False                  # sets the echo parameter
    # pool_max_queries: Optional[conint(gt=0)] = None

    class Config:
        env_file = '.env'


@lru_cache
def get_settings():
    return Settings()
