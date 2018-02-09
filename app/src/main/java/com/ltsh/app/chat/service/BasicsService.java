package com.ltsh.app.chat.service;

import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.entity.req.RandomReq;
import com.ltsh.app.chat.entity.resp.RandomResp;

/**
 * Created by Random on 2018/1/25.
 */

public interface BasicsService extends BaseService {
    public Result<RandomResp> getRandom();
}
