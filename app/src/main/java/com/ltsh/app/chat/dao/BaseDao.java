package com.ltsh.app.chat.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.utils.db.DbUtils;
import com.ltsh.app.chat.entity.BaseEntity;
import com.ltsh.app.chat.utils.BeanUtils;

import com.ltsh.common.util.LogUtils;
import com.ltsh.common.util.db.PropertyMethod;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Random on 2017/11/7.
 */

public class BaseDao {

    public static int insert(Object object) {
        Class<?> aClass = object.getClass();

        int result = -1;
        Cursor cursor = null;
        SQLiteDatabase writableDatabase = null;
        try {
            writableDatabase = CacheObject.dbHelper.getWritableDatabase();
            ContentValues contentValues = BeanUtils.beanToContentValues(object);
            contentValues.remove("id");
            long insert = writableDatabase.insert(DbUtils.getTableName(aClass), null, contentValues);
            if(insert > 1) {
                String sql = "select last_insert_rowid() from " + DbUtils.getTableName(aClass);
                cursor = writableDatabase.rawQuery(sql, null);
                if(cursor.moveToFirst()){
                    result = cursor.getInt(0);
                    cursor.close();
                }
            }

        } catch (Exception e) {
            LogUtils.error(e.getMessage(), e);
        } finally {
            close(cursor);
            close(writableDatabase);
        }

        return result;
    }
    public static int deleteById(Class classT,String id) {
        SQLiteDatabase writableDatabase = null;
        try {
            writableDatabase = CacheObject.dbHelper.getWritableDatabase();
            return writableDatabase.delete(DbUtils.getTableName(classT), "id=?", new String[]{id});
        } catch (Exception e) {
            LogUtils.error(e.getMessage(), e);
        } finally {
            close(writableDatabase);
        }
        return -1;
    }
    public static int delete(Class classT, String whereClause, String[] whereArgs) {
        SQLiteDatabase writableDatabase = null;
        try {
            writableDatabase = CacheObject.dbHelper.getWritableDatabase();
            return writableDatabase.delete(DbUtils.getTableName(classT), whereClause, whereArgs);
        } catch (Exception e) {
            LogUtils.error(e.getMessage(), e);
        } finally {
            close(writableDatabase);
        }
        return -1;
    }
    public static void execSQL(String sql, Object[] args) {
        SQLiteDatabase writableDatabase = null;
        try {
            writableDatabase = CacheObject.dbHelper.getWritableDatabase();
            writableDatabase.execSQL(sql, args);
        } catch (Exception e) {
            LogUtils.error(e.getMessage(), e);
        } finally {
            close(writableDatabase);
        }
    }

    public static void update(BaseEntity object) {
        SQLiteDatabase writableDatabase = null;
        try {
            writableDatabase = CacheObject.dbHelper.getWritableDatabase();
            ContentValues contentValues = BeanUtils.beanToContentValues(object);
            writableDatabase.update(DbUtils.getTableName(object.getClass()), contentValues, "id=?", new String[]{object.getId() + ""});
        } catch (Exception e) {
            LogUtils.error(e.getMessage(), e);
        } finally {
            close(writableDatabase);
        }
    }
    public static <T> T getById(Class<T> classT, int id) {
        List<T> query = query(classT, "id = ?", new String[]{id + ""}, null);
        if(!query.isEmpty()) {
            return query.get(0);
        }
        return null;
    }
    public static List<Map> rawQueryMap(String sql, String[] params) {
        SQLiteDatabase readableDatabase = null;
        Cursor cursor = null;
        try {
            List<Map> list = new ArrayList<>();
            readableDatabase = CacheObject.dbHelper.getReadableDatabase();
            cursor = readableDatabase.rawQuery(sql, params);
            if (cursor.moveToFirst()) {
                do {
                    String[] columnNames = cursor.getColumnNames();
                    Map<String, Object> map = new HashMap<>();
                    for (String columnName : columnNames) {
                        setMapValue(cursor, map, columnName);
                    }
                    list.add(map);
                } while (cursor.moveToNext());
            }
            return list;
        } catch (Exception e) {
            LogUtils.error(e.getMessage(), e);
        } finally {
            close(cursor);
            close(readableDatabase);
        }
        return new ArrayList<>();
    }

    public static <T> List<T> rawQuery(Class<T> classT, String sql, String[] params) {
        SQLiteDatabase readableDatabase = null;
        Cursor cursor = null;
        try {
            readableDatabase = CacheObject.dbHelper.getReadableDatabase();
            cursor = readableDatabase.rawQuery(sql, params);
            List<T> list = cursorToList(classT, cursor);
            return list;
        } catch (Exception e) {
            LogUtils.error(e.getMessage(), e);
        } finally {
            close(cursor);
            close(readableDatabase);
        }
        return new ArrayList<>();
    }
    public static <T> T single(Class<T> classT, String where, String[] params) {
        List<T> query = query(classT, where, params, null);
        if(query.isEmpty()) {
            return null;
        } else {
            return query.get(0);
        }
    }
    public static <T> List<T> query(Class<T> classT, String where, String[] params, String orderBy) {
        SQLiteDatabase readableDatabase = null;
        Cursor cursor = null;
        try {
            readableDatabase = CacheObject.dbHelper.getReadableDatabase();
            cursor = readableDatabase.query(DbUtils.getTableName(classT), DbUtils.getColumns(classT), where, params, null, null, orderBy);
            List<T> list = cursorToList(classT, cursor);
            return list;
        } catch (Exception e) {
            LogUtils.error(e.getMessage(), e);
        } finally {
            close(cursor);
            close(readableDatabase);
        }
        return new ArrayList<>();
    }

    public static <T> List<T> queryMyList(Class<T> classT, String orderBy) {
        SQLiteDatabase readableDatabase = null;
        Cursor cursor = null;
        try {
            readableDatabase = CacheObject.dbHelper.getReadableDatabase();
            String where = "create_by=?";
            String[] params = new String[]{CacheObject.userToken.getId() + ""};
            cursor = readableDatabase.query(DbUtils.getTableName(classT), DbUtils.getColumns(classT), where, params, null, null, orderBy);
            List<T> list = cursorToList(classT, cursor);
            return list;
        } catch (Exception e) {
            LogUtils.error(e.getMessage(), e);
        } finally {
            close(cursor);
            close(readableDatabase);
        }
        return new ArrayList<>();
    }
    public static <T> List<T> cursorToList(Class classT, Cursor cursor) {
        List<T> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                try {
                    Object o = classT.newInstance();
                    List<Field> declaredFields = BeanUtils.getFieldList(classT);
                    for (int i = 0; i < declaredFields.size(); i++) {
                        Field declaredField = declaredFields.get(i);
                        PropertyMethod propertyMethod = new PropertyMethod(declaredField.getName(), classT);
                        String columnName = DbUtils.getColumnName(declaredField.getName());
                        setBeanValue(cursor, columnName, o, propertyMethod);
                    }
                    list.add((T)o);
                } catch (Exception e) {
                    LogUtils.error(e.getMessage(), e);
                }
            } while (cursor.moveToNext());
        }
        return list;
    }
    public static void close(Closeable closeable) {
        if(closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                LogUtils.info(e.getMessage(), e);
            }
        }
    }


    public static void setMapValue(Cursor cursor, Map map, String columnName) {
        setValue(cursor, columnName, 1, map, null);
    }
    public static void setBeanValue(Cursor cursor, String columnName, Object bean, PropertyMethod propertyMethod) {
        setValue(cursor, columnName, 0, bean, propertyMethod);
    }
    public static void setValue(Cursor cursor, String columnName, int type, Object bean, PropertyMethod propertyMethod) {
        int columnIndex = cursor.getColumnIndex(columnName);
        if(columnIndex == -1) {
            return;
        }
        switch (cursor.getType(columnIndex)) {
            case Cursor.FIELD_TYPE_BLOB:
                if(type == 0) {
                    BeanUtils.setValue(propertyMethod, bean, cursor.getBlob(columnIndex));
                } else if(type == 1) {
                    ((Map)bean).put(columnName, cursor.getBlob(columnIndex));
                }
                break;
            case Cursor.FIELD_TYPE_FLOAT:
                if(type == 0) {
                    BeanUtils.setValue(propertyMethod, bean, cursor.getFloat(columnIndex));
                } else if(type == 1) {
                    ((Map)bean).put(columnName, cursor.getFloat(columnIndex));
                }
                break;
            case Cursor.FIELD_TYPE_INTEGER:
                if(type == 0) {
                    BeanUtils.setValue(propertyMethod, bean, cursor.getInt(columnIndex));
                } else if(type == 1) {
                    ((Map)bean).put(columnName, cursor.getInt(columnIndex));
                }
                break;
            case Cursor.FIELD_TYPE_NULL:
                break;
            case Cursor.FIELD_TYPE_STRING:
                if(type == 0) {
                    BeanUtils.setValue(propertyMethod, bean, cursor.getString(columnIndex));
                } else if(type == 1) {
                    ((Map)bean).put(columnName, cursor.getString(columnIndex));
                }
                break;
        }
    }
}
