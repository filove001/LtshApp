package com.ltsh.app.chat.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Random on 2017/9/27.
 */

public class DateUtils {
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
    public static String format(Date date, String pattern){
        if(date == null) {
            return null;
        }
        simpleDateFormat.applyPattern(pattern);
        return simpleDateFormat.format(date);
    }
}
