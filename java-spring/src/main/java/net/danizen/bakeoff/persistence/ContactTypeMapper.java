package net.danizen.bakeoff.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import net.danizen.bakeoff.model.ContactType;


public class ContactTypeMapper implements RowMapper<ContactType> {
    @Override
    public ContactType mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ContactType(
                rs.getInt("type_id"),
                rs.getString("type_name")
        );
    }
}
