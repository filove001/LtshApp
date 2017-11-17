package com.ltsh.app.chat.listener;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ltsh.app.chat.activity.ChatActivity;
import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.entity.UserFriend;
import com.ltsh.app.chat.entity.viewbean.MessageItem;

import com.ltsh.common.util.JsonUtils;

/**
 * Created by Random on 2017/10/13.
 */

public class ChatItemClickListener implements ListView.OnItemClickListener {
    private Activity activity;
    public ChatItemClickListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MessageItem item = (MessageItem)CacheObject.messageAdapter.getItem(position);
        UserFriend userFriend = new UserFriend();
        userFriend.setName(item.getCreateByName());
        userFriend.setFriendUserId(item.getCreateBy());
//        userFriend.setFriendUserId(item.get);
        Intent chatIntent = new Intent("android.intent.action.CHAT");
        chatIntent.putExtra("friendData", JsonUtils.toJson(userFriend));
        chatIntent.setClassName(activity, ChatActivity.class.getName());

        activity.startActivity(chatIntent);

//        activity.finish();
    }
}
