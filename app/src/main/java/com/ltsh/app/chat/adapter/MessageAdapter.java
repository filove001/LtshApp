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
import com.ltsh.app.chat.entity.viewbean.MessageItem;
import com.ltsh.app.chat.utils.BeanUtils;
import com.ltsh.app.chat.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MessageAdapter extends BaseAdapter{


    private Context mContext;
    private List<MessageItem> mData = new ArrayList<>();


    public MessageAdapter(Context mContext) {
        this.mContext = mContext;
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
            holder.msg_item_txt_title = (TextView) convertView.findViewById(R.id.msg_item_txt_title);
            holder.msg_item_txt_content = (TextView) convertView.findViewById(R.id.msg_item_txt_content);
            holder.msg_item_txt_time = (TextView) convertView.findViewById(R.id.msg_item_txt_time);
            holder.msg_item_num = (TextView) convertView.findViewById(R.id.msg_item_num);
            convertView.setTag(R.id.tag_message_info,holder);
        }else{
            holder = (ViewHolder) convertView.getTag(R.id.tag_message_info);
        }

        Object obj = mData.get(position);
        //设置下控件的值

        MessageItem item = (MessageItem) obj;
        if(item != null){
            holder.msg_item_txt_title.setText(item.getCreateByName());
            holder.msg_item_num.setText((item.getFszCount() + item.getWdCount()) + "");
            String lastMsg = item.getLastMsg();
            if(lastMsg != null) {
                holder.msg_item_txt_content.setText(lastMsg.split("_")[1]);
            }
            holder.msg_item_txt_time.setText(item.getLastTime());

        }

        return convertView;
    }



    private static class ViewHolder{
        TextView msg_item_txt_title;
        TextView msg_item_txt_content;
        TextView msg_item_txt_time;
        TextView msg_item_num;
    }

    //往特定位置，添加一个元素
    public void add(int position,MessageItem item){
        boolean isChange = false;
        for (MessageItem message :
                mData) {
            if (item.getCreateBy().intValue() == message.getCreateBy().intValue()) {
                BeanUtils.copyProperties(item, message);
            }
            isChange = true;
        }
        if(!isChange) {
            mData.add(position,item);
        }

        notifyDataSetChanged();
    }
    //往特定位置，添加一个元素
    public void addAll(int position, List<MessageItem> items){
        for (MessageItem item :
                items) {
            add(position, item);
        }
        notifyDataSetChanged();
    }
}
