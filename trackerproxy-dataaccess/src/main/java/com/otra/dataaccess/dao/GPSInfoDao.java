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
public interface GPSInfoDao {

    static final String tableName = "gpsdump";

    @Timed
    @SqlUpdate("INSERT INTO " + tableName + " (id, device_id, latitude, longitude, time) " +
            "values " + " (now(), :info.deviceId, :info.latitude, :info.longitude, :info.time)")
    void insert(@BindBean("info") GPSInfo info);


    @Timed
    @SqlQuery("SELECT * FROM " + tableName  + " where device_id = :device_id and time > :time order by time")
    List<GPSInfo> getGPSInfosForDevice(@Bind("device_id") String deviceId, @Bind("time") Long time);

}
