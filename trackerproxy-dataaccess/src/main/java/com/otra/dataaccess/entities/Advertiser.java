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
public class Advertiser implements Serializable {

    private Integer id;
    private String name;
    private String emailId;
    private String contact;
    private DateTime createdAt;
    private DateTime updatedAt;

    public static class AdvertiserMapper implements ResultSetMapper<Advertiser> {

        public Advertiser map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
            return new Advertiser(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("email_id"),
                    resultSet.getString("contact"),
                    new DateTime(resultSet.getTimestamp("created_at")),
                    new DateTime(resultSet.getTimestamp("updated_at")));
        }
    }
}
