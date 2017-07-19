package com.otra.dataaccess.dao;

import com.otra.dataaccess.entities.GPSInfo;
import com.otra.dataaccess.entities.Location;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * Created by juyal.shashank on 27/11/16.
 */
@RegisterMapper(Location.LocationMapper.class)
public interface LocationDAO {

    String tableName = "location";

    @SqlUpdate("INSERT INTO location (road_id, road_name, neighbourhood, suburb, city, " +
            "county, state_district, postcode, country, country_code)\n" +
            "VALUES (:location.roadId, :location.roadName, :location.neighbourhood, " +
            ":location.suburb, :location.city, :location.county, :location.stateDistrict, " +
            ":location.postcode, :location.country, :location.countryCode)")
    void insert(@BindBean("location") Location location);

    @SqlQuery("SELECT * from " + tableName + " WHERE road_id = :road_id")
    Location get(@Bind("road_id") String roadId);

}
