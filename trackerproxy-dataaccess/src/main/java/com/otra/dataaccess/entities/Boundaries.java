package com.otra.dataaccess.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
@Data
public class Boundaries implements Serializable {

    private Integer id;
    private String name;
    private String boundaries;

    public static class BoundariesMapper implements ResultSetMapper<Boundaries> {

        public Boundaries map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
            return new Boundaries(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("boundaries"));
        }
    }
}
