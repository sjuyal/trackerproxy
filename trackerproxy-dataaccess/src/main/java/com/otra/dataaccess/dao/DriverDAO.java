package com.otra.dataaccess.dao;

import com.otra.dataaccess.entities.Driver;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * Created by juyal.shashank on 27/11/16.
 */
@RegisterMapper(Driver.DriverMapper.class)
public interface DriverDAO {

    String tableName = "driver";

    @SqlUpdate("INSERT INTO " + tableName +
            " ( car_no, name, is_approved, campaign_id) " +
            " VALUES(:car_no, :name, :is_approved, :campaign_id)")
    void create(@Bind("car_no") String carNo, @Bind("name") String name,
                       @Bind("is_approved") Boolean isApproved,
                       @Bind("campaign_id") Integer campaignId);

    @SqlQuery("SELECT * from " + tableName + " WHERE id = :id")
    Driver get(@Bind("id") Integer id);

    @SqlQuery("SELECT * from " + tableName)
    List<Driver> getAllDrivers();

}
