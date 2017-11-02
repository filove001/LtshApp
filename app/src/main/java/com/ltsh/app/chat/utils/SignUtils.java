package com.ltsh.app.chat.utils;




import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * Created by Random on 2017/9/29.
 */
public class SignUtils {
    public static String getSignStr(Object params) {
        Map map = JsonUtils.fromJson(JsonUtils.toJson(params), Map.class);
        Set<Object> strings = map.keySet();
        String[] paramsStrs = new String[strings.size()];
        strings.toArray(paramsStrs);
        Arrays.sort(paramsStrs);
        StringBuffer stringBuffer = new StringBuffer();
        for (String str : paramsStrs) {
            if(str.equals("class") || str.equals("signInfo")) {
                continue;
            }
            if(map.get(str) == null) {
                continue;
            }
            stringBuffer.append(str).append("=").append(map.get(str)).append("&");
        }
        return stringBuffer.substring(0,stringBuffer.length() - 1);
    }
    /**
     * 获取签名,签名方式md5(md5("key1=value1&key2=value2...." + key) + random),其中参数以字段排序
     * @param str 需要生成签名的字符串
     * @param key 密钥
     * @param random 随机数
     * @return
     */
    public static String getSign(String str, String key, String random){
        if(random == null) {
            random = "";
        }
        return MD5Util.encoder(MD5Util.encoder(str + key) + random);

    }
    public static String getSign(Map<String, String> params, String key, String random){
        String paramsStr = getSignStr(params);
        return getSign(paramsStr, key, random);
    }
}
