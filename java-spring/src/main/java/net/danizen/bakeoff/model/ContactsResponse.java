package net.danizen.bakeoff.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@JsonPropertyOrder({"count", "results"})
@Getter
@Setter
public class ContactsResponse {

    private int count;
	private List<Contact> results;

	public ContactsResponse() {
		results = new ArrayList<Contact>();
	}
	public void add(Contact contact) {
		results.add(contact);
	}
}
