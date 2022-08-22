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
public class ContactTypesResponse {

    private int count;
	private List<ContactType> results;

	public ContactTypesResponse() {
		results = new ArrayList<ContactType>();
	}

	public void add(ContactType contactType) {
		results.add(contactType);
	}
}
