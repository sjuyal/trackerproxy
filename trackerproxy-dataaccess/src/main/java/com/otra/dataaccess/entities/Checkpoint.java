package com.otra.dataaccess.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by juyal.shashank on 27/11/16.
 */
@AllArgsConstructor
@Data
public class Checkpoint implements Serializable {

    private Integer id;
    private Integer deviceId;
    private Long time;
    private DateTime createdAt;
    private DateTime updatedAt;

    public static class CheckpointMapper implements ResultSetMapper<Checkpoint> {

        public Checkpoint map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
            return new Checkpoint(resultSet.getInt("id"),
                    resultSet.getInt("device_id"),
                    resultSet.getLong("time"),
                    new DateTime(resultSet.getTimestamp("created_at")),
                    new DateTime(resultSet.getTimestamp("updated_at")));
        }
    }
}
