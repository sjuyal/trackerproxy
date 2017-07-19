package com.otra.dataaccess.dao;

import com.otra.dataaccess.entities.Campaign;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * Created by juyal.shashank on 27/11/16.
 */
@RegisterMapper(Campaign.CampaignMapper.class)
public interface CampaignDAO {

    String tableName = "campaign";

    @SqlQuery("SELECT * from " + tableName + " WHERE id = :id")
    public Campaign get(@Bind("id") Integer id);

}
