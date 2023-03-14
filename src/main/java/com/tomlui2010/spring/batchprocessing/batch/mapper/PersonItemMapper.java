package com.tomlui2010.spring.batchprocessing.batch.mapper;

import com.tomlui2010.spring.batchprocessing.batch.model.PersonDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/*used to map the rows of a database result set to objects in Java. It is typically used in conjunction with
Spring's JdbcTemplate class, which provides a convenient way to execute SQL queries and map the results to objects.*/
public class PersonItemMapper implements RowMapper<PersonDTO>{
    @Override
    public PersonDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return PersonDTO
                .builder()
                .id(rs.getLong("id"))
                .firstname(rs.getString("firstname"))
                .lastname(rs.getString("lastname"))
                .email(rs.getString("email"))
                .subscription_status(rs.getString("subscription_status"))
                .email_sent_status(rs.getString("email_sent_status"))
                .build();
    }
}
