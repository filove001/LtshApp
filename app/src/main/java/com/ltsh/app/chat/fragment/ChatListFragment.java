package com.ltsh.app.chat.fragment;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ltsh.app.chat.adapter.MessageAdapter;
import com.ltsh.app.chat.R;
import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.listener.ChatItemClickListener;

/**
 * Created by Jay on 2015/8/30 0030.
 */
public class ChatListFragment extends Fragment {

    private ListView list_content;


    public void initData() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initData();
        View view = inflater.inflate(R.layout.fg_content,container,false);

        list_content = (ListView) view.findViewById(R.id.list_content);
        if(CacheObject.messageAdapter == null) {
            CacheObject.messageAdapter = new MessageAdapter(this.getActivity());
        }
        list_content.setAdapter(CacheObject.messageAdapter);
        list_content.setOnItemClickListener(new ChatItemClickListener(getActivity()));
        return view;
    }
}
