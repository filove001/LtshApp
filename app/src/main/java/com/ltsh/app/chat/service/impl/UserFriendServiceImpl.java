package com.ltsh.app.chat.service.impl;

import android.content.Context;

import com.ltsh.app.chat.config.AppConstants;
import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.entity.req.PageReq;
import com.ltsh.app.chat.entity.req.user.AddFriendReq;
import com.ltsh.app.chat.enums.ResultCodeEnum;
import com.ltsh.app.chat.handler.CallbackHandler;
import com.ltsh.app.chat.service.UserFriendService;

/**
 * Created by Random on 2018/1/31.
 */

public class UserFriendServiceImpl extends PageBaseServiceImpl implements UserFriendService {
    public UserFriendServiceImpl(Context context) {
        super(context);
    }

    @Override
    protected String getPageUrl() {
        return AppConstants.GET_USER_FRIEND_URL;
    }

    @Override
    public Result addFriend(AddFriendReq req, CallbackHandler callbackHandler) {
        super.enctyRequest(AppConstants.ADD_USER_FRIEND_URL, req, callbackHandler);
        return new Result<>(ResultCodeEnum.SUCCESS);
    }

}
