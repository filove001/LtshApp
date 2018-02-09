package com.ltsh.app.chat.service.impl;

import android.content.Context;

import com.ltsh.app.chat.config.AppConstants;
import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.entity.req.PageReq;
import com.ltsh.app.chat.enums.ResultCodeEnum;
import com.ltsh.app.chat.handler.CallbackHandler;
import com.ltsh.app.chat.service.BaseService;
import com.ltsh.app.chat.service.UserGroupRelService;
import com.ltsh.app.chat.service.UserGroupService;

/**
 * Created by Random on 2018/1/31.
 */

public class UserGroupRelServiceImpl extends PageBaseServiceImpl implements UserGroupRelService {

    public UserGroupRelServiceImpl(Context context) {
        super(context);
    }

    @Override
    protected String getPageUrl() {
        return AppConstants.GET_USER_GROUP_REL_URL;
    }
}
