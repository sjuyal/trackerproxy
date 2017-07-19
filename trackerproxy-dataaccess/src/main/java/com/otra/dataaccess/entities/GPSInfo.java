package com.otra.dataaccess.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class GPSInfo {
    private Double latitude;
    private Double longitude;
    private Long time;
    private String deviceId;


    public static class GPSInfoMapper implements ResultSetMapper<GPSInfo> {
        @Override
        public GPSInfo map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
            return new GPSInfo(
                    resultSet.getDouble("latitude"),
                    resultSet.getDouble("longitude"),
                    resultSet.getLong("time"),
                    resultSet.getString("device_id")
            );
        }
    }

}
