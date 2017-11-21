package com.otra.dataaccess.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
@Data
public class Boundaries implements Serializable {

    private Integer id;
    private String name;
    private String boundaries;
    private List<Coordinate> coordinates;
    private Integer locationId;

    public Boundaries(Integer id, String name, String boundaries){
        this.id = id;
        this.name = name;
        this.boundaries = boundaries;
    }

    public static class BoundariesMapper implements ResultSetMapper<Boundaries> {

        public Boundaries map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
            return new Boundaries(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("boundaries"));
        }
    }
}
