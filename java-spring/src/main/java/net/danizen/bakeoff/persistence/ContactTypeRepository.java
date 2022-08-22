package net.danizen.bakeoff.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import net.danizen.bakeoff.model.ContactType;

@Repository
public class ContactTypeRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ContactTypeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ContactType> findAll() {
        String sql = "SELECT type_id, type_name FROM contact_types";
        return jdbcTemplate.query(sql, new ContactTypeMapper());
    }
}
