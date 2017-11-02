package com.ltsh.app.chat.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ltsh.app.chat.CallBackInterface;
import com.ltsh.app.chat.adapter.FriendAdapter;
import com.ltsh.app.chat.adapter.MessageAdapter;
import com.ltsh.app.chat.R;
import com.ltsh.app.chat.config.AppConstants;
import com.ltsh.app.chat.db.DbUtils;
import com.ltsh.app.chat.entity.MessageInfo;
import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.entity.UserFriend;
import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.enums.ResultCodeEnum;
import com.ltsh.app.chat.listener.FriendItemClickListener;
import com.ltsh.app.chat.utils.AppHttpClient;
import com.ltsh.app.chat.utils.JsonUtils;
import com.ltsh.app.chat.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jay on 2015/8/30 0030.
 */
public class FriendFragment extends Fragment implements CallBackInterface {


    private ListView friend_list;
    private List<UserFriend> mData = null;

    public void callBack(Result result) {

        Result<Map> mapResult = result;
        if(ResultCodeEnum.SUCCESS.getCode().equals(mapResult.getCode())) {
            Map content = mapResult.getContent();
            List resultList = (List)content.get("resultList");
            for (Object obj : resultList) {
                final UserFriend userFriend = JsonUtils.fromJson(JsonUtils.toJson(obj), UserFriend.class);
                CacheObject.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        CacheObject.friendAdapter.add(0,userFriend);
                    }
                });
            }
        } else {
            LogUtils.e(mapResult.getCode() + ":" + mapResult.getMessage());
        }
    }
    public void initData() {
        List<UserFriend> userFriends = DbUtils.query(UserFriend.class, "create_by = ?", new String[]{"1"}, "id desc");

        for (UserFriend userFriend :
                userFriends) {
            mData.add(userFriend);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("pageNumber", "1");
        map.put("pageSize", "10000");
        AppHttpClient.threadPost(AppConstants.SERVLCE_URL, AppConstants.GET_FRIEND_URL, map, this, getActivity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.friend_content,container,false);
        mData = new ArrayList<UserFriend>();
        friend_list = (ListView) view.findViewById(R.id.friend_list);
        friend_list.setOnItemClickListener(new FriendItemClickListener(this.getActivity()));
        CacheObject.friendAdapter = new FriendAdapter(this.getActivity(),mData);
        friend_list.setAdapter(CacheObject.friendAdapter);
        initData();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }


}
