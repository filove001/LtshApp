package com.ltsh.app.chat.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
                        int columnIndex = cursor.getColumnIndex(columnName);
                        switch (cursor.getType(columnIndex)) {
                            case Cursor.FIELD_TYPE_BLOB:
                                map.put(columnName, cursor.getBlob(columnIndex));
                                break;
                            case Cursor.FIELD_TYPE_FLOAT:
                                map.put(columnName, cursor.getFloat(columnIndex));
                                break;
                            case Cursor.FIELD_TYPE_INTEGER:
                                map.put(columnName, cursor.getInt(columnIndex));
                                break;
                            case Cursor.FIELD_TYPE_NULL:
                                break;
                            case Cursor.FIELD_TYPE_STRING:
                                map.put(columnName, cursor.getString(columnIndex));
                                break;
                        }
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
    public static <T> List<T> query(Class<T> classT, String where, String[] params, String orderBy) {
        SQLiteDatabase readableDatabase = null;
        Cursor cursor = null;
        try {
            List<T> list = new ArrayList<>();
            readableDatabase = dbHelper.getReadableDatabase();
            cursor = readableDatabase.query(getTableName(classT), DbUtils.getColumns(classT), where, params, null, null, orderBy);

            if (cursor.moveToFirst()) {
                do {
                    try {
                        Object o = classT.newInstance();
                        List<Field> declaredFields = getFieldList(classT);
                        for (int i = 0; i < declaredFields.size(); i++) {
                            Field declaredField = declaredFields.get(i);
                            PropertyMethod propertyMethod = new PropertyMethod(declaredField.getName(), classT);
                            Class returnType = propertyMethod.getReturnType();

                            String columnName = getColumnName(declaredField.getName(), 0);
                            int columnIndex = cursor.getColumnIndex(columnName);
                            switch (cursor.getType(columnIndex)) {
                                case Cursor.FIELD_TYPE_BLOB:
                                    setValue(returnType, propertyMethod.getWriteMethod(), o, cursor.getBlob(columnIndex));
                                    break;
                                case Cursor.FIELD_TYPE_FLOAT:
                                    setValue(returnType, propertyMethod.getWriteMethod(), o, cursor.getFloat(columnIndex));
                                    break;
                                case Cursor.FIELD_TYPE_INTEGER:
                                    setValue(returnType, propertyMethod.getWriteMethod(), o, cursor.getInt(columnIndex));
                                    break;
                                case Cursor.FIELD_TYPE_NULL:
                                    break;
                                case Cursor.FIELD_TYPE_STRING:
                                    setValue(returnType, propertyMethod.getWriteMethod(), o, cursor.getString(columnIndex));
                                    break;
                            }

                        }
                        list.add((T)o);
                    } catch (Exception e) {
                        LogUtils.e(e.getMessage(), e);
                    }

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
        List<Field> declaredFields = getFieldList(object.getClass());
        for (Field field:declaredFields) {
            if(field.getName().toLowerCase().equals("id") || field.getAnnotation(NoDbColumn.class) != null) {
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
        return getColumnName(name, 1).toLowerCase();
    }
    private static List<Field> getFieldList(Class classT) {
        Class tempClass = classT;
        List<Field> fieldList = new ArrayList<>();
        while (tempClass != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
            Field[] declaredFields = tempClass.getDeclaredFields();
            for (Field field :
                    declaredFields) {
                if (field.getName().equals("serialVersionUID") || field.getName().equals("$change") || field.getName().indexOf("shadow$") != -1) {
                    continue;
                }
                fieldList.add(field);
            }
//            fieldList.addAll(Arrays.asList(tempClass .getDeclaredFields()));
            tempClass = tempClass.getSuperclass(); //得到父类,然后赋给自己
        }
        return fieldList;
    }
    public static List<DbColumn> getDbColumns(Class classT) {
        List<Field> fieldList = getFieldList(classT);

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
        return stringBuffer.toString();
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

    public static void setValue(Class toType, Method method, Object obj, Object value) {

        try {
            if(toType.isArray() && value != null && value.getClass().isArray()) {
                method.invoke(obj, value);
            }else if(toType == Integer.class || toType == Integer.TYPE) {
                method.invoke(obj, (Integer)value);
            } else if(toType == Double.class || toType == Double.TYPE) {
                method.invoke(obj, ((Number)value).doubleValue());
            } else if(toType == Boolean.class || toType == Boolean.TYPE) {
                method.invoke(obj, ((Number)value).intValue() == 0 ? false : true);
            } else if(toType == Byte.class || toType == Byte.TYPE) {
                method.invoke(obj, ((Number)value).byteValue());
            } else if(toType == Character.class || toType == Character.TYPE) {
                method.invoke(obj, ((String)value));
            } else if(toType == Short.class || toType == Short.TYPE) {
                method.invoke(obj, ((Number)value).shortValue());
            } else if(toType == Long.class || toType == Long.TYPE) {
                method.invoke(obj, ((Number)value).longValue());
            } else if(toType == Float.class || toType == Float.TYPE) {
                method.invoke(obj, ((Number)value).floatValue());
            } else if(toType == BigInteger.class) {
                throw new RuntimeException("Don't know about " + toType);
            } else if(toType == BigDecimal.class) {
                method.invoke(obj, new BigDecimal(value + ""));
            } else if(toType == String.class) {
                method.invoke(obj, ((String)value));
            } else {
                throw new RuntimeException("Don't know about " + toType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

