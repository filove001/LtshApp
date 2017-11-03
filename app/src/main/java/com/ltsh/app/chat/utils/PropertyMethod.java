package com.ltsh.app.chat.utils;

import java.lang.reflect.Method;
import java.util.Locale;

/**
 * Created by Random on 2017/9/28.
 */


public class PropertyMethod {
    static final String GET_PREFIX = "get";
    static final String SET_PREFIX = "set";
    static final String IS_PREFIX = "is";
    private String propertyName;
    private Method readMethod;
    private Method writeMethod;
    private Class returnType;



    public PropertyMethod(String propertyName, Class<?> beanClass) {
        this.propertyName = propertyName;
        String readMethodName = null;
        String writeMethodName = SET_PREFIX + capitalize(propertyName);
        writeMethod = getMethod(beanClass, writeMethodName);
        returnType = writeMethod.getParameterTypes()[0];
        Class<?> type = returnType;
        if (type == boolean.class || type == null) {
            readMethodName = IS_PREFIX + capitalize(propertyName);
        } else {
            readMethodName = GET_PREFIX + capitalize(propertyName);
        }
        readMethod = getMethod(beanClass, readMethodName);
    }
    public static Method getMethod(Class<?> beanClass, String methodName) {
        Method[] methods = beanClass.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }

    public static String capitalize(String var0) {
        return var0 != null && var0.length() != 0?var0.substring(0, 1).toUpperCase(Locale.ENGLISH) + var0.substring(1):var0;
    }

    public Method getReadMethod() {
        return readMethod;
    }

    public void setReadMethod(Method readMethod) {
        this.readMethod = readMethod;
    }

    public Method getWriteMethod() {
        return writeMethod;
    }

    public void setWriteMethod(Method writeMethod) {
        this.writeMethod = writeMethod;
    }

    public Class getReturnType() {
        return returnType;
    }

    public void setReturnType(Class returnType) {
        this.returnType = returnType;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
}
