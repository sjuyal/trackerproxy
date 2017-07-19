package com.otra.nucleo.utils;

public class RDBMSConfig {

    private String dbHost;
    private int dbPort;
    private String userName;
    private String password;
    private String dbName;

    public RDBMSConfig(String dbHost, int dbPort, String userName, String password, String dbName) {
        this.dbHost = dbHost;
        this.dbPort = dbPort;
        this.userName = userName;
        this.password = password;
        this.dbName = dbName;
    }

    public String getDbHost() {
        return dbHost;
    }

    public int getDbPort() {
        return dbPort;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getDbName() {
        return dbName;
    }
}
