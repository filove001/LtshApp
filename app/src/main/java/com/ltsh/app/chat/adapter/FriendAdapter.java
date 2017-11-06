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
import com.ltsh.app.chat.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class FriendAdapter extends LtshBaseAdapter<UserFriend>{


    private Context mContext;

    public FriendAdapter(Context mContext) {
        this.mContext = mContext;
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

        Object obj = getDataList().get(position);
        //设置下控件的值

        UserFriend userFriend = (UserFriend) obj;
        if(userFriend != null){
            holder.img_friend_icon.setImageBitmap(ImageUtils.readBitMap(convertView.getContext(), R.mipmap.iv_icon_baidu));
            holder.img_friend_icon.setVisibility(View.VISIBLE);
//            holder.img_friend_icon.seti;
            holder.txt_friend_name.setText(userFriend.getName());
        }

        return convertView;
    }



    private static class ViewHolder{
        ImageView img_friend_icon;
        TextView txt_friend_name;
    }

}
