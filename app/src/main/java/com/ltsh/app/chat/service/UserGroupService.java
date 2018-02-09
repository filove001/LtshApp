package com.ltsh.app.chat.service;


import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.entity.req.PageReq;
import com.ltsh.app.chat.handler.CallbackHandler;

/**
 * Created by Random on 2018/1/31.
 */

public interface UserGroupService extends BaseService {
    /**
     * 分页查询
     * @param req
     * @param callbackHandler
     * @return
     */
    public Result page(PageReq req, CallbackHandler callbackHandler);
}
