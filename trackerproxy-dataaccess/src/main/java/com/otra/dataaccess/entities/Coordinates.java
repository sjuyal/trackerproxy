package com.otra.dataaccess.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by juyal.shashank on 27/11/16.
 */
@AllArgsConstructor
@Data
public class Coordinates implements Serializable {

    private Integer id;
    private String latitude;
    private String longitude;
    private String roadId;

    public static class CoordinatesMapper implements ResultSetMapper<Coordinates> {

        public Coordinates map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
            return new Coordinates(resultSet.getInt("id"),
                    resultSet.getString("latitude"),
                    resultSet.getString("longitude"),
                    resultSet.getString("road_id"));
        }
    }
}
