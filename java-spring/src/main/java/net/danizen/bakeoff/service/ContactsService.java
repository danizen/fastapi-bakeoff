package net.danizen.bakeoff.service;

import org.springframework.stereotype.Service;

import net.danizen.bakeoff.model.Contact;
import net.danizen.bakeoff.model.ContactType;
import net.danizen.bakeoff.model.ContactTypesResponse;
import net.danizen.bakeoff.model.ContactsResponse;

@Service
public class ContactsService {
	
	public ContactTypesResponse getTypes() {
		var response = new ContactTypesResponse();
		response.add(new ContactType(1, "Relative"));
		response.add(new ContactType(2, "Friend"));
		response.add(new ContactType(3, "Coworker"));
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
