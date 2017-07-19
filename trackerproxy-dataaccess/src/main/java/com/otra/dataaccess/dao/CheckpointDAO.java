package com.otra.dataaccess.dao;

import com.otra.dataaccess.entities.Checkpoint;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by juyal.shashank on 27/11/16.
 */
@RegisterMapper(Checkpoint.CheckpointMapper.class)
public interface CheckpointDAO {

    String tableName = "checkpoints";

    @SqlUpdate("INSERT INTO " + tableName +
            " ( device_id, time) " +
            " VALUES(:device_id, :time)")
    void create(@Bind("device_id") Integer deviceId, @Bind("time") Long time);

    @SqlUpdate("UPDATE " + tableName +
            " set time = :time where device_id = :device_id")
    void update(@Bind("device_id") Integer deviceId, @Bind("time") Long time);


    @SqlQuery("SELECT * from " + tableName + " WHERE device_id = :device_id")
    Checkpoint get(@Bind("device_id") Integer deviceId);


}
