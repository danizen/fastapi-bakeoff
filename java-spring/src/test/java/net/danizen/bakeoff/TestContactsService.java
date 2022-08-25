package net.danizen.bakeoff;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import net.danizen.bakeoff.model.ContactType;
import net.danizen.bakeoff.persistence.ContactRepository;
import net.danizen.bakeoff.persistence.ContactTypeRepository;
import net.danizen.bakeoff.service.ContactsService;

@ExtendWith(MockitoExtension.class)
public class TestContactsService {

	@Mock
	private ContactTypeRepository typeRepository;

	@Mock
	private ContactRepository contactRepository;

	private ContactsService service;

	@BeforeEach
	public void setup() {
	    when(typeRepository.findAll()).thenReturn(List.of(
	            new ContactType(1, "Favorites"),
	            new ContactType(2, "Vendors"),
	            new ContactType(3, "Coworkers")
	    ));
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
}
