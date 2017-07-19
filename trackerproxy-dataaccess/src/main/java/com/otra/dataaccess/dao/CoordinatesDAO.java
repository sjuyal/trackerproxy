package com.otra.dataaccess.dao;

import com.otra.dataaccess.entities.Coordinates;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * Created by juyal.shashank on 27/11/16.
 */
@RegisterMapper(Coordinates.CoordinatesMapper.class)
public interface CoordinatesDAO {

    String tableName = "coordinates";

    @SqlQuery("SELECT * from " + tableName + " WHERE id = :id")
    public Coordinates get(@Bind("id") Integer id);

}
