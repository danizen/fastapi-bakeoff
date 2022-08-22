package net.danizen.bakeoff;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import net.danizen.bakeoff.model.Contact;
import net.danizen.bakeoff.model.ContactType;

@SpringBootTest
public class TestContact {
    private List<Contact> contacts;

    public TestContact() {
        ContactType type = new ContactType(1, "Testing");
        contacts = List.of(
                new Contact(3, "Glen", "Young", type),
                new Contact(1, "Erik", "Bloom", type),
                new Contact(2, "Dave", "Brubeck", type));
    }

    @Test
    public void testNoArgConstructor() {
        Contact actual = new Contact();
        assertEquals(0, actual.getId());
        assertEquals(null, actual.getFirstName());
        assertEquals(null, actual.getLastName());
        assertEquals(null, actual.getType());
        assertEquals(0, actual.getPhoneNumbers().size());
        assertEquals(0, actual.getEmails().size());
    }

    @Test
    public void testArgsConstructor() {
        Contact actual = contacts.get(0);
        assertEquals(3, actual.getId());
        assertEquals("Glen", actual.getFirstName());
        assertEquals("Young", actual.getLastName());
        assertEquals("Testing", actual.getType().getName());
    }

    @Test
    public void testToCommaDelimited() {
        String actual = Contact.toCommaDelimitedIdString(contacts);
        assertEquals("3, 1, 2", actual);
    }

    @Test
    public void testToMap() {
        Map<Integer, Contact> actual = Contact.toMap(contacts);
        assertEquals(3, actual.size());
        assertEquals("Brubeck", actual.get(2).getLastName());
    }
}
