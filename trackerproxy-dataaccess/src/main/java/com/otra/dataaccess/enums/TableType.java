package com.otra.dataaccess.enums;

public enum TableType {
    METRIC_MONTHLY("metric_monthly"),
    METRIC_DAILY("metric_daily"),
    METRIC_HOURLY("metric_hourly"),
    METRIC_TILL_DATE("metric_till_date");

    String tableName;

    TableType(String tableName) {
        this.tableName = tableName;
    }

    public static TableType fromString(String entityAndLevel) {
        return TableType.valueOf(entityAndLevel.toUpperCase());
    }

    public String toString() {
        return this.tableName;
    }

}
