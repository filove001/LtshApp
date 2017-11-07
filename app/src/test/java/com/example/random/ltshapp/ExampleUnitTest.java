package com.example.random.ltshapp;

import com.ltsh.app.chat.utils.db.DbUtils;
import com.ltsh.app.chat.entity.MessageInfo;
import com.ltsh.app.chat.entity.UserFriend;
import com.ltsh.app.chat.entity.UserToken;


import org.junit.Test;

import java.util.List;


import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void test() {
        List<DbColumn> dbClounms = DbUtils.getDbColumns(MessageInfo.class);
        String createTable = DbUtils.getCreateTable(dbClounms);

        System.out.println(createTable);
        dbClounms = DbUtils.getDbColumns(UserFriend.class);
        createTable = DbUtils.getCreateTable(dbClounms);
        System.out.println(createTable);

        dbClounms = DbUtils.getDbColumns(UserToken.class);
        createTable = DbUtils.getCreateTable(dbClounms);
        System.out.println(createTable);
    }
}