package com.ltsh.app.chat.db;

import com.ltsh.app.chat.entity.viewbean.MessageItem;

import java.util.List;

/**
 * Created by Random on 2017/11/3.
 */

public class MessageItemDao {
    public static List<MessageItem> getList(Integer userId) {
        String sql = "\n" +
                "SELECT * FROM (\n" +
                "SELECT \n" +
                "  msg.CREATE_BY,\n" +
                "  fr.name NAME,\n" +
                "  SUM(\n" +
                "    CASE WHEN msg.status = 'FSZ' THEN 1 ELSE 0 END\n" +
                "  ) FSZ_COUNT,\n" +
                "  SUM(\n" +
                "    CASE WHEN msg.status = 'WD' or msg.status='YSD' THEN 1 ELSE 0 END\n" +
                "  ) WD_COUNT,\n" +
                "  SUM(\n" +
                "    CASE WHEN msg.status = 'YD' THEN 1 ELSE 0 END\n" +
                "  ) YD_COUNT,\n" +
                "  MAX(msg.create_time) LAST_TIME,\n" +
                "  MAX(msg.id || '_' || msg.`msg_context`) LAST_MSG \n" +
                "FROM\n" +
                "  message_info msg \n" +
                "  LEFT JOIN USER_FRIEND fr ON msg.`create_by` = fr.`friend_user_id`\n" +
                "WHERE  msg.`source_type`='USER' and fr.belongs_to=? and msg.belongs_to=?\n" +
                "GROUP BY msg.create_by,fr.name\n" +
                "UNION ALL\n" +
                "SELECT \n" +
                "  msg.CREATE_BY,\n" +
                "  ur.name  NAME,\n" +
                "  SUM(\n" +
                "    CASE WHEN msg.status = 'FSZ' THEN 1 ELSE 0 END\n" +
                "  ) FSZ_COUNT,\n" +
                "  SUM(\n" +
                "    CASE WHEN msg.status = 'WD' or msg.status='YSD' THEN 1 ELSE 0 END\n" +
                "  ) WD_COUNT,\n" +
                "  SUM(\n" +
                "    CASE WHEN msg.status = 'YD' THEN 1 ELSE 0 END\n" +
                "  ) YD_COUNT,\n" +
                "  MAX(msg.create_time) LAST_TIME,\n" +
                "  MAX(msg.id || '_' || msg.`msg_context`) LAST_MSG \n" +
                "FROM\n" +
                "  message_info msg \n" +
                "  LEFT JOIN User_group ur ON msg.`source_id` = ur.id\n" +
                "WHERE msg.`source_type`='GROUP' and ur.belongs_to=? and msg.belongs_to=? \n" +
                "GROUP BY msg.create_by,ur.name\n" +
                ") messgeItem \n" +
                "ORDER BY LAST_TIME DESC";
        final List<MessageItem> messageItemList = BaseDao.rawQuery(MessageItem.class,sql, new String[]{userId + "", userId + "", userId + "", userId + ""});
        return messageItemList;
    }
    public static void updateMessageRead(Integer belongsTo, Integer toUser) {
        String sql = "update message_info set status='YD' where (status='FSZ' or status='YSD' or status='WD') and (belongs_to=? and send_user=?)";
        BaseDao.execSQL(sql, new Object[]{belongsTo, toUser});
    }
}
