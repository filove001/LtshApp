package com.ltsh.app.chat.handler;

import com.ltsh.app.chat.db.BaseDao;
import com.ltsh.app.chat.entity.BaseEntity;
import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.enums.ResultCodeEnum;
import com.ltsh.common.util.JsonUtils;
import com.ltsh.common.util.LogUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by Random on 2017/12/29.
 */

public class LoadEntityCallHandler {

    public void callBack(Result result, Class<? extends BaseEntity> tClass) {
        Result<Map> mapResult = result;
        if(ResultCodeEnum.SUCCESS.getCode().equals(mapResult.getCode())) {
            Map content = mapResult.getContent();
            List resultList = (List)content.get("resultList");
            for (Object obj : resultList) {
                final BaseEntity entity = JsonUtils.fromJson(JsonUtils.toJson(obj), tClass);
                BaseEntity single = single(entity);
                if(single == null) {
                    BaseDao.insert(entity);
                } else {
                    entity.setId(single.getId());
                    BaseDao.update(entity);
                }
            }
        } else {
            LogUtils.error(mapResult.getCode() + ":" + mapResult.getMessage());
        }
    }
    protected BaseEntity single(BaseEntity entity) {
        BaseEntity entity1 = BaseDao.getById(entity.getClass(), entity.getId());
        return entity1;
    }
}
