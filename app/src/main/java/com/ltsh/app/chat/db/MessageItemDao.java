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
                "msg.CREATE_BY," +
                "msg.CREATE_BY_NAME," +
                "SUM(CASE WHEN msg.status = 'FSZ' THEN 1 ELSE 0 END) FSZ_COUNT," +
                "SUM(CASE WHEN msg.status = 'WD' THEN 1 ELSE 0 END) WD_COUNT," +
                "SUM(CASE WHEN msg.status = 'YD' THEN 1 ELSE 0 END) YD_COUNT," +
                "MAX(msg.create_time) LAST_TIME, " +
                "MAX(id || '_' || msg.`msg_context`) LAST_MSG " +
                "FROM message_info msg " +
                "WHERE msg.to_user=? " +
                "GROUP BY msg.create_by,msg.create_by_name order by id desc";
        final List<MessageItem> messageItemList = DbUtils.rawQuery(MessageItem.class,sql, new String[]{userId + ""});
        return messageItemList;
    }
}
