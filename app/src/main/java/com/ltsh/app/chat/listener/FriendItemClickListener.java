package com.ltsh.app.chat.listener;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ltsh.app.chat.ui.activity.ChatActivity;
import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.entity.UserFriend;

import com.ltsh.common.util.JsonUtils;
import com.ltsh.common.util.LogUtils;

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
        LogUtils.info(String.format("position:%s,id:%s", position + "", id + ""));
        UserFriend item = (UserFriend)CacheObject.friendAdapter.getItem(position);
        Intent chatIntent = new Intent("android.intent.action.CHAT");
        chatIntent.putExtra("friendData", JsonUtils.toJson(item));
        chatIntent.setClassName(activity, ChatActivity.class.getName());

        activity.startActivity(chatIntent);

//        activity.finish();
    }
}
