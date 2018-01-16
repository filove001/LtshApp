package com.ltsh.app.chat.dao;

import com.ltsh.app.chat.db.DBCipherHelper;
import com.ltsh.common.util.LogUtils;

import net.sqlcipher.SQLException;
import net.sqlcipher.database.SQLiteDatabase;

/**
 * Created by Random on 2018/1/16.
 */

public abstract class SqlExecute<T> {
    private DBCipherHelper dbhelper;
    public SqlExecute(DBCipherHelper dbhelper) {
        this.dbhelper = dbhelper;
    }
    public T execute() {
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = dbhelper.getWritableDatabase();
            return run(sqLiteDatabase);
        } catch (SQLException e) {
            LogUtils.error(e.getMessage(), e);
        } finally {
            sqLiteDatabase.close();
        }
        return null;
    }
    public abstract T run(SQLiteDatabase sqLiteDatabase);
}
