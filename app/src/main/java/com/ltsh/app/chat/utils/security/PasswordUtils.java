package com.ltsh.app.chat.utils.security;

import com.ltsh.common.util.security.MD5Util;

/**
 * Created by Random on 2018/2/1.
 */

public class PasswordUtils {
    private final static String prefix1 = "ltsh:";
    private final static String prefix2 = "ltshChat:";
    private final static String prefix3 = "chat:";
    private final static String prefix4 = "ltshUser:";
    public static String createPassword(String inputPassword) {
        return MD5Util.encoder(prefix4 + inputPassword);
    }
    public static String createLoginPassword(String inputPassword, String randomValue) {
        return MD5Util.encoder(MD5Util.encoder(prefix2 + MD5Util.encoder(prefix3 + createPassword(inputPassword)) + randomValue));
    }
}
