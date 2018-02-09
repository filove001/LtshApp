package com.ltsh.app.chat.utils;

import com.ltsh.app.chat.service.BaseService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Random on 2018/1/31.
 */

public class ServiceContextUtils {
    private static Map<String, BaseService> serviceMap;
    static {
        serviceMap = new HashMap<String, BaseService>();
    }
    public static void addService(Class classT,BaseService baseService) {
        serviceMap.put(classT.getSimpleName(), baseService);
    }
    public static <T extends BaseService> T getService(Class<T> classT) {
        return (T)serviceMap.get(classT.getSimpleName());
    }

    public static BaseService getService(String name) {
        return serviceMap.get(name);
    }
}
