package net.danizen.bakeoff;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.hasKey;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import net.danizen.bakeoff.controller.BakeoffController;
import net.danizen.bakeoff.model.Contact;
import net.danizen.bakeoff.model.ContactType;
import net.danizen.bakeoff.model.ContactTypesResponse;
import net.danizen.bakeoff.model.ContactsResponse;
import net.danizen.bakeoff.model.FibonacciResponse;
import net.danizen.bakeoff.service.ContactsService;
import net.danizen.bakeoff.service.FibonacciService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BakeoffController.class)
public class TestController {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FibonacciService fibonacciService;

    @MockBean
    private ContactsService contactsService;

    @BeforeEach
    public void setup() {
        when( fibonacciService.getFibonacci(22) )
            .thenReturn( new FibonacciResponse(28657) );

        ContactTypesResponse typesResponse = new ContactTypesResponse();
        typesResponse.setCount(2);
        typesResponse.setResults(List.of(
                new ContactType(1, "Vendors"),
                new ContactType(2, "Sales")
        ));
        when( contactsService.getTypes() ).thenReturn( typesResponse );

        Contact contact = new Contact(789, "Susanna", "Greenwood", new ContactType(1, "Vendors"));
        ContactsResponse contactsResponse = new ContactsResponse();
        contactsResponse.setCount(228513);
        contactsResponse.add(contact);

        when( contactsService.getContact(789) ).thenReturn( contact );
        when( contactsService.getContacts(100, 0, null) ).thenReturn( contactsResponse );

    }

    @Test
    public void fibonacciOk() throws Exception {
        mvc.perform(get("/fibonacci/22"))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(content().contentType("application/json"))
           .andExpect(jsonPath("$.result", is(28657)));
    }
    @Test
    public void fibonacciParamTypeError() throws Exception {
        mvc.perform(get("/fibonacci/22n/"))
           .andDo(print())
           .andExpect(status().isBadRequest())
           .andExpect(content().contentType("application/json"))
           .andExpect(jsonPath("$.status", is(400)))
           .andExpect(jsonPath("$.errors", hasKey("number")))
           .andExpect(jsonPath("$.errors.number", startsWith("Failed to convert value of type")));
    }

    @Test
    public void fibonacciParamTooLargeError() throws Exception {
        mvc.perform(get("/fibonacci/212/"))
           .andDo(print())
           .andExpect(status().isBadRequest())
           .andExpect(content().contentType("application/json"))
           .andExpect(jsonPath("$.status", is(400)))
           .andExpect(jsonPath("$.errors", hasKey("number")))
           .andExpect(jsonPath("$.errors.number", startsWith("must be less than or equal to")));
    }

    @Test
    public void typesOk() throws Exception {
        mvc.perform(get("/types"))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(content().contentType("application/json"))
           .andExpect(jsonPath("$.count", is(2)))
           .andExpect(jsonPath("$.results").isArray())
           .andExpect(jsonPath("$.results", hasSize(2)));
    }

    @Test
    public void contactsOk() throws Exception {
        mvc.perform(get("/contacts"))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(content().contentType("application/json"))
           .andExpect(jsonPath("$.count", is(228513)))
           .andExpect(jsonPath("$.results").isArray())
           .andExpect(jsonPath("$.results", hasSize(1)))
           .andExpect(jsonPath("$.results[0].first_name", is("Susanna")))
           .andExpect(jsonPath("$.results[0].last_name", is("Greenwood")))
           .andExpect(jsonPath("$.results[0].contact_id", is(789)));
    }

    @Test
    public void contactsLimitTypeError() throws Exception {
        mvc.perform(get("/contacts").param("limit", "dan"))
           .andDo(print())
           .andExpect(status().isBadRequest())
           .andExpect(content().contentType("application/json"))
           .andExpect(jsonPath("$.status", is(400)))
           .andExpect(jsonPath("$.errors", hasKey("limit")))
           .andExpect(jsonPath("$.errors.limit", startsWith("Failed to convert value of type")));
    }

    @Test
    public void contactsLimitTooLarge() throws Exception {
        mvc.perform(get("/contacts").param("limit", "201"))
           .andDo(print())
           .andExpect(status().isBadRequest())
           .andExpect(content().contentType("application/json"))
           .andExpect(jsonPath("$.status", is(400)))
           .andExpect(jsonPath("$.errors", hasKey("limit")))
           .andExpect(jsonPath("$.errors.limit", startsWith("must be less than or equal to")));
    }

    @Test
    public void contactOffsetTypeError() throws Exception {
        mvc.perform(get("/contacts").param("offset", "zero"))
           .andDo(print())
           .andExpect(status().isBadRequest())
           .andExpect(content().contentType("application/json"))
           .andExpect(jsonPath("$.status", is(400)))
           .andExpect(jsonPath("$.errors", hasKey("offset")))
           .andExpect(jsonPath("$.errors.offset", startsWith("Failed to convert value of type")));
    }

    @Test
    public void contactStartsPunctuationError() throws Exception {
        mvc.perform(get("/contacts").param("starts", "Dan?"))
           .andDo(print())
           .andExpect(status().isBadRequest())
           .andExpect(content().contentType("application/json"))
           .andExpect(jsonPath("$.status", is(400)))
           .andExpect(jsonPath("$.errors", hasKey("starts")))
           .andExpect(jsonPath("$.errors.starts", startsWith("should not contain punctuation")));
    }
}
