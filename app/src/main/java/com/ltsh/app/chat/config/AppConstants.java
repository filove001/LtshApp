package com.ltsh.app.chat.config;

/**
 * Created by Random on 2017/9/27.
 */

public class AppConstants {
//    public final static String SERVLCE_URL = "http://192.168.31.208:8013";
    public static String APP_ID = "";
    public static String APP_SECRET = "";
    /**
     * 服务器地址
     */
    public final static String SERVLCE_URL = "http://120.79.158.226:8013";
    /**
     * 获取信息
     */
    public final static String GET_MESSAGE_URL = "/chat/message/getMessage";
    /**
     * 获取信息
     */
    public final static String SEND_MESSAGE_URL = "/chat/message/sendMessage";
    /**
     * 登录url
     */
    public final static String LOGIN_URL = "/chat/user/loginVerify";
    /**
     * 注册url
     */
    public final static String REGISTER_URL = "/chat/user/register";
    /**
     * 获取随机数
     */
    public final static String GET_RANDOM_URL = "/chat/basics/getRandomStr";
    /**
     * 获取好友
     */
    public final static String GET_FRIEND_URL = "/chat/userFriend/page";
    public final static String ADD_FRIEND_URL = "/chat/userFriend/add";
    public final static String GET_GROUP_URL = "/chat/userGroup/page";
    public final static String GET_GROUP_REL_URL = "/chat/userGroupRel/page";

    public final static String USER_TOKEN_KEY = "user.token.key";

    public static final int DISK_MAX_SIZE = 10 * 1024 * 1024;//10MB
    public static final int TIMER_INTERVAL = 20 * 1000;//10MB
    public static boolean isInit = true;
}
