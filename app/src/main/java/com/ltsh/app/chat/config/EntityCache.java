package com.ltsh.app.chat.config;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.ltsh.app.chat.entity.BaseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Random on 2017/12/29.
 */

public class EntityCache {
    public static int maxMemory = (int) (Runtime.getRuntime().totalMemory()/1024);
    public static int cacheSize = maxMemory/8;
    public static LruCache<String, Map<Integer, BaseEntity>> entityCache = new LruCache<String, Map<Integer, BaseEntity>>(cacheSize);


    public static void init(Class entityClass, List<? extends BaseEntity> list){
        String key = getKey(entityClass);
        if(entityCache.get(key) == null) {
            Map<Integer, BaseEntity> map = new HashMap<>();
            entityCache.put(key, map);
        }
        Map<Integer, BaseEntity> integerTMap = entityCache.get(key);
        for (BaseEntity t : list) {
            integerTMap.put(t.getId(), t);
        }
    }
    public static <T extends BaseEntity> T get(Class<T> entityClass, Integer id) {
        String key = getKey(entityClass);
        return (T)entityCache.get(key).get(id);
    }

    public static void add(Class entityClass,BaseEntity t) {
        String key = getKey(entityClass);
        entityCache.get(key).put(t.getId(), t);
    }
    public static void remove(Class entityClass, Integer id) {
        String key = getKey(entityClass);
        entityCache.get(key).remove(id);
    }
    private static String getKey(Class entityClass){
        String simpleName = entityClass.getSimpleName();
        return simpleName;
    }
}
