from typing import List

from pydantic import PositiveInt
from fastapi import FastAPI, Depends

from .schema import Version, Contact
from .database import ContactsService
from .config import Settings, get_settings


settings = get_settings()
app = FastAPI()


@lru_cache
async def connect_db(database_url : )


@app.on_event('startup')
async def startup():
    app.state.pool = settings.database_url


@app.get('/version', response_model=Version)
def get_version():
    return Version(version='0.0.1')


@app.get('/fibonacci/<number>/', response_model=PositiveInt)
def get_fibonacci(number: PositiveInt):
    return 2


@app.get('/contacts/', response_model=List[Contact])
def list_contacts(dao=Depends(ContactsService)):
    import pdb
    pdb.set_trace()
    return dao.list_contacts()


@app.get('/contacts/<contact_id>/', response_model=Contact)
def retrieve_contact(contact_id: PositiveInt, dao=Depends(ContactsService)):
    return dao.get_contact(contact_id)
