package com.otra.dataaccess.dao;

import com.codahale.metrics.annotation.Timed;
import com.otra.dataaccess.entities.GPSInfo;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(GPSInfo.GPSInfoMapper.class)
public interface RealTimeInfoDao {

    static final String tableName = "realtimedump";

    @Timed
    @SqlUpdate("INSERT INTO " + tableName + " (id, device_id, latitude, longitude, time) " +
            "values " + " (now(), :info.deviceId, :info.latitude, :info.longitude, :info.time)")
    void insert(@BindBean("info") GPSInfo info);

    @Timed
    @SqlQuery("SELECT * FROM " + tableName  + " where device_id = :device_id")
    GPSInfo getGPSInfoForDevice(@Bind("device_id") String deviceId);

    @SqlUpdate("UPDATE " + tableName +" set latitude = :rt.latitude, " +
            "longitude = :rt.longitude, time = :rt.time " +
            "where device_id = :rt.deviceId")
    void update(@BindBean("rt") GPSInfo gpsInfo);



}
