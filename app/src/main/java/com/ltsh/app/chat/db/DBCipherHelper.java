package com.ltsh.app.chat.db;

import android.content.Context;

import com.ltsh.app.chat.config.AppConstants;
import com.ltsh.app.chat.entity.MessageInfo;
import com.ltsh.app.chat.entity.UserFriend;
import com.ltsh.app.chat.entity.UserGroup;
import com.ltsh.app.chat.entity.UserGroupRel;
import com.ltsh.app.chat.entity.UserToken;
import com.ltsh.app.chat.utils.db.DbUtils;
import com.ltsh.common.util.LogUtils;
import com.ltsh.common.util.db.DbColumn;

import net.sqlcipher.SQLException;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import java.util.List;

/**
 * Created by Random on 2018/1/16.
 */

public class DBCipherHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "ltsh_db";//数据库名字
    public static final String DB_PWD="root";//数据库密码
    private static final int DB_VERSION = 1;   // 数据库版本

    public synchronized SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase(DB_PWD);
    }

    public DBCipherHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        //不可忽略的 进行so库加载
        SQLiteDatabase.loadLibs(context);
    }

    public DBCipherHelper(Context context) {
        this(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * 创建数据库
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表
        AppConstants.isInit = true;
        createTable(db, MessageInfo.class);
        createTable(db, UserFriend.class);
        createTable(db, UserGroup.class);
        createTable(db, UserGroupRel.class);
        createTable(db, UserToken.class);

    }

    public void createTable(SQLiteDatabase db, Class classTemp) {
        List<DbColumn> dbClounms = DbUtils.getDbColumns(classTemp);
        String createTable = DbUtils.getCreateTable(dbClounms);
        db.execSQL(createTable);
    }

    /**
     * 数据库升级
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}