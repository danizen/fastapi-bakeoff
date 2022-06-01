from typing import List

from pydantic import PositiveInt
from fastapi import FastAPI, Depends

from .schema import Version, Contact
from .database import DataAccess

app = FastAPI()


@app.get('/version', response_model=Version)
def get_version():
    return Version(version='0.0.1')


@app.get('/fibonacci/<number>/', response_model=PositiveInt)
def get_fibonacci(number: PositiveInt):
    return 2


@app.get('/contacts/', response_model=List[Contact])
def list_contacts(dao=Depends(DataAccess)):
    return dao.list_contacts()


@app.get('/contacts/<contact_id>/', response_model=Contact)
def retrieve_contact(contact_id: PositiveInt, dao=Depends(DataAccess)):
    return dao.get_contact(contact_id)
