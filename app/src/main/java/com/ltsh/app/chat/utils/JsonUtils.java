package com.ltsh.app.chat.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Random on 2017/9/27.
 */

public class JsonUtils {
        private static Gson gson = new GsonBuilder()
                    .setLenient()// json宽松
                    .enableComplexMapKeySerialization()//支持Map的key为复杂对象的形式
                    .serializeNulls() //智能null
                    .setPrettyPrinting()// 调教格式
                    .disableHtmlEscaping() //默认是GSON把HTML 转义的
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();
    public static String toJson(Object object){
        return JSONObject.toJSONString(object);
//        return gson.toJson(object);
    }
    public static <T> T fromJson(String jsonStr, Class<T> classT){
        return JSONObject.parseObject(jsonStr, classT);
//        return gson.fromJson(jsonStr, classT);
    }
}
