package net.danizen.bakeoff.service;

import org.springframework.stereotype.Service;

import net.danizen.bakeoff.model.Contact;
import net.danizen.bakeoff.model.ContactType;
import net.danizen.bakeoff.model.ContactTypesResponse;
import net.danizen.bakeoff.model.ContactsResponse;
import net.danizen.bakeoff.persistence.ContactTypeRepository;

@Service
public class ContactsService {

    private ContactTypeRepository typesRepository;

    public ContactsService(ContactTypeRepository typesRepository) {
        this.typesRepository = typesRepository;
    }

	public ContactTypesResponse getTypes() {
	    typesRepository.findAll();
		var response = new ContactTypesResponse();
		typesRepository.findAll().forEach(contactType -> {
		    response.add(contactType);
		});
		return response;
	}

	public Contact getContact(int id) {
		var response = new Contact();
		response.setId(id);
		response.setFirstName("Sarah");
		response.setLastName("Avery");
		response.setType(new ContactType(1, "Relative"));
		return response;
	}

	public ContactsResponse getContacts() {
		var response = new ContactsResponse();
		response.add(getContact(22));
		return response;
	}
}
