package com.ltsh.app.chat.listener;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.ltsh.app.chat.R;
import com.ltsh.app.chat.activity.ChatActivity;
import com.ltsh.app.chat.activity.ContextActivity;
import com.ltsh.app.chat.config.AppConstants;
import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.db.DbUtils;
import com.ltsh.app.chat.entity.UserFriend;
import com.ltsh.app.chat.entity.UserToken;
import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.enums.ResultCodeEnum;
import com.ltsh.app.chat.utils.AppHttpClient;
import com.ltsh.app.chat.utils.JsonUtils;
import com.ltsh.app.chat.utils.LogUtils;
import com.ltsh.app.chat.utils.MD5Util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Random on 2017/10/13.
 */

public class FriendItemClickListener implements ListView.OnItemClickListener {
    private Activity activity;
    public FriendItemClickListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LogUtils.i(String.format("position:%s,id:%s", position + "", id + ""));
        UserFriend item = (UserFriend)CacheObject.friendAdapter.getItem(position);
        Intent chatIntent = new Intent("android.intent.action.CHAT");
        chatIntent.putExtra("friendData", JsonUtils.toJson(item));
        chatIntent.setClassName(activity, ChatActivity.class.getName());

        activity.startActivity(chatIntent);

//        activity.finish();
    }
}
