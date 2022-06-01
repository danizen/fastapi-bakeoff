from faker import Faker
from .schema import Contact, ContactType


class DataAccess:
    def __init__(self, seed: int = 0):
        self.contact_types = [
            ContactType(type_id=1, type_name='Friends'),
            ContactType(type_id=2, type_name='Relatives'),
            ContactType(type_id=3, type_name='Coworkers')
        ]
        faker = Faker(seed)
        num_contacts = faker.random.randint(10, 100)
        contacts = []
        for contact_id in range(1, num_contacts+1):
            first_name = faker.first_name()
            last_name = faker.last_name()
            contact_type = faker.random.choice(self.contact_types)
            contact = Contact(
                contact_id=contact_id,
                first_name=first_name,
                last_name=last_name,
                contact_type=contact_type
            )
            contacts.append(contact)
        self.contacts = contacts

    def list_contacts(self):
        return self.contacts

    def get_contact(self, contact_id: int):
        for contact in self.contacts:
            if contact.contact_id == contact_id:
                return contact
        return None
