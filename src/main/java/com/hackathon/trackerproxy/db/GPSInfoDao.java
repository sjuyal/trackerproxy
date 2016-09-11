package com.hackathon.trackerproxy.db;

import com.codahale.metrics.annotation.Timed;
import com.hackathon.trackerproxy.api.GPSInfo;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(GPSInfo.GPSInfoMapper.class)
public interface GPSInfoDao {

    static final String tableName = "file";

    @Timed
    @SqlUpdate("INSERT INTO " + tableName + " (user_id, g_meeting_id, file_link) " +
            "values " + " (:info.userId, :info.meetingId, :info.fileLink)")
    void insert(@BindBean("info") GPSInfo info);


    @Timed
    @SqlQuery("SELECT f.* FROM " + tableName + " f , meeting m where m.g_meeting_id=f.g_meeting_id " +
            "where name like :name and description like :description")
    List<GPSInfo> getFiles(@Bind("name") String name, @Bind("description") String description);

    @Timed
    @SqlQuery("SELECT * FROM " + tableName )
    List<GPSInfo> getAllFiles();



}
