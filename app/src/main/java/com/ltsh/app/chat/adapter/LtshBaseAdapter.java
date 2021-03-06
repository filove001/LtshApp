package com.ltsh.app.chat.adapter;

/**
 * Created by Random on 2017/9/27.
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ltsh.app.chat.R;
import com.ltsh.app.chat.entity.BaseEntity;
import com.ltsh.app.chat.entity.MessageInfo;
import com.ltsh.app.chat.utils.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class LtshBaseAdapter<T extends BaseEntity> extends android.widget.BaseAdapter {



    private List<T> dataList = new ArrayList<>();

    public List<T> getDataList() {
        return dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
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

    /**
     * 是否重复
     * @param item
     * @return
     */
    public boolean isRepetition(T item) {
        return false;
    }
    //往特定位置，添加一个元素
    public boolean add(T item, boolean isBatch) {
        return add(null, item, isBatch);
    }
    //往特定位置，添加一个元素
    public boolean add(T item) {
        return add(null, item, false);
    }
    //往特定位置，添加一个元素
    public boolean add(Integer position,T item, boolean isBatch){
        boolean isUpdate = false;//是否有修改
        boolean isExist = false;//是否已经存在
        if(isRepetition(item)){
            return false;
        } else {
            if(item.getId() != null) {
                for (T message : dataList) {
                    if (message.getId() != null && item.getId().intValue() == message.getId().intValue()) {
                        isExist = true;
                        if(message.getUpdateTime() != null && !message.getUpdateTime().equals(message.getUpdateTime())) {
                            BeanUtils.copyProperties(item, message);
                            isUpdate = true;
                            break;
                        }
                        break;
                    }

                }
            }
        }
        if(!isExist) {
            if(position != null) {
                dataList.add(position,item);
            } else {
                dataList.add(item);
            }
        }

        if(!isBatch & (!isExist || isUpdate)) {
//            notifyDataSetInvalidated();
            notifyDataSetChanged();
            return true;
        }
        if(!isExist || isUpdate) {
            return true;
        }
        return false;
    }

    //往特定位置，添加一个元素
    public void addAll(Integer position, List<T> items){
        boolean isrn = false;
        for (T item : items) {
            boolean tmp = add(position, item, true);
            if(tmp) {
                isrn = tmp;
            }

        }
        if(isrn) {
            notifyDataSetChanged();
        }

    }
    //往特定位置，添加一个元素
    public void addAll(List<T> items){
        addAll(null, items);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void clear() {
        this.dataList.clear();
    }
}
