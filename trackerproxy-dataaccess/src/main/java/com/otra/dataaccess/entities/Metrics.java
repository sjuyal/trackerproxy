package com.otra.dataaccess.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by juyal.shashank on 27/11/16.
 */
@AllArgsConstructor
@Data
public class Metrics implements Serializable {

    private Integer id;
    private Integer metricId;
    private String roadId;
    private Integer campaignId;
    private Integer driverId;
    private BigDecimal distance;
    private BigDecimal time;
    private BigDecimal spends;
    private BigDecimal impressions;
    private DateTime createdAt;

    public static class MetricMapper implements ResultSetMapper<Metrics> {

        public Metrics map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
            return new Metrics(resultSet.getInt("id"),
                    resultSet.getInt("metric_id"),
                    resultSet.getString("road_id"),
                    resultSet.getInt("campaign_id"),
                    resultSet.getInt("driver_id"),
                    resultSet.getBigDecimal("distance"),
                    resultSet.getBigDecimal("time"),
                    resultSet.getBigDecimal("spends"),
                    resultSet.getBigDecimal("impressions"),
                    new DateTime(resultSet.getTimestamp("created_at")));
        }
    }
}
