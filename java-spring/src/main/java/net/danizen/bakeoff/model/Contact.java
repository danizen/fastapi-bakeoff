package net.danizen.bakeoff.model;

import java.util.ArrayList;
import java.util.List;

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
	private @JsonProperty("phone_number") List<String> phoneNumber;
	private @JsonProperty("email") List<String> email;

	public Contact() {
		this.phoneNumber = new ArrayList<String>();
		this.email = new ArrayList<String>();
	}
	public Contact(int id, String firstName, String lastName, ContactType type) {
	    this.id = id;
	    this.firstName = firstName;
	    this.lastName = lastName;
	    this.type = type;
        phoneNumber = new ArrayList<String>();
        email = new ArrayList<String>();
	}

	public void addPhoneNumber(String phoneNumber) {
		this.phoneNumber.add(phoneNumber);
	}

	public void addEmail(String email) {
		this.email.add(email);
	}
}
