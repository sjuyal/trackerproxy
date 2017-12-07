package com.otra.dataaccess.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by juyal.shashank on 27/11/16.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location implements Serializable {

    private Integer id;
    private Integer areaId;
    private DateTime createdAt;
    private DateTime updatedAt;

    public Location(Integer areaId, String city,
                    String country) {
        this.areaId = areaId;
    }

    public static class LocationMapper implements ResultSetMapper<Location> {

        public Location map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
            return new Location(resultSet.getInt("id"),
                    resultSet.getInt("area_id"),
                    new DateTime(resultSet.getTimestamp("created_at")),
                    new DateTime(resultSet.getTimestamp("updated_at")));
        }
    }
}
