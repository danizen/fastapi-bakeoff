package net.danizen.bakeoff.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class ContactType {
	private final int id;
	private final String name;

	public ContactType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	@JsonProperty("type_id")
	public int getId() {
		return id;
	}

	@JsonProperty("type_name")
	public String getName() {
		return name;
	}
}
