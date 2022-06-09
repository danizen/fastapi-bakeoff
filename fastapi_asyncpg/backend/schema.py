from typing import List, Optional
from pydantic import BaseModel, NonNegativeInt


class Version(BaseModel):
    version: str


class ContactType(BaseModel):
    type_id: NonNegativeInt
    type_name: str


class Contact(BaseModel):
    contact_id: NonNegativeInt
    first_name: str
    last_name: str
    contact_type: ContactType
    phone_number: Optional[List[str]]
    email: Optional[List[str]]


class ContactTypesResponse(BaseModel):
    count: NonNegativeInt
    results: List[ContactType]


class ContactsResponse(BaseModel):
    count: NonNegativeInt
    results: List[Contact]
