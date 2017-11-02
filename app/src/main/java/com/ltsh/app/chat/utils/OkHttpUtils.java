package com.ltsh.app.chat.utils;


import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.enums.ResultCodeEnum;

import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Random on 2017/09/06.
 */
public class OkHttpUtils {

    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/octet-stream; charset=utf-8");
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    static OkHttpClient client = new OkHttpClient.Builder().readTimeout(70L, TimeUnit.SECONDS).writeTimeout(70L, TimeUnit.SECONDS).connectTimeout(70L, TimeUnit.SECONDS).build();

    public static String post(String url, Map<String, Object> json) {
        return post(url, json, null);
    }
    public static String post(String url, Map<String, Object> json, Map<String, List<File>> fileMap) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        for (String key: json.keySet()) {
            if(json.get(key) != null) {
                builder.addFormDataPart(key, String.valueOf(json.get(key)));
            }
        }
        if(fileMap != null) {
            for (String key : fileMap.keySet()) {
                List<File> files = fileMap.get(key);
                if (files != null) {
                    for (File file : files) {
                        builder.addFormDataPart("file", file.getName(),
                                RequestBody.create(MEDIA_TYPE_MARKDOWN, file));
                    }
                }
            }
        }

        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Unexpected code " + response);
            }
        } catch (Exception e) {
            LogUtils.e(e.getMessage(), e);
        } finally {
            if(response != null) {
                response.close();
            }
        }

        return JsonUtils.toJson(new Result(ResultCodeEnum.REQUEST_ERROR));
    }
}
