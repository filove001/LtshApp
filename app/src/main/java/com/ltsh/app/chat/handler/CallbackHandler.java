package com.ltsh.app.chat.handler;

import com.ltsh.app.chat.entity.common.Result;

/**
 * Created by Random on 2017/10/26.
 */

public interface CallbackHandler {
    public void callBack(Result result);
    public void error(Result result);
}
