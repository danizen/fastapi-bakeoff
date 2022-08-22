package net.danizen.bakeoff.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import net.danizen.bakeoff.model.Contact;
import net.danizen.bakeoff.model.ContactType;


public class ContactMapper implements RowMapper<Contact> {

    @Override
    public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Contact(
                rs.getInt("contact_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                new ContactType(
                        rs.getInt("type_id"),
                        rs.getString("type_name")
                )
        );
    }

}
