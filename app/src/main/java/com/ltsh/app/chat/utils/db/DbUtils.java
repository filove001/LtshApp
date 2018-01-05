package com.ltsh.app.chat.utils.db;


import com.ltsh.app.chat.utils.BeanUtils;
import com.ltsh.app.chat.utils.Names;


import com.ltsh.common.util.LogUtils;
import com.ltsh.common.util.db.PropertyMethod;
import com.ltsh.common.util.db.DbColumn;
import com.ltsh.common.util.db.NoDbColumn;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Random on 2017/9/27.
 */
public class DbUtils {

    public static void getTableInfo() {
        String str = "select * from sqlite_master where type='table';";
//        dbHelper.getWritableDatabase().query("sqlite_master",  "*", null, null, null, null, null);
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
//            if(field.getName().toUpperCase().equals("ID") || field.getAnnotation(NoDbColumn.class) != null) {
//                continue;
//            }
            try {
                PropertyMethod propertyMethod = new PropertyMethod(field.getName(), object.getClass());
                Object o = propertyMethod.getReadMethod().invoke(object);
                list.add(o);
            } catch (Exception e) {
                LogUtils.error(e.getMessage(), e);
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
//            if(dbColumn.isPk()) {
//
//            } else {
            columndSb.append(dbColumn.getColumnName()).append(",");
            valueSb.append("?,");
//            }

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
                stringBuffer.append(dbColumn.getColumnName()).append(" ").append(dbColumn.getDbType()).append(" PRIMARY KEY ").append(",");
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
        return getColumnName(name);
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
            DbColumn dbColumn = new DbColumn(getColumnName(name), getDbType(declaringClass), tableName, false);
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


    public static String getColumnName(String filedName) {
        return Names.toUnderlineName(filedName).toUpperCase();
//        StringBuffer stringBuffer = new StringBuffer(filedName);
//        for (int j = skip; j < stringBuffer.length(); j++) {
//            String substring = stringBuffer.substring(j, j + 1);
//            if(substring.equals(substring.toUpperCase())) {
//                stringBuffer.replace(j, j+1,"_" + substring.toLowerCase());
////                    System.out.println(stringBuffer.substring(i,i+1));
//            }
//        }
//        return stringBuffer.toString().toUpperCase();
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



}

