package com.ltsh.app.chat.fragment;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ltsh.app.chat.adapter.MessageAdapter;
import com.ltsh.app.chat.R;
import com.ltsh.app.chat.db.DbUtils;
import com.ltsh.app.chat.entity.MessageInfo;
import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.listener.FriendItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Jay on 2015/8/30 0030.
 */
public class ChatListFragment extends Fragment {

    private ListView list_content;
    private List<Map> mData = null;


    public void initData() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initData();
        View view = inflater.inflate(R.layout.fg_content,container,false);
        String sql = "SELECT " +
                "msg.create_by," +
                "msg.create_by_name," +
                "SUM(CASE WHEN msg.status = 'FSZ' THEN 1 ELSE 0 END) FSZ," +
                "SUM(CASE WHEN msg.status = 'WD' THEN 1 ELSE 0 END) WD," +
                "SUM(CASE WHEN msg.status = 'YD' THEN 1 ELSE 0 END) YD," +
                "strftime('YYYY-MM-DD HH:MM:SS',MAX(msg.create_time)) maxTime, " +
                "MAX(id || '_' || msg.`msg_context`) last_msg " +
                "FROM message_info msg " +
                "WHERE msg.to_user=? " +
                "GROUP BY msg.create_by,msg.create_by_name";
        List<Map> maps = DbUtils.rawQueryMap(sql, new String[]{CacheObject.userToken.getId() + ""});
        mData = new ArrayList<Map>();
        for (Map map : maps) {
            mData.add(map);
        }
        list_content = (ListView) view.findViewById(R.id.list_content);
        CacheObject.messageAdapter = new MessageAdapter(this.getActivity(),maps);
        list_content.setAdapter(CacheObject.messageAdapter);

        return view;
    }
}
