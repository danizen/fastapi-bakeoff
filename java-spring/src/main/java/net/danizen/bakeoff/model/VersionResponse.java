package net.danizen.bakeoff.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class VersionResponse {
	private @Getter @Setter String version;

	public VersionResponse(String version) {
	    this.version = version;
	}
	public VersionResponse() {}
}
