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
public class Device implements Serializable {

    private Integer id;
    private String gpsDevNum;
    private Boolean isWorking;
    private Integer driverId;
    private DateTime installationDate;
    private DateTime createdAt;
    private DateTime updatedAt;

    public static class DeviceMapper implements ResultSetMapper<Device> {

        public Device map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
            return new Device(resultSet.getInt("id"),
                    resultSet.getString("gps_device_num"),
                    resultSet.getBoolean("is_working"),
                    resultSet.getInt("driver_id"),
                    new DateTime(resultSet.getTimestamp("installation_date")),
                    new DateTime(resultSet.getTimestamp("created_at")),
                    new DateTime(resultSet.getTimestamp("updated_at")));
        }
    }
}
