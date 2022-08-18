package net.danizen.bakeoff;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.danizen.bakeoff.service.ContactsService;

@SpringBootTest
public class TestContactsService {

	@Autowired
	private ContactsService service;

	@Test
	public void testGetTypes() {
		var actual = service.getTypes();
		
		assertEquals(3, actual.getCount());
		assertEquals(new ContactType(1, "Relatives"), actual.getResults().get(0));
	}
}
