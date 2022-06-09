from typing import Optional

from pydantic import NonNegativeInt, conint
from starlette.responses import RedirectResponse
from starlette.status import HTTP_302_FOUND
from fastapi import FastAPI

from .config import get_settings
from .contacts import ContactsService
from .orm import create_async_engine, create_session_maker
from .schema import (
    Version,
    FibonacciResponse,
    Contact,
    ContactTypesResponse,
    ContactsResponse,
)


settings = get_settings()
app = FastAPI()


@app.on_event('startup')
def startup():
    app.state.engine = engine = create_async_engine(settings)
    app.state.sessionmaker = create_session_maker(engine)


@app.on_event('shutdown')
async def shutdown():
    await app.state.engine.dispose()


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
def fib(n: int) -> int:
    if n < 0:
        raise ValueError('should be non-negative')
    elif n < 2:
        return 1
    else:
        return fib(n-1) + fib(n-2)


@app.get('/fibonacci/{number}/', response_model=FibonacciResponse)
async def get_fibonacci(number: conint(ge=0, lt=35)):
    return FibonacciResponse(result=fib(number))


@app.get('/types/', response_model=ContactTypesResponse)
async def list_types():
    async with app.state.sessionmaker() as session:
        dao = ContactsService(session)
        return await dao.list_types()


@app.get('/contacts/', response_model=ContactsResponse)
async def fetch_contacts(limit: conint(gt=0, le=200) = 100,
                         offset: conint(ge=0) = 0,
                         starts: Optional[str] = None):
    if offset is None:
        offset = 0
    if limit is None:
        limit = 100
    async with app.state.sessionmaker() as session:
        dao = ContactsService(session)
        return await dao.list_contacts(limit, offset, starts)


@app.get('/contacts/{contact_id}/', response_model=Contact)
async def retrieve_contact(contact_id: NonNegativeInt):
    async with app.state.sessionmaker() as session:
        dao = ContactsService(session)
        return await dao.get_contact(contact_id)
