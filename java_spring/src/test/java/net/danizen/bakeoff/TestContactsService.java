package net.danizen.bakeoff;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.danizen.bakeoff.model.ContactType;
import net.danizen.bakeoff.service.ContactsService;

@SpringBootTest
public class TestContactsService {

	@Autowired
	private ContactsService service;

	@Test
	public void testGetTypes() {
		var actual = service.getTypes();
		var results = actual.getResults();
		
		assertEquals(3, results.size());
		assertEquals(3, actual.getCount());
		assertEquals(new ContactType(1, "Relative"), results.get(0));
		assertEquals(new ContactType(2, "Friend"), results.get(1));
		assertEquals(new ContactType(3, "Coworker"), results.get(2));
	}
}
