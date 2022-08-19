package net.danizen.bakeoff.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.EqualsAndHashCode;
import lombok.ToString;


@EqualsAndHashCode
@ToString
@JsonPropertyOrder({"count", "results"})
public class ContactTypesResponse {

	private List<ContactType> types;

	public ContactTypesResponse() {
		types = new ArrayList<ContactType>();
	}

	public int getCount() {
		return types.size();
	}

	public List<ContactType> getResults() {
		return types;
	}

	public void add(ContactType contactType) {
		types.add(contactType);
	}
}
