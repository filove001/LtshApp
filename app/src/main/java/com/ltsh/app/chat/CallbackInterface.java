package com.ltsh.app.chat;

import com.ltsh.app.chat.entity.common.Result;

/**
 * Created by Random on 2017/10/26.
 */

public interface CallbackInterface {
    public void callBack(Result result);
    public void error(Result result);
}
