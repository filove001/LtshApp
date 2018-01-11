package com.ltsh.app.chat.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ltsh.app.chat.handler.CallbackHandler;
import com.ltsh.app.chat.adapter.FriendAdapter;
import com.ltsh.app.chat.R;
import com.ltsh.app.chat.config.AppConstants;
import com.ltsh.app.chat.dao.BaseDao;
import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.entity.BaseEntity;
import com.ltsh.app.chat.entity.UserFriend;
import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.listener.FriendItemClickListener;
import com.ltsh.app.chat.service.LoadEntityCallSerivice;
import com.ltsh.app.chat.utils.http.AppHttpClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jay on 2015/8/30 0030.
 */
public class FriendFragment extends Fragment {


    private ListView friend_list;


    public void initData() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNumber", "1");
        map.put("pageSize", "10000");
        AppHttpClient.threadPost(AppConstants.SERVLCE_URL, AppConstants.GET_FRIEND_URL, map, getActivity(), new CallbackHandler(){
            @Override
            public void callBack(Result result) {
                LoadEntityCallSerivice loadEntityCallSerivice = new LoadEntityCallSerivice() {
                    @Override
                    protected BaseEntity single(BaseEntity entity) {
                        UserFriend entity1 = (UserFriend) entity;
                        UserFriend single = BaseDao.single(UserFriend.class, "friend_user_id=? and belongs_to=?", new String[]{entity1.getFriendUserId() + "", entity1.getCreateBy() + ""});
                        return single;
                    }
                };
                loadEntityCallSerivice.callBack(result, UserFriend.class);
            }

            @Override
            public void error(Result result) {

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
