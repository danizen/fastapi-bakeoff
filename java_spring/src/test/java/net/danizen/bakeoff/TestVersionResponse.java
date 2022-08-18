package net.danizen.bakeoff;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import net.danizen.bakeoff.model.VersionResponse;

@SpringBootTest
public class TestVersionResponse {
	
	@Test
	public void allArgsConstructor() {
		var response = new VersionResponse("0.0.1");
		assertEquals("0.0.1", response.getVersion());
	}
	
	@Test
	public void noArgsConstructor() {
		var response = new VersionResponse();
		assertEquals(null, response.getVersion());
	}
	
	@Test
	public void setter() {
		var response = new VersionResponse();
		response.setVersion("0.0.2");
		assertEquals("0.0.2", response.getVersion());
	}
	
	
}
