package net.danizen.bakeoff;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import net.danizen.bakeoff.model.Contact;
import net.danizen.bakeoff.model.ContactType;


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
        assertThat(actual.getId()).isEqualTo(0);
        assertThat(actual.getFirstName()).isNull();
        assertThat(actual.getLastName()).isNull();
        assertThat(actual.getType()).isNull();
        assertThat(actual.getPhoneNumbers()).size().isEqualTo(0);
        assertThat(actual.getEmails()).size().isEqualTo(0);
    }

    @Test
    public void testArgsConstructor() {
        Contact actual = contacts.get(0);
        assertThat(actual.getId()).isEqualTo(3);
        assertThat(actual.getFirstName()).isEqualTo("Glen");
        assertThat(actual.getLastName()).isEqualTo("Young");
        assertThat(actual.getType().getName()).isEqualTo("Testing");
    }

    @Test
    public void testToCommaDelimited() {
        String actual = Contact.toCommaDelimitedIdString(contacts);
        assertThat(actual).isEqualTo("3, 1, 2");
    }

    @Test
    public void testToMap() {
        Map<Integer, Contact> actual = Contact.toMap(contacts);
        assertThat(actual).size().isEqualTo(3);
        assertThat(actual).containsOnlyKeys(3, 1, 2);
        assertThat(actual.get(2).getLastName()).isEqualTo("Brubeck");
    }
}
