package com.otra.nucleo.utils;

import org.skife.jdbi.v2.DBI;

/**
 * Created by juyal.shashank on 26/11/16.
 */
public class DBUtils {

    public static RDBMSConfig rdbmsConfig;

    public static void setup(){
        rdbmsConfig = new RDBMSConfig("localhost",3306,"root","","otradb");
    }

    public static DBI CassandraDBIProvider(){
        /*setup();
        String dburl = "jdbc:mysql://" + rdbmsConfig.getDbHost() + ":"
                + rdbmsConfig.getDbPort() + "/"
                + rdbmsConfig.getDbName();

        DBI dbi = new DBI(dburl, rdbmsConfig.getUserName(), rdbmsConfig.getPassword());*/
        DBI dbi = new DBI("jdbc:cassandra://localhost:9160/cadredb");
        return dbi;
    }

    public static DBI MysqlDBIProvider(){
        setup();
        String dburl = "jdbc:mysql://" + rdbmsConfig.getDbHost() + ":"
                + rdbmsConfig.getDbPort() + "/"
                + rdbmsConfig.getDbName();

        DBI dbi = new DBI(dburl, rdbmsConfig.getUserName(), rdbmsConfig.getPassword());
        return dbi;
    }
}
