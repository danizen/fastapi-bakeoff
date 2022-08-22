package net.danizen.bakeoff.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.jdbc.core.RowMapper;

public class IdValueMapper implements RowMapper<ImmutablePair<Integer,String>> {

    @Override
    public ImmutablePair<Integer, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ImmutablePair<Integer, String>( rs.getInt(1), rs.getString(2));
    }
}
