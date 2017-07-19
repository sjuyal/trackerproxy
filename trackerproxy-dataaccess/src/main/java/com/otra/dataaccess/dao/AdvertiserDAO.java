package com.otra.dataaccess.dao;

import com.otra.dataaccess.entities.Advertiser;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * Created by juyal.shashank on 27/11/16.
 */
@RegisterMapper(Advertiser.AdvertiserMapper.class)
public interface AdvertiserDAO {

    String tableName = "Advertiser";

    @SqlQuery("SELECT * from " + tableName + " WHERE id = :id")
    public Advertiser get(@Bind("id") Integer id);

}
