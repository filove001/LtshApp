package com.ltsh.app.chat.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ltsh.app.chat.CallBackInterface;
import com.ltsh.app.chat.adapter.FriendAdapter;
import com.ltsh.app.chat.R;
import com.ltsh.app.chat.config.AppConstants;
import com.ltsh.app.chat.dao.BaseDao;
import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.entity.UserFriend;
import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.enums.ResultCodeEnum;
import com.ltsh.app.chat.listener.FriendItemClickListener;
import com.ltsh.app.chat.utils.http.AppHttpClient;

import com.ltsh.common.util.JsonUtils;
import com.ltsh.common.util.LogUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jay on 2015/8/30 0030.
 */
public class FriendFragment extends Fragment {


    private ListView friend_list;

    public void callBack1(Result result) {

        Result<Map> mapResult = result;
        if(ResultCodeEnum.SUCCESS.getCode().equals(mapResult.getCode())) {
            Map content = mapResult.getContent();
            List resultList = (List)content.get("resultList");
            for (Object obj : resultList) {
                final UserFriend userFriend = JsonUtils.fromJson(JsonUtils.toJson(obj), UserFriend.class);
                UserFriend single = BaseDao.single(UserFriend.class, "friend_user_id=? and create_by=?", new String[]{userFriend.getFriendUserId() + "", userFriend.getCreateBy() + ""});
                if(single == null) {
                    BaseDao.insert(userFriend);
                } else {
                    userFriend.setId(single.getId());
                    BaseDao.update(userFriend);
                }
            }
        } else {
            LogUtils.error(mapResult.getCode() + ":" + mapResult.getMessage());
        }
    }
    public void initData() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNumber", "1");
        map.put("pageSize", "10000");
        AppHttpClient.threadPost(AppConstants.SERVLCE_URL, AppConstants.GET_FRIEND_URL, map, getActivity(), new CallBackInterface(){
            @Override
            public void callBack(Result result) {
                callBack1(result);
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.friend_content,container,false);
        friend_list = (ListView) view.findViewById(R.id.friend_list);
        friend_list.setOnItemClickListener(new FriendItemClickListener(this.getActivity()));
        if(CacheObject.friendAdapter == null) {
            CacheObject.friendAdapter = new FriendAdapter(this.getActivity());
            friend_list.setAdapter(CacheObject.friendAdapter);
        }
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
