package com.ltsh.app.chat.ui.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ltsh.app.chat.R;
import com.ltsh.app.chat.adapter.FriendAdapter;
import com.ltsh.app.chat.config.AppConstants;
import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.entity.UserFriend;
import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.handler.CallbackHandler;
import com.ltsh.app.chat.handler.LoadEntityCallHandler;
import com.ltsh.app.chat.listener.FriendItemClickListener;
import com.ltsh.app.chat.utils.http.AppHttpClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jay on 2015/8/30 0030.
 */
public class UserInfoFragment extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.user_info,container,false);
        View viewById = view.findViewById(R.id.btn_sz);
        TextView user_name_text = view.findViewById(R.id.user_name_text);
        TextView login_name_text = view.findViewById(R.id.login_name_text);
        user_name_text.setText(CacheObject.userToken.getName());
        login_name_text.setText(CacheObject.userToken.getLoginName());
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "设置", Toast.LENGTH_LONG).show();
            }
        });
        //initData();
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
