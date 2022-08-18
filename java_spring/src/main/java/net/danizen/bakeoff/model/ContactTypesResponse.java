package net.danizen.bakeoff.model;

import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class ContactTypesResponse {

	private List<ContactType> contacts;

	public ContactTypesResponse() {
		contacts = new ArrayList<ContactType>();
	}

	public int getCount() {
		return contacts.size();
	}

	public List<ContactType> getResults() {
		return contacts;
	}

	public void add(ContactType contactType) {
		contacts.add(contactType);
	}
}
