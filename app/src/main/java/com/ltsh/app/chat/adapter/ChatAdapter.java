package com.ltsh.app.chat.adapter;

/**
 * Created by Random on 2017/9/27.
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ltsh.app.chat.R;
import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.entity.MessageInfo;
import com.ltsh.app.chat.utils.ImageUtils;

public class ChatAdapter extends LtshBaseAdapter{


    private Context mContext;
    private ListView listView;

    public ChatAdapter(Context mContext, ListView listView) {
        this.mContext = mContext;
        this.listView = listView;
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
//        int itemViewType = getItemViewType(position);

        ViewHolder viewHolder = null;
        Object obj = getDataList().get(position);
        //设置下控件的值

        MessageInfo item = (MessageInfo) obj;
        viewHolder = null;
        int itemViewType = 0;
        if(item.getCreateBy().intValue() == CacheObject.userToken.getId().intValue()) {
            itemViewType = 0;
        } else {
            itemViewType = 1;
        }
        if(convertView == null){
            if(itemViewType == 0) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.my_chat_item, parent, false);
            } else {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.friend_chat_item, parent, false);
            }
        }else{
            if(itemViewType == 0) {
                viewHolder = (ViewHolder) convertView.getTag(R.id.tag_my_msg_item);
            } else {
                viewHolder = (ViewHolder) convertView.getTag(R.id.tag_friend_msg_item);
            }
        }
        if(viewHolder == null) {
            viewHolder = getViewHolder(itemViewType, convertView);
        }

        if(item != null){
            viewHolder.chat_item_img_icon.setImageBitmap(ImageUtils.readBitMap(convertView.getContext(), R.mipmap.iv_icon_baidu));
            viewHolder.chat_item_img_icon.setVisibility(View.VISIBLE);
            viewHolder.chat_item_txt_content.setText(item.getMsgContext());
//            if(viewHolder.type == 0) {
//                viewHolder.chat_item_txt_name.setText(item.getCreateByName());
//            } else {
                viewHolder.chat_item_txt_name.setText(item.getCreateByName());
//            }
//            if(item.getCreateTime() != null) {
//                viewHolder.chat_item_txt_time.setText(item.getCreateTime());
//            }

        }

        return convertView;
    }
    private ViewHolder getViewHolder(int itemViewType, View convertView) {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.type=itemViewType;
        viewHolder.chat_item_txt_content = convertView.findViewById(R.id.chat_item_txt_content);
//            viewHolder.chat_item_txt_time = convertView.findViewById(R.id.chat_item_txt_time);
        viewHolder.chat_item_img_icon = convertView.findViewById(R.id.chat_item_img_icon);
        viewHolder.chat_item_txt_name = convertView.findViewById(R.id.chat_item_txt_name);
        if(itemViewType == 0) {
            convertView.setTag(R.id.tag_my_msg_item,viewHolder);
        } else {
            convertView.setTag(R.id.tag_friend_msg_item,viewHolder);
        }
        return viewHolder;
    }
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        listView.smoothScrollToPosition(listView.getHeight());
    }

    private class ViewHolder{
        TextView chat_item_txt_content;
        TextView chat_item_txt_time;
        TextView chat_item_txt_name;
        ImageView chat_item_img_icon;
        int type;
    }


}
