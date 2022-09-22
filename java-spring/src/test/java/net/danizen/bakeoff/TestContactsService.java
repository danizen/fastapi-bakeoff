package net.danizen.bakeoff;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import net.danizen.bakeoff.model.Contact;
import net.danizen.bakeoff.model.ContactType;
import net.danizen.bakeoff.persistence.ContactRepository;
import net.danizen.bakeoff.persistence.ContactTypeRepository;
import net.danizen.bakeoff.service.ContactsService;

@ExtendWith(MockitoExtension.class)
public class TestContactsService {

	@Mock(lenient = true)
	private ContactTypeRepository typeRepository;

	@Mock(lenient = true)
	private ContactRepository contactRepository;

	private ContactsService service;

	@BeforeEach
	public void setup() {
	    when(typeRepository.findAll()).thenReturn(List.of(
	            new ContactType(1, "Favorites"),
	            new ContactType(2, "Vendors"),
	            new ContactType(3, "Coworkers")
	    ));

        var contact = new Contact(789, "Susanna", "Greenwood", new ContactType(1, "Vendors"));
        var contactList = List.of(contact);

        when( contactRepository.countAll() ).thenReturn(228513);
        when( contactRepository.countStartsWith("G") ).thenReturn(13);
        when( contactRepository.findContactById(789) ).thenReturn(contact);
        when( contactRepository.findAll(100, 0) ).thenReturn(contactList);
        when( contactRepository.findStartsWith(100, 0, "G") ).thenReturn(contactList);

	    // NOTE - we can use @InjectMocks from mockito to do this, but it is one line
	    service = new ContactsService(typeRepository, contactRepository);

	}

	@Test
	public void testGetTypes() {
		var actual = service.getTypes();
		var results = actual.getResults();

		assertThat(results.size()).isEqualTo(3);
		assertThat(actual.getCount()).isEqualTo(3);
		assertThat(results.get(0).getName()).isEqualTo("Favorites");
		assertThat(results.get(1).getName()).isEqualTo("Vendors");
		assertThat(results.get(2).getName()).isEqualTo("Coworkers");
	}

	@Test
	public void testGetContact() {
	    var actual = service.getContact(789);
	    assertThat(actual.getFirstName()).isEqualTo("Susanna");
	}

	@Test
	public void testGetAllContacts() {
	    var actual = service.getContacts(100, 0, null);
	    var results = actual.getResults();

	    assertThat(actual.getCount()).isEqualTo(228513);
	    assertThat(results.size()).isEqualTo(1);
	    assertThat(results.get(0).getFirstName()).isEqualTo("Susanna");
	}

	@Test
	public void testGetContactsStartingWithG() {
        var actual = service.getContacts(100, 0, "G");
        var results = actual.getResults();

        assertThat(actual.getCount()).isEqualTo(13);
        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getFirstName()).isEqualTo("Susanna");
	}
}