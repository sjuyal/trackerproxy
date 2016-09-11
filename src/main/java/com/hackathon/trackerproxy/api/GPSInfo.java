package com.hackathon.trackerproxy.api;

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
    Integer id;
    String userId;
    String meetingId;
    String fileLink;

    public static class GPSInfoMapper implements ResultSetMapper<GPSInfo> {
        @Override
        public GPSInfo map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
            return new GPSInfo(
                    resultSet.getInt("id"),
                    resultSet.getString("user_id"),
                    resultSet.getString("g_meeting_id"),
                    resultSet.getString("file_link")
            );
        }
    }

}
