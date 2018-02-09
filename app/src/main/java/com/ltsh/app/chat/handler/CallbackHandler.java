package com.ltsh.app.chat.handler;

import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.entity.req.AppReq;

/**
 * Created by Random on 2017/10/26.
 */

public interface CallbackHandler {
    /**
     * 请求之前
     * @param appReq
     */
    public void before(final AppReq appReq);
    /**
     * 成功
     * @param result
     */
    public void succeed(final Result result);

    /**
     * 失败
     * @param result
     */
    public void fail(final Result result);

    /**
     * 调用完成回调,无论成功或者失败都调用
     * @param result
     */
    public void complete(final Result result);
}
