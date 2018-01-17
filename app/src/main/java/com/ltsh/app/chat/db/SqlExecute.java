package com.ltsh.app.chat.db;

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
    public abstract T execute(SQLiteDatabase sqLiteDatabase);
    public T run() {
        SQLiteDatabase sqLiteDatabase = null;

        try {
            sqLiteDatabase = dbhelper.getWritableDatabase();
            return execute(sqLiteDatabase);
        } catch (SQLException e) {
            LogUtils.error(e.getMessage(), e);
        } finally {
            sqLiteDatabase.close();
        }
        return null;
    }
}
