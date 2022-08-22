package net.danizen.bakeoff.persistence;

import java.util.List;
import java.util.Map;

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

    private void addPhoneAndEmail(List<Contact> contacts) {
        // data structures
        Map<Integer, Contact> map = Contact.toMap(contacts);
        String contactIds = Contact.toCommaDelimitedIdString(contacts);

        // phone numbers
        String phoneSql = String.format(
                "SELECT contact_id, phone_number FROM contact_phones WHERE contact_id IN (%s)",
                contactIds);
        jdbcTemplate.queryForStream(phoneSql, new IdValueMapper()).forEach(pair -> {
           Contact contact = map.get(pair.getKey());
           contact.addPhoneNumber(pair.getValue());
        });

        // emails
        String emailSql = String.format(
                "SELECT contact_id, email_address FROM contact_emails WHERE contact_id IN (%s)",
                contactIds);
        jdbcTemplate.queryForStream(emailSql, new IdValueMapper()).forEach(pair -> {
            Contact contact = map.get(pair.getKey());
            contact.addEmail(pair.getValue());
        });
    }

    public List<Contact> findAll(int limit, int offset) {
        // a list of contacts
        String sql = """
                SELECT
                    c.contact_id,
                    c.first_name,
                    c.last_name,
                    ct.type_id,
                    ct.type_name
                FROM contacts c
                JOIN contact_types ct ON (c.type_id = ct.type_id)
                LIMIT ? OFFSET ?
                """;
        List<Contact> contacts = jdbcTemplate.query(
                sql, new ContactMapper(),
                limit, offset);
        addPhoneAndEmail(contacts);
        return contacts;
    }

    public List<Contact> findStartsWith(int limit, int offset, String startsWith) {
        // a list of contacts
        String like = startsWith.toLowerCase()+"%";
        String sql = """
                SELECT
                    c.contact_id,
                    c.first_name,
                    c.last_name,
                    ct.type_id,
                    ct.type_name
                FROM contacts c
                JOIN contact_types ct ON (c.type_id = ct.type_id)
                WHERE LOWER(c.last_name) LIKE ?
                LIMIT ? OFFSET ?
                """;
        List<Contact> contacts = jdbcTemplate.query(
                sql, new ContactMapper(),
                like, limit, offset);
        addPhoneAndEmail(contacts);
        return contacts;

    }

    public Integer countAll() {
        String sql = "SELECT COUNT(contact_id) FROM contacts";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public Integer countStartsWith(String startsWith) {
        String like = startsWith.toLowerCase()+"%";
        String sql = "SELECT COUNT(contact_id) FROM contacts WHERE LOWER(last_name) LIKE ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, like);
    }
}
