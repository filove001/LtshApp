package com.ltsh.app.chat.service.impl;

import com.ltsh.app.chat.config.AppConstants;
import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.entity.req.AppReq;
import com.ltsh.app.chat.entity.req.RandomReq;
import com.ltsh.app.chat.entity.resp.RandomResp;
import com.ltsh.app.chat.service.BasicsService;
import com.ltsh.app.chat.utils.BeanUtils;
import com.ltsh.app.chat.utils.http.AppHttpClient;
import com.ltsh.common.util.JsonUtils;
import com.ltsh.common.util.LogUtils;
import com.ltsh.common.util.StringUtils;
import com.ltsh.common.util.security.AES;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Random on 2018/1/25.
 */

public class BasicsServiceImpl implements BasicsService {
    @Override
    public Result<RandomResp> getRandom() {
        String uuid = StringUtils.getUUID();
        AppReq<String> appReq = new AppReq<String>(CacheObject.baseReq, uuid);
        String baseUrl = AppConstants.SERVLCE_URL;
        String url = AppConstants.GET_RANDOM_URL;
//        Map<String, Object> params = new HashMap<>();
//        params.putAll(JsonUtils.fromJson(JsonUtils.toJson(CacheObject.baseReq), Map.class));
//
//        String uuid = StringUtils.getUUID();
//        params.put("uuid", uuid);
        String post = AppHttpClient.postJson(baseUrl, url, appReq, "");
        LogUtils.info("返回参数:" + post);
        Map map = JsonUtils.fromJson(post, Map.class);
        if(!map.get("code").equals("000000")) {
            return new Result<>((String)map.get("code"), (String)map.get("message"));
        }
        Map content = (Map)map.get("content");
        String randomKey = (String)content.get("randomKey");
        String randomValue = (String) content.get("randomValue");
        String[] str = new String[2];
        try {
            str[0] = AES.decrypt(randomKey, uuid);
            str[1] = AES.decrypt(randomValue, uuid);
        } catch (Exception e) {
            LogUtils.error(e.getMessage(), e);
        }
        return new Result<>(new RandomResp(str[0], str[1]));
    }
}
