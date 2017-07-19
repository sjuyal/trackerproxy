package com.otra.dataaccess.dao;

import com.otra.dataaccess.entities.Device;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by juyal.shashank on 27/11/16.
 */
@RegisterMapper(Device.DeviceMapper.class)
public interface DeviceDAO {

    String tableName = "device";

    @SqlUpdate("INSERT INTO " + tableName +
            " ( driver_id, installation_date) " +
            " VALUES(:driver_id, :installation_date)")
    void create(@Bind("driver_id") Integer driverId, @Bind("installation_date") Timestamp timestamp);

    @SqlQuery("SELECT * from " + tableName + " WHERE id = :id")
    Device get(@Bind("id") Integer id);

    @SqlQuery("SELECT * from " + tableName + " WHERE gps_device_num = :id")
    Device getFromGPSDev(@Bind("id") String id);

    @SqlQuery("SELECT * from " + tableName + " where is_working = 1")
    List<Device> getAllActiveDevices();


}
