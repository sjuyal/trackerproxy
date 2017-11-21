package com.otra.dataaccess.dao;

import com.otra.dataaccess.entities.Metrics;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Define;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;

/**
 * Created by juyal.shashank on 27/11/16.
 */
@UseStringTemplate3StatementLocator
@RegisterMapper(Metrics.MetricMapper.class)
public interface MetricsDAO {

    @SqlQuery("SELECT * from <tableName> WHERE id = :id")
    public Metrics get(@Bind("id") Integer id, @Define("tableName") String tableName);

    @SqlUpdate("INSERT INTO <tableName> (`metric_id`, `location_id`, `campaign_id`, " +
            "`driver_id`, `distance`, `time`, `spends`, `impressions`)" +
            "VALUES (:metric_id, :location_id, :campaign_id, :driver_id, :distance, " +
            ":time, :spends, :impressions)")
    public void insert(@Bind("metric_id") Integer metricId, @Bind("location_id") Integer locationId,
                       @Bind("campaign_id") Integer campaignId, @Bind("driver_id") Integer driverId,
                       @Bind("distance") Double distance, @Bind("time") Double time,
                       @Bind("spends") Double spends, @Bind("impressions") Double impressions,
                       @Define("tableName") String tableName);

}
