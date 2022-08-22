package net.danizen.bakeoff.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Setter
public class ContactType {

	private int id;
	private String name;

	public ContactType(int id, String name) {
		this.id = id;
		this.name = name;
	}
	public ContactType() {}

	@JsonProperty("type_id")
	public int getId() {
		return id;
	}

	@JsonProperty("type_name")
	public String getName() {
		return name;
	}
}
