package com.otra.dataaccess.dao;

import com.otra.dataaccess.entities.Metrics;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
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
            "`driver_id`, `distance`, `time`, `spends`, `impressions`, `impact`)" +
            "VALUES (:metric_id, :location_id, :campaign_id, :driver_id, :distance, " +
            ":time, :spends, :impressions, :impact)")
    public void insert(@Bind("metric_id") Integer metricId, @Bind("location_id") Integer locationId,
                       @Bind("campaign_id") Integer campaignId, @Bind("driver_id") Integer driverId,
                       @Bind("distance") Double distance, @Bind("time") Double time,
                       @Bind("spends") Double spends, @Bind("impressions") Double impressions, @Bind("impact") Double impact,
                       @Define("tableName") String tableName);

    @SqlUpdate("INSERT INTO <tableName> (`location_id`, `campaign_id`, " +
            "`driver_id`, `distance`, `time`, `spends`, `impressions`, `impact`)" +
            "VALUES (:location_id, :campaign_id, :driver_id, :distance, " +
            ":time, :spends, :impressions, :impact)")
    public void insertWithoutMetric(@Bind("location_id") Integer locationId,
                       @Bind("campaign_id") Integer campaignId, @Bind("driver_id") Integer driverId,
                       @Bind("distance") Double distance, @Bind("time") Double time,
                       @Bind("spends") Double spends, @Bind("impressions") Double impressions, @Bind("impact") Double impact,
                       @Define("tableName") String tableName);

    @SqlQuery("SELECT * from <tableName> WHERE metric_id = :metric_id and " +
            "location_id = :location_id and campaign_id = :campaign_id and driver_id = :driver_id")
    Metrics getMetricsForMetricLocationCampaignDriverId(@Bind("metric_id") Integer metricId, @Bind("location_id") Integer locationId,
                                                  @Bind("campaign_id") Integer campaignId,@Bind("driver_id") Integer driverId,
                                                  @Define("tableName") String tableName);

    @SqlQuery("SELECT * from <tableName> WHERE " +
            "location_id = :location_id and campaign_id = :campaign_id and driver_id = :driver_id")
    Metrics getMetricsForLocationCampaignDriverId(@Bind("location_id") Integer locationId,
                                                  @Bind("campaign_id") Integer campaignId,@Bind("driver_id") Integer driverId,
                                                  @Define("tableName") String tableName);

    @SqlUpdate("UPDATE <tableName> set distance = :mt.distance, " +
            "time = :mt.time, spends = :mt.spends, impressions = :mt.impressions " +
            "where id = :mt.id")
    void update(@BindBean("mt") Metrics metrics, @Define("tableName") String tableName);

}
