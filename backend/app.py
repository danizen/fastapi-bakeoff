from typing import List, Optional

import asyncpg
from pydantic import NonNegativeInt, conint
from fastapi import FastAPI

from .schema import Version, Contact, ContactType
from .database import ContactsService
from .config import get_settings


settings = get_settings()
app = FastAPI()


@app.on_event('startup')
async def startup():
    params = settings.connect_params()
    app.state.pool = await asyncpg.create_pool(
        min_size=settings.pool_min_size,
        max_size=settings.pool_max_size,
        **params
    )


@app.get('/version', response_model=Version)
def get_version():
    return Version(version='0.0.1')


@app.get('/fibonacci/<number>/', response_model=conint(gt=0))
async def get_fibonacci(number: conint(ge=0)):
    return 2


@app.get('/types/', response_model=List[ContactType])
async def list_types():
    async with app.state.pool.acquire() as conn:
        dao = ContactsService(conn)
        return await dao.list_types()


@app.get('/contacts/', response_model=List[Contact])
async def list_contacts(limit: Optional[conint(gt=0, le=200)],
                        offset: Optional[conint(ge=0)]):
    async with app.state.pool.acquire() as conn:
        dao = ContactsService(conn)
        return await dao.list_contacts(limit, offset)


@app.get('/contacts/<contact_id>/', response_model=Contact)
async def retrieve_contact(contact_id: NonNegativeInt):
    async with app.state.pool.acquire() as conn:
        dao = ContactsService(conn)
        return await dao.get_contact(contact_id)
