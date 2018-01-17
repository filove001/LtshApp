package com.ltsh.app.chat.db;

import android.content.ContentValues;



import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.utils.db.DbUtils;
import com.ltsh.app.chat.entity.BaseEntity;
import com.ltsh.app.chat.utils.BeanUtils;

import com.ltsh.common.util.LogUtils;
import com.ltsh.common.util.db.PropertyMethod;

import net.sqlcipher.Cursor;
import net.sqlcipher.SQLException;
import net.sqlcipher.database.SQLiteDatabase;

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
    private static DBCipherHelper dbhelper = CacheObject.dbHelper;
    public static Integer insert(final BaseEntity baseEntity) {
        final Class<?> aClass = baseEntity.getClass();
        return new SqlExecute<Integer>(dbhelper) {
            @Override
            public Integer execute(SQLiteDatabase sqLiteDatabase) {
                ContentValues contentValues = BeanUtils.beanToContentValues(baseEntity);
                contentValues.remove("id");
                long insert = sqLiteDatabase.insert(DbUtils.getTableName(aClass), null, contentValues);
                if(insert > 0) {
                    String sql = "select last_insert_rowid() from " + DbUtils.getTableName(aClass);
                    Cursor cursor = null;
                    try {
                        cursor = sqLiteDatabase.rawQuery(sql, null);
                        if(cursor.moveToFirst()){
                            baseEntity.setId(cursor.getInt(0));
                        }
                        return baseEntity.getId();
                    } catch (Exception e) {
                        LogUtils.error(e.getMessage(), e);
                    } finally {
                        close(cursor, null);
                    }
                }
                return null;
            }
        }.run();
    }
    public static int deleteById(final Class classT,final String id) {
        return new SqlExecute<Integer>(dbhelper) {
            @Override
            public Integer execute(SQLiteDatabase sqLiteDatabase) {
                return sqLiteDatabase.delete(DbUtils.getTableName(classT), "id=?", new String[]{id});
            }
        }.run();
    }


    public static int delete(final Class classT, final String whereClause, final String[] whereArgs) {
        return new SqlExecute<Integer>(dbhelper) {
            @Override
            public Integer execute(SQLiteDatabase sqLiteDatabase) {
                return sqLiteDatabase.delete(DbUtils.getTableName(classT), whereClause, whereArgs);
            }
        }.run();
    }
    public static void execSQL(final String sql, final Object[] args) {
        new SqlExecute<Integer>(dbhelper) {
            @Override
            public Integer execute(SQLiteDatabase sqLiteDatabase) {
                sqLiteDatabase.execSQL(sql, args);
                return 0;
            }
        }.run();
    }

    public static void update(final BaseEntity object) {
        new SqlExecute<Integer>(dbhelper) {
            @Override
            public Integer execute(SQLiteDatabase sqLiteDatabase) {
                ContentValues contentValues = BeanUtils.beanToContentValues(object);
                return sqLiteDatabase.update(DbUtils.getTableName(object.getClass()), contentValues, "id=?", new String[]{object.getId() + ""});
            }
        }.run();
    }
    public static <T> T getById(Class<T> classT, int id) {
        List<T> query = query(classT, "id = ?", new String[]{id + ""}, null);
        if(!query.isEmpty()) {
            return query.get(0);
        }
        return null;
    }
    public static List<Map> rawQueryMap(final String sql, final String[] params) {
        return new SqlExecute<List<Map>>(dbhelper) {
            @Override
            public List<Map> execute(SQLiteDatabase sqLiteDatabase) {
                List<Map> list = new ArrayList<>();
                Cursor cursor = null;
                try {
                    cursor = sqLiteDatabase.rawQuery(sql, params);
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
                } catch (SQLException e) {
                    LogUtils.error(e.getMessage(), e);
                } finally {
                    close(cursor, null);
                }
                return list;
            }
        }.run();
    }

    public static <T> List<T> rawQuery(final Class<T> classT, final String sql, final String[] params) {
        return new SqlExecute<List<T>>(dbhelper) {
            @Override
            public List<T> execute(SQLiteDatabase sqLiteDatabase) {
                List<T> list = new ArrayList<>();
                Cursor cursor = null;
                try {
                    cursor = sqLiteDatabase.rawQuery(sql, params);
                    list = cursorToList(classT, cursor);
                } catch (SQLException e) {
                    LogUtils.error(e.getMessage(), e);
                } finally {
                    close(cursor, null);
                }
                return list;
            }
        }.run();
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
        return query(classT, where,params, orderBy, null);
    }
    public static <T> List<T> query(final Class<T> classT, final String where, final String[] params, final String orderBy, final String limit) {
        return new SqlExecute<List<T>>(dbhelper) {
            @Override
            public List<T> execute(SQLiteDatabase sqLiteDatabase) {
                List<T> list = new ArrayList<>();
                Cursor cursor = null;
                try {
                    cursor = sqLiteDatabase.query(DbUtils.getTableName(classT), DbUtils.getColumns(classT), where, params, null, null, orderBy, limit);
                    list = cursorToList(classT, cursor);
                } catch (SQLException e) {
                    LogUtils.error(e.getMessage(), e);
                } finally {
                    close(cursor, null);
                }
                return list;
            }
        }.run();

    }
    public static <T> List<T> queryMyList(final Class<T> classT, final String orderBy) {

        return new SqlExecute<List<T>>(dbhelper) {
            @Override
            public List<T> execute(SQLiteDatabase sqLiteDatabase) {
                List<T> list = new ArrayList<>();
                Cursor cursor = null;
                try {
                    String where = "create_by=?";
                    String[] params = new String[]{CacheObject.userToken.getId() + ""};
                    cursor = sqLiteDatabase.query(DbUtils.getTableName(classT), DbUtils.getColumns(classT), where, params, null, null, orderBy);
                    list = cursorToList(classT, cursor);
                } catch (SQLException e) {
                    LogUtils.error(e.getMessage(), e);
                } finally {
                    close(cursor, null);
                }
                return list;
            }
        }.run();

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



    public static void close(Cursor cursor, SQLiteDatabase database) {
        if(cursor != null)
            cursor.close();
//        if(database != null)
//            database.close();
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
