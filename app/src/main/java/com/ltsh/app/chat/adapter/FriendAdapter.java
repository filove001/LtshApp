package com.ltsh.app.chat.adapter;

/**
 * Created by Random on 2017/9/27.
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ltsh.app.chat.R;
import com.ltsh.app.chat.entity.MessageInfo;
import com.ltsh.app.chat.entity.UserFriend;
import com.ltsh.app.chat.entity.viewbean.MessageItem;
import com.ltsh.app.chat.utils.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class FriendAdapter extends BaseAdapter{


    private Context mContext;
    private List<UserFriend> mData = new ArrayList<>();


    public FriendAdapter(Context mContext) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.friend_item, parent, false);
            holder.img_friend_icon = (ImageView) convertView.findViewById(R.id.img_friend_icon);
            holder.txt_friend_name = (TextView) convertView.findViewById(R.id.txt_friend_name);
            convertView.setTag(R.id.tag_user_friend,holder);
        }else{
            holder = (ViewHolder) convertView.getTag(R.id.tag_user_friend);
        }

        Object obj = mData.get(position);
        //设置下控件的值

        UserFriend userFriend = (UserFriend) obj;
        if(userFriend != null){
//            holder.img_friend_icon.seti;
            holder.txt_friend_name.setText(userFriend.getName());
        }

        return convertView;
    }



    private static class ViewHolder{
        ImageView img_friend_icon;
        TextView txt_friend_name;
    }

    //往特定位置，添加一个元素
    public void add(int position,UserFriend item){
        boolean isChange = false;
        for (UserFriend message :
                mData) {
            if (item.getFriendUserId().intValue() == message.getFriendUserId().intValue()) {
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
    public void addAll(int position, List<UserFriend> items){
        for (UserFriend item :
                items) {
            add(position, item);
        }
        notifyDataSetChanged();
    }
}
