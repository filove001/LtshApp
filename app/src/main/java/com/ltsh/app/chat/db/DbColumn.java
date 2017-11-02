package com.ltsh.app.chat.db;

/**
 * Created by Random on 2017/9/28.
 */

public class DbColumn {
    private String columnName;
    private String dbType;
    private String tableName;
    private boolean isPk;

    public boolean isPk() {
        return isPk;
    }

    public void setPk(boolean pk) {
        isPk = pk;
    }

    public DbColumn(String columnName, String dbType, String tableName, boolean isPk) {
        this.columnName = columnName;
        this.dbType = dbType;
        this.tableName = tableName;
        this.isPk = isPk;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }
}
