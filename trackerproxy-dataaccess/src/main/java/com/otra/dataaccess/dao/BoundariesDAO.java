package com.otra.dataaccess.dao;

import com.otra.dataaccess.entities.Boundaries;
import com.otra.dataaccess.entities.Driver;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(Boundaries.BoundariesMapper.class)
public interface BoundariesDAO {

    String tableName = "boundaries";

    @SqlUpdate("INSERT INTO " + tableName +
            " ( name, boundaries) " +
            " VALUES(:name, :boundaries)")
    void create(@Bind("name") String name, @Bind("boundaries") String boundaries);

    @SqlQuery("SELECT * from " + tableName)
    List<Boundaries> getAllBoundaries();

    @SqlQuery("SELECT name from " + tableName)
    List<String> getAllAreas();

}
