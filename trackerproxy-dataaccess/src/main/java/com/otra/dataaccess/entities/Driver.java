package com.otra.dataaccess.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.joda.time.DateTime;
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
public class Driver implements Serializable {

    private Integer id;
    private String carNo;
    private String name;
    private Boolean isApproved;
    private Integer campaignId;
    private DateTime createdAt;
    private DateTime updatedAt;

    public static class DriverMapper implements ResultSetMapper<Driver> {

        public Driver map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
            return new Driver(resultSet.getInt("id"),
                    resultSet.getString("car_no"),
                    resultSet.getString("name"),
                    resultSet.getBoolean("is_approved"),
                    resultSet.getInt("campaign_id"),
                    new DateTime(resultSet.getTimestamp("created_at")),
                    new DateTime(resultSet.getTimestamp("updated_at")));
        }
    }
}
