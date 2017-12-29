package com.ltsh.app.chat.dao;

import com.ltsh.app.chat.utils.db.DbUtils;
import com.ltsh.app.chat.entity.viewbean.MessageItem;

import java.util.List;

/**
 * Created by Random on 2017/11/3.
 */

public class MessageItemDao {
    public static List<MessageItem> getList(Integer userId) {
        String sql = "SELECT \n" +
                "  msg.CREATE_BY,\n" +
                "  CASE WHEN gr.name <> NULL\n" +
                "\tTHEN gr.name\n" +
                "\tELSE fr.name\n" +
                "\tEND NAME,\n" +
                "  source_type,\n" +
                "  source_id,\n" +
                "  SUM(\n" +
                "    CASE WHEN msg.status = 'FSZ' THEN 1 ELSE 0 END\n" +
                "  ) FSZ_COUNT,\n" +
                "  SUM(\n" +
                "    CASE WHEN msg.status = 'WD' THEN 1 ELSE 0 END\n" +
                "  ) WD_COUNT,\n" +
                "  SUM(\n" +
                "    CASE WHEN msg.status = 'YD' THEN 1 ELSE 0 END\n" +
                "  ) YD_COUNT,\n" +
                "  MAX(msg.create_time) LAST_TIME,\n" +
                "  MAX(msg.id || '_' || msg.`msg_context`) LAST_MSG \n" +
                "FROM\n" +
                "  message_info msg \n" +
                "  LEFT JOIN USER_GROUP gr ON msg.`source_id`=gr.`id`\n" +
                "  LEFT JOIN USER_FRIEND fr ON msg.`source_id` = fr.`id`\n" +
                "WHERE msg.to_user = ? \n" +
                "GROUP BY msg.create_by,msg.source_type,msg.source_id\n" +
                "ORDER BY LAST_TIME DESC";
        final List<MessageItem> messageItemList = BaseDao.rawQuery(MessageItem.class,sql, new String[]{userId + ""});
        return messageItemList;
    }
    public static void updateMessageRead(Integer createBy, Integer toUser) {
        String sql = "update message_info set status='YD' where (status='FSZ' or status='WD') and ((create_by=? and to_user=?) or (to_user=? and create_by=?))";
        BaseDao.execSQL(sql, new Object[]{createBy, toUser, createBy, toUser});
    }
}
