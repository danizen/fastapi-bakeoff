package net.danizen.bakeoff.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import net.danizen.bakeoff.model.Contact;

@Repository
public class ContactRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ContactRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Contact> findAll() {
        String sql = ""; // TODO: multiline SQL to get contact joined with contact type
        List<Contact> contacts = jdbcTemplate.query(sql, new ContactMapper());

        // TODO: gather all phone_ids and email_ids for the contact_ids and map
        return contacts;
    }
}
