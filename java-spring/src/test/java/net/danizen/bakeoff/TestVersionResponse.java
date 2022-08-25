package net.danizen.bakeoff;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import net.danizen.bakeoff.model.VersionResponse;

public class TestVersionResponse {

	@Test
	public void allArgsConstructor() {
		var response = new VersionResponse("0.0.1");
		assertThat(response.getVersion()).isEqualTo("0.0.1");
	}

	@Test
	public void noArgsConstructor() {
		var response = new VersionResponse();
		assertThat(response.getVersion()).isNull();
	}

	@Test
	public void setter() {
		var response = new VersionResponse();
		response.setVersion("0.0.2");
		assertThat(response.getVersion()).isEqualTo("0.0.2");
	}
}
