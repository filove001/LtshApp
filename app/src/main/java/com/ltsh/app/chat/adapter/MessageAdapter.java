package com.ltsh.app.chat.adapter;

/**
 * Created by Random on 2017/9/27.
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;

import com.ltsh.app.chat.R;
import com.ltsh.app.chat.entity.MessageInfo;
import com.ltsh.app.chat.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MessageAdapter extends BaseAdapter{


    private Context mContext;
    private List<Map> mData = null;


    public MessageAdapter(Context mContext, List<Map> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //多布局的核心，通过这个判断类别
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    //类别数目
    @Override
    public int getViewTypeCount() {
        return 2;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.message_item, parent, false);
            holder.txt_title = (TextView) convertView.findViewById(R.id.txt_title);
            holder.txt_content = (TextView) convertView.findViewById(R.id.txt_content);
            holder.txt_time = (TextView) convertView.findViewById(R.id.txt_time);
            holder.tab_menu_channel_num = (TextView) convertView.findViewById(R.id.tab_menu_channel_num);
            convertView.setTag(R.id.tag_message_info,holder);
        }else{
            holder = (ViewHolder) convertView.getTag(R.id.tag_message_info);
        }

        Object obj = mData.get(position);
        //设置下控件的值

        Map chatMessage = (Map) obj;
        if(chatMessage != null){
            holder.txt_title.setText((String)chatMessage.get("create_by_name"));
            holder.tab_menu_channel_num.setText(((int)chatMessage.get("FSZ") + (int)chatMessage.get("WD")) + "");
            String last_msg = (String) chatMessage.get("last_msg");
            if(last_msg != null) {
                holder.txt_content.setText(last_msg.split("_")[1]);
            }

            if(chatMessage.get("maxTime") != null) {
                holder.txt_time.setText((String)chatMessage.get("maxTime"));
            }
        }

        return convertView;
    }



    private static class ViewHolder{
        TextView txt_title;
        TextView txt_content;
        TextView txt_time;
        TextView tab_menu_channel_num;
    }

    //往特定位置，添加一个元素
    public void add(int position,Map data){
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.add(position,data);
        notifyDataSetChanged();
    }
}
