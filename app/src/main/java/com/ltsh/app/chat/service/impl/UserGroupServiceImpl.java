package com.ltsh.app.chat.service.impl;

import android.content.Context;

import com.ltsh.app.chat.config.AppConstants;
import com.ltsh.app.chat.service.UserGroupService;

/**
 * Created by Random on 2018/1/31.
 */

public class UserGroupServiceImpl extends PageBaseServiceImpl implements UserGroupService {
    public UserGroupServiceImpl(Context context) {
        super(context);
    }

    @Override
    protected String getPageUrl() {
        return AppConstants.GET_USER_GROUP_URL;
    }

}
