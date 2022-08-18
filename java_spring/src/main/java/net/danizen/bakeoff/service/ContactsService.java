package net.danizen.bakeoff.service;

import org.springframework.stereotype.Service;

import net.danizen.bakeoff.model.ContactType;
import net.danizen.bakeoff.model.ContactTypesResponse;

@Service
public class ContactsService {
	
	public ContactTypesResponse getTypes() {
		var response = new ContactTypesResponse();
		response.add(new ContactType(1, "Relative"));
		response.add(new ContactType(2, "Friend"));
		response.add(new ContactType(3, "Coworker"));
		return response;
	}
}
