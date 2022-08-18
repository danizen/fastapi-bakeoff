package net.danizen.bakeoff.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@JsonPropertyOrder({"count", "results"})
public class ContactsResponse {

	private List<Contact> contacts;

	public ContactsResponse() {
		contacts = new ArrayList<Contact>();
	}

	public int getCount() {
		return contacts.size();
	}

	public List<Contact> getResults() {
		return contacts;
	}

	public void add(Contact contact) {
		contacts.add(contact);
	}
}
