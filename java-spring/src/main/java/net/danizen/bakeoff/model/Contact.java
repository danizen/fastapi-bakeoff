package net.danizen.bakeoff.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
@Setter
public class Contact {
	private @JsonProperty("contact_id") int id;
	private @JsonProperty("first_name") String firstName;
	private @JsonProperty("last_name") String lastName;
	private @JsonProperty("contact_type") ContactType type;
	private @JsonProperty("phone_number") List<String> phoneNumbers;
	private @JsonProperty("email") List<String> emails;

	public Contact() {
		this.phoneNumbers = new ArrayList<String>();
		this.emails = new ArrayList<String>();
	}
	public Contact(int id, String firstName, String lastName, ContactType type) {
	    this.id = id;
	    this.firstName = firstName;
	    this.lastName = lastName;
	    this.type = type;
        phoneNumbers = new ArrayList<String>();
        emails = new ArrayList<String>();
	}

	public void addPhoneNumber(String phoneNumber) {
		this.phoneNumbers.add(phoneNumber);
	}

	public void addEmail(String email) {
		this.emails.add(email);
	}

	public static Map<Integer,Contact> toMap(List<Contact> contacts) {
	    return contacts.stream().collect(Collectors.toMap(
                contact -> contact.getId(),
                contact -> contact));

	}

	public static String toCommaDelimitedIdString(List<Contact> contacts) {
	    return String.join(", ", contacts.stream().map(contact -> Integer.toString(contact.getId())).toArray(String[]::new));
	}
}
