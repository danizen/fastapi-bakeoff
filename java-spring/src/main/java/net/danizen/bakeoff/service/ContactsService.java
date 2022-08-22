package net.danizen.bakeoff.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.danizen.bakeoff.model.Contact;
import net.danizen.bakeoff.model.ContactType;
import net.danizen.bakeoff.model.ContactTypesResponse;
import net.danizen.bakeoff.model.ContactsResponse;
import net.danizen.bakeoff.persistence.ContactRepository;
import net.danizen.bakeoff.persistence.ContactTypeRepository;

@Service
public class ContactsService {

    private ContactTypeRepository typesRepository;
    private ContactRepository contactRepository;

    @Autowired
    public ContactsService(ContactTypeRepository typesRepository, ContactRepository contactRepository) {
        this.typesRepository = typesRepository;
        this.contactRepository = contactRepository;
    }

	public ContactTypesResponse getTypes() {
	    typesRepository.findAll();
		var response = new ContactTypesResponse();
		List<ContactType> contactTypes = typesRepository.findAll();
		response.setCount(contactTypes.size());
		response.setResults(contactTypes);
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

	public ContactsResponse getContacts(int limit, int offset, String startsWith) {
		var response = new ContactsResponse();
		if (startsWith == null) {
    		response.setCount(contactRepository.countAll());
    		response.setResults(contactRepository.findAll(limit, offset));
		} else {
		    response.setCount(contactRepository.countStartsWith(startsWith));
		    response.setResults(contactRepository.findStartsWith(limit, offset, startsWith));
		}
		return response;
	}
}
