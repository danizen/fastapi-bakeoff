from typing import Optional

import asyncpg
from pydantic import NonNegativeInt, conint
from starlette.responses import RedirectResponse
from starlette.status import HTTP_302_FOUND
from fastapi import FastAPI, HTTPException

from .schema import (
    Version,
    FibonacciResponse,
    Contact,
    ContactTypesResponse,
    ContactsResponse
)
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


@app.get('/', include_in_schema=False)
def home():
    return RedirectResponse(url='/docs', status_code=HTTP_302_FOUND)


@app.get('/version/', response_model=Version)
def get_version():
    return Version(version='0.0.1')


# This is an intentionally naively recursive
# implementation that ignores the basics of
# dynamic programming. We want it to slow down
# arbitrarily through busy work.
#
# What is very interesting is that the server
# waits forever for a response from this even
# if this recursive fake solution is made async!
def fib(n: int) -> int:
    if n < 0:
        raise ValueError('should be non-negative')
    elif n < 2:
        return 1
    else:
        return fib(n-1) + fib(n-2)


@app.get('/fibonacci/{number}/', response_model=FibonacciResponse)
def get_fibonacci(number: conint(ge=0, lt=35)):
    return FibonacciResponse(result=fib(number))


@app.get('/types/', response_model=ContactTypesResponse)
async def list_types():
    async with app.state.pool.acquire() as conn:
        dao = ContactsService(conn)
        return await dao.list_types()


@app.get('/contacts/', response_model=ContactsResponse)
async def list_contacts(limit: conint(gt=0, le=200) = 100,
                        offset: conint(ge=0) = 0,
                        starts: Optional[str] = None):
    if offset is None:
        offset = 0
    if limit is None:
        limit = 100
    async with app.state.pool.acquire() as conn:
        dao = ContactsService(conn)
        return await dao.fetch_contacts(limit, offset, starts)


@app.get('/contacts/{contact_id}/', response_model=Contact)
async def retrieve_contact(contact_id: NonNegativeInt):
    async with app.state.pool.acquire() as conn:
        dao = ContactsService(conn)
        contact = await dao.get_contact(contact_id)
        if contact is None:
            raise HTTPException(
                status_code=404,
                detail='Contact not found')
        return contact
