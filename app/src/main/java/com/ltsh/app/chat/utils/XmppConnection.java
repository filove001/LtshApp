package com.ltsh.app.chat.utils;

import com.ltsh.common.util.LogUtils;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

/**
 * Created by Random on 2018/1/18.
 */

public class XmppConnection {
    private static String SERVER_HOST = "ltsh-app.local";
    private static int SERVER_PORT = 5222;
    private static String SERVER_NAME = "ltsh-app.local";
    private static XMPPConnection connection;
    public static XMPPConnection getConnection() {
        if(XmppConnection.connection == null) {
            synchronized (XmppConnection.class) {
                if(XmppConnection.connection == null) {
                    XMPPConnection.DEBUG_ENABLED = true;
                    //配置文件  参数（服务地地址，端口号，域）
                    ConnectionConfiguration conConfig = new ConnectionConfiguration(SERVER_HOST, SERVER_PORT, SERVER_NAME);
                    //设置断网重连 默认为true
                    conConfig.setReconnectionAllowed(true);
                    //设置登录状态 true-为在线
                    conConfig.setSendPresence(true);
                    //设置不需要SAS验证
                    conConfig.setSASLAuthenticationEnabled(true);
                    //开启连接
                    XMPPConnection connection = new XMPPConnection(conConfig);
                    try {
                        connection.connect();
                        XmppConnection.connection = connection;
                    } catch (XMPPException e) {
                        LogUtils.error(e.getMessage(), e);
                        XmppConnection.connection = null;
                    }
                    //添加额外配置信息
                }
            }
        }
        return XmppConnection.connection;
    }
}
