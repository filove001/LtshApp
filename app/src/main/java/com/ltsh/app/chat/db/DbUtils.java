package com.ltsh.app.chat.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ltsh.app.chat.entity.BaseEntity;
import com.ltsh.app.chat.utils.BeanUtils;
import com.ltsh.app.chat.utils.JsonUtils;
import com.ltsh.app.chat.utils.LogUtils;
import com.ltsh.app.chat.utils.PropertyMethod;


import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Random on 2017/9/27.
 */
public class DbUtils {

    public static void getTableInfo() {
        String str = "select * from sqlite_master where type='table';";
//        dbHelper.getWritableDatabase().query("sqlite_master",  "*", null, null, null, null, null);
    }
    public static MyDBOpenHelper dbHelper;

    public static void insert(Object object) {
        Class<?> aClass = object.getClass();
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
        writableDatabase.execSQL(getInsertSql(aClass), getValues(object));
    }
    public static void update(BaseEntity object) {
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = BeanUtils.beanToContentValues(object);
        writableDatabase.update(getTableName(object.getClass()), contentValues, "id=?", new String[]{object.getId() + ""});
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
            readableDatabase = dbHelper.getReadableDatabase();
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
            cursor.close();
            readableDatabase.close();
            return list;
        } catch (Exception e) {
            LogUtils.e(e.getMessage(), e);
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
            readableDatabase = dbHelper.getReadableDatabase();
            cursor = readableDatabase.rawQuery(sql, params);
            List<T> list = cursorToList(classT, cursor);
            return list;
        } catch (Exception e) {
            LogUtils.e(e.getMessage(), e);
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
            readableDatabase = dbHelper.getReadableDatabase();
            cursor = readableDatabase.query(getTableName(classT), DbUtils.getColumns(classT), where, params, null, null, orderBy);
            List<T> list = cursorToList(classT, cursor);
            return list;
        } catch (Exception e) {
            LogUtils.e(e.getMessage(), e);
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
                        String columnName = getColumnName(declaredField.getName(), 0);
                        setBeanValue(cursor, columnName, o, propertyMethod);
                    }
                    list.add((T)o);
                } catch (Exception e) {
                    LogUtils.e(e.getMessage(), e);
                }
            } while (cursor.moveToNext());
        }
        return list;
    }
    public static void setMapValue(Cursor cursor, Map map, String columnName) {
        setValue(cursor, columnName, 1, map, null);
    }
    public static void setBeanValue(Cursor cursor, String columnName, Object bean, PropertyMethod propertyMethod) {
        setValue(cursor, columnName, 0, bean, propertyMethod);
    }
    public static void setValue(Cursor cursor, String columnName, int type, Object bean, PropertyMethod propertyMethod) {
        int columnIndex = cursor.getColumnIndex(columnName);
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

    public static String[] getColumns(Class classT) {
        return getColumns(getDbColumns(classT));
    }
    public static String[] getColumns(List<DbColumn> dbColumns) {
        String[] columns = new String[dbColumns.size()];
        for (int i = 0; i < dbColumns.size(); i++) {
            columns[i] = dbColumns.get(i).getColumnName();
        }
        return columns;
    }
    public static Object[] getValues(Object object) {
        List<Object> list = new ArrayList<>();
        List<Field> declaredFields = BeanUtils.getFieldList(object.getClass());
        for (Field field:declaredFields) {
            if(field.getName().toUpperCase().equals("ID") || field.getAnnotation(NoDbColumn.class) != null) {
                continue;
            }
            try {
                PropertyMethod propertyMethod = new PropertyMethod(field.getName(), object.getClass());
                Object o = propertyMethod.getReadMethod().invoke(object);
                list.add(o);
            } catch (Exception e) {
                e.printStackTrace();
//                LogUtils.e(DbUtils.class.getName(),e.getMessage(), e);
            }
        }
        return list.toArray();
    }

    public static String getInsertSql(Class classT) {
        return getInsertSql(getDbColumns(classT));
    }
    public static String getInsertSql(List<DbColumn> dbColumns) {
        String str = "INSERT INTO";
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str).append(" ").append(dbColumns.get(0).getTableName()).append("(");
        StringBuffer columndSb = new StringBuffer();
        StringBuffer valueSb = new StringBuffer();
        for (DbColumn dbColumn:dbColumns) {
            if(dbColumn.isPk()) {

            } else {
                columndSb.append(dbColumn.getColumnName()).append(",");
                valueSb.append("?,");
            }

        }
        stringBuffer.append(columndSb.substring(0, columndSb.length() - 1)).append(") values(").append(valueSb.substring(0, valueSb.length() - 1)).append(")");

        return stringBuffer.toString();
    }


    public static String getCreateTable(List<DbColumn> dbColumns) {
        String str = "CREATE TABLE";
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str).append(" ").append(dbColumns.get(0).getTableName()).append("(");
        for (DbColumn dbColumn:dbColumns) {
            if(dbColumn.isPk()) {
                stringBuffer.append(dbColumn.getColumnName()).append(" ").append(dbColumn.getDbType()).append(" PRIMARY KEY AUTOINCREMENT").append(",");
            } else {
                stringBuffer.append(dbColumn.getColumnName()).append(" ").append(dbColumn.getDbType()).append(",");
            }
        }
        stringBuffer = new StringBuffer(stringBuffer.substring(0, stringBuffer.length() -1));
        stringBuffer.append(")");
        return stringBuffer.toString();
    }
    public static String getTableName(Class classT) {
        String name = classT.getSimpleName();
        return getColumnName(name, 1);
    }

    public static List<DbColumn> getDbColumns(Class classT) {
        List<Field> fieldList = BeanUtils.getFieldList(classT);

        String tableName = getTableName(classT);
        List<DbColumn> columns = new ArrayList<>();
        for (int i = 0; i < fieldList.size(); i++) {
            Field field = fieldList.get(i);
            String name = field.getName();

            NoDbColumn annotation = field.getAnnotation(NoDbColumn.class);
            if(annotation != null) {
                continue;
            }
            Class<?> declaringClass = field.getType();
            DbColumn dbColumn = new DbColumn(getColumnName(name, 0), getDbType(declaringClass), tableName, false);
            if(name.toLowerCase().equals("id")) {
                dbColumn.setPk(true);
            }
            if(dbColumn.isPk()) {
                columns.add(0,dbColumn);
            } else {
                columns.add(dbColumn);
            }


        }
        return columns;
    }


    public static String getColumnName(String filedName, int skip) {
        StringBuffer stringBuffer = new StringBuffer(filedName);
        for (int j = skip; j < stringBuffer.length(); j++) {
            String substring = stringBuffer.substring(j, j + 1);
            if(substring.equals(substring.toUpperCase())) {
                stringBuffer.replace(j, j+1,"_" + substring.toLowerCase());
//                    System.out.println(stringBuffer.substring(i,i+1));
            }
        }
        return stringBuffer.toString().toUpperCase();
    }

    public static String getDbType(Type toType) {
        String result = "";
        if(toType == Integer.class || toType == Integer.TYPE) {
            result = "INTEGER";
        } else if(toType == Double.class || toType == Double.TYPE) {
            result = "FLOAT";
        } else if(toType == Boolean.class || toType == Boolean.TYPE) {
            result = "INTEGER";
        } else if(toType == Byte.class || toType == Byte.TYPE) {
            result = "INTEGER";
        } else if(toType == Character.class || toType == Character.TYPE) {
            result = "VARCHAR(50)";
        } else if(toType == Short.class || toType == Short.TYPE) {
            result = "integer";
        } else if(toType == Long.class || toType == Long.TYPE) {
            result = "BIGINT";
        } else if(toType == Float.class || toType == Float.TYPE) {
            result = "FLOAT";
        } else if(toType == BigInteger.class) {
            result = "BIGINT";
        } else if(toType == BigDecimal.class) {
            result = "FLOAT";
        } else if(toType == String.class) {
            result = "VARCHAR(50)";
        } else if(toType == Date.class) {
            result = "DATETIME";
        } else {
            throw new RuntimeException("Don't know about " + toType);
        }
        return result;
    }


    public static void close(Closeable closeable) {
        if(closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                LogUtils.e(e.getMessage(), e);
            }
        }
    }
}

