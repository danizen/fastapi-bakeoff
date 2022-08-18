package net.danizen.bakeoff.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class ContactType {
	private final int id;
	private final String name;
	
	@JsonProperty("type_id")
	public int getId() {
		return id;
	}
	
	@JsonProperty("type_name")
	public String getName() {
		return name;
	}
}
