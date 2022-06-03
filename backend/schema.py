from typing import Optional
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
    phone_number: Optional[str]
    email: Optional[str]
