package com.ltsh.app.chat.utils;

import android.content.ContentValues;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.R.attr.value;

/**
 * Created by Random on 2017/11/3.
 */

public class BeanUtils {
    public static Map<String, Object> beanToMap(Object source) {
        Map<String, Object> map = new HashMap<>();
        List<Field> sourceFields = getFieldList(source.getClass());
        for (int i = 0; i < sourceFields.size(); i++) {
            Field sourceField = sourceFields.get(i);
            PropertyMethod sourcePropertyMethod = new PropertyMethod(sourceField.getName(), source.getClass());
            try {
                Object invoke = sourcePropertyMethod.getReadMethod().invoke(source);
                map.put(sourceField.getName(), invoke);
            } catch (Exception e) {
                LogUtils.e(e.getMessage(), e);
            }
        }
        return map;
    }
    public static ContentValues beanToContentValues(Object source) {
        ContentValues contentValues = new ContentValues();
        List<Field> sourceFields = getFieldList(source.getClass());
        for (int i = 0; i < sourceFields.size(); i++) {
            Field sourceField = sourceFields.get(i);
            PropertyMethod sourcePropertyMethod = new PropertyMethod(sourceField.getName(), source.getClass());
            try {
                Object invoke = sourcePropertyMethod.getReadMethod().invoke(source);
                setValue(contentValues, PropertyMethod.getMethod(contentValues.getClass(), "pull"), invoke, sourcePropertyMethod.getReturnType());
            } catch (Exception e) {
                LogUtils.e(e.getMessage(), e);
            }
        }
        return contentValues;
    }
    public static void copyProperties(Object source, Object target) {
        List<Field> sourceFields = getFieldList(source.getClass());
        List<Field> targetFields = getFieldList(target.getClass());
        for (int i = 0; i < sourceFields.size(); i++) {
            Field sourceField = sourceFields.get(i);
            for (int j = 0; j < targetFields.size(); j++) {
                Field targetField = targetFields.get(i);
                if(sourceField.getName().equals(targetField.getName())) {
                    PropertyMethod sourcePropertyMethod = new PropertyMethod(sourceField.getName(), source.getClass());
                    PropertyMethod targetPropertyMethod = new PropertyMethod(sourceField.getName(), target.getClass());
                    try {
                        Object invoke = sourcePropertyMethod.getReadMethod().invoke(source);
                        targetPropertyMethod.getWriteMethod().invoke(target, invoke);
                    } catch (Exception e) {
                        LogUtils.e(e.getMessage(), e);
                    }
                    break;
                }
            }
        }
    }
    public static List<Field> getFieldList(Class classT) {
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
    public static void setValue(PropertyMethod propertyMethod, Object obj, Object value) {
        Class toType = propertyMethod.getReturnType();
        Method method = propertyMethod.getWriteMethod();
        setValue(obj, method, value, toType);
    }
    public static void setValue(Object obj, Method method, Object value, Class toType) {
        try {
            if(toType == Integer.class || toType == Integer.TYPE) {
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
            LogUtils.e(e.getMessage(), e);
        }
    }

}
