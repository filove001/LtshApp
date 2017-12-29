package com.ltsh.app.chat.config;

import com.ltsh.app.chat.entity.BaseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Random on 2017/12/29.
 */

public class BaseCache {
    public static Map<String, Map<Integer, BaseEntity>> cacheMap = new HashMap<>();

    public static void init(Class entityClass, List<? extends BaseEntity> list){
        String key = getKey(entityClass);
        if(cacheMap.get(key) == null) {
            Map<Integer, BaseEntity> map = new HashMap<>();
            cacheMap.put(key, map);
        }
        Map<Integer, BaseEntity> integerTMap = cacheMap.get(key);
        for (BaseEntity t : list) {
            integerTMap.put(t.getId(), t);
        }
    }
    public static <T extends BaseEntity> T get(Class<T> entityClass, Integer id) {
        String key = getKey(entityClass);
        return (T)cacheMap.get(key).get(id);
    }
    public static void add(Class entityClass,BaseEntity t) {
        String key = getKey(entityClass);
        cacheMap.get(key).put(t.getId(), t);
    }
    public static void remove(Class entityClass, Integer id) {
        String key = getKey(entityClass);
        cacheMap.get(key).remove(id);
    }
    private static String getKey(Class entityClass){
        String simpleName = entityClass.getSimpleName();
        return simpleName;
    }
}
