package com.otra.dataaccess.dao;

import com.otra.dataaccess.entities.GPSInfo;
import com.otra.dataaccess.entities.Location;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * Created by juyal.shashank on 27/11/16.
 */
@RegisterMapper(Location.LocationMapper.class)
public interface LocationDAO {

    String tableName = "location";

    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO location (area_id)" +
            "VALUES (:location.areaId)")
    int insert(@BindBean("location") Location location);

    @SqlQuery("SELECT * from " + tableName + " WHERE area_id = :area_id")
    Location get(@Bind("area_id") Integer id);

}
