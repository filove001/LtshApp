package com.ltsh.app.chat.utils;

import android.util.Log;





/**
 * Created by Random on 2017/9/27.
 */

public class LogUtils {
    private static String[] getMethodNames(StackTraceElement[] sElements){
        String[] strs = new String[3];
        strs[0] = sElements[1].getFileName();
        strs[1] = sElements[1].getMethodName();
        strs[2] = sElements[1].getLineNumber() + "";
        return strs;
    }

    public static void i(String msg) {
        String[] methodNames = getMethodNames(new Throwable().getStackTrace());
        Log.i(methodNames[0],createLog(methodNames,msg));
    }

    private static String createLog(String[] methodNames,String log) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(methodNames[1]);
        buffer.append("(").append(methodNames[0]).append(":").append(methodNames[2]).append(")");
        buffer.append(log);
        return buffer.toString();
    }
    public static void v(String msg) {
        String[] methodNames = getMethodNames(new Throwable().getStackTrace());
        Log.v(methodNames[0],createLog(methodNames,msg));
    }
    public static void e(String msg, Throwable throwable) {
        String[] methodNames = getMethodNames(new Throwable().getStackTrace());
        Log.e(methodNames[0],createLog(methodNames,msg), throwable);
    }

    public static void e(String msg) {
        String[] methodNames = getMethodNames(new Throwable().getStackTrace());
        Log.e(methodNames[0],createLog(methodNames,msg));
    }
}
