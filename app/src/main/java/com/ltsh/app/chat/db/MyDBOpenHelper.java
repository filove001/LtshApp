package com.ltsh.app.chat.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.ltsh.app.chat.entity.MessageInfo;
import com.ltsh.app.chat.entity.UserFriend;
import com.ltsh.app.chat.entity.UserToken;
import com.ltsh.app.chat.utils.LogUtils;

import java.util.List;

/**
 * Created by Random on 2017/9/27.
 */

public class MyDBOpenHelper extends SQLiteOpenHelper {

    public MyDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {super(context, name, factory, version); }
    @Override
    //数据库第一次创建时被调用
    public void onCreate(SQLiteDatabase db) {
        List<DbColumn> dbClounms = DbUtils.getDbColumns(MessageInfo.class);
        String createTable = DbUtils.getCreateTable(dbClounms);

        db.execSQL(createTable);
        dbClounms = DbUtils.getDbColumns(UserFriend.class);
        createTable = DbUtils.getCreateTable(dbClounms);
        db.execSQL(createTable);

        dbClounms = DbUtils.getDbColumns(UserToken.class);
        createTable = DbUtils.getCreateTable(dbClounms);
        db.execSQL(createTable);
    }
    //软件版本号发生改变时调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("ALTER TABLE person ADD phone VARCHAR(12) NULL");
        LogUtils.i("oldVersion:" + oldVersion + "newVersion:" + newVersion);

    }
}