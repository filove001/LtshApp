package com.ltsh.app.chat.db;

import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.entity.viewbean.MessageItem;

import java.util.List;

/**
 * Created by Random on 2017/11/3.
 */

public class MessageItemDao {
    public static List<MessageItem> getList(Integer userId) {
        String sql = "SELECT " +
                "msg.create_by," +
                "msg.create_by_name," +
                "SUM(CASE WHEN msg.status = 'FSZ' THEN 1 ELSE 0 END) FSZ_COUNT," +
                "SUM(CASE WHEN msg.status = 'WD' THEN 1 ELSE 0 END) WD_COUNT," +
                "SUM(CASE WHEN msg.status = 'YD' THEN 1 ELSE 0 END) YD_COUNT," +
                "strftime('YYYY-MM-DD HH:MM:SS',MAX(msg.create_time)) last_time, " +
                "MAX(id || '_' || msg.`msg_context`) last_msg " +
                "FROM message_info msg " +
                "WHERE msg.to_user=? " +
                "GROUP BY msg.create_by,msg.create_by_name order by id desc";
        final List<MessageItem> messageItemList = DbUtils.rawQuery(MessageItem.class,sql, new String[]{userId + ""});
        return messageItemList;
    }
}
