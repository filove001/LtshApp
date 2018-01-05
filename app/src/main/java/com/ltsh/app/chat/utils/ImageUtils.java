package com.ltsh.app.chat.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ltsh.common.util.LogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by BiQiu on 2017/11/4.
 */

public class ImageUtils {
    /**

     *  以最省内存的方式读取本地资源的图片

     *  @param context

     *  @param resId

     *  @return

     */

    public  static Bitmap readBitMap(Context context, int resId){

        BitmapFactory.Options opt = new  BitmapFactory.Options();

        opt.inPreferredConfig =  Bitmap.Config.RGB_565;

//        opt.inPurgeable = true;
//
//        opt.inInputShareable = true;

        //  获取资源图片

        InputStream is =  context.getResources().openRawResource(resId);

        return  BitmapFactory.decodeStream(is, null, opt);

    }
    public  static Bitmap readBitMap(Context context, File file){

        BitmapFactory.Options opt = new  BitmapFactory.Options();

        opt.inPreferredConfig =  Bitmap.Config.RGB_565;

//        opt.inPurgeable = true;
//
//        opt.inInputShareable = true;

        //  获取资源图片
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            LogUtils.error(e.getMessage(), e);
        }

        return  BitmapFactory.decodeStream(fileInputStream, null, opt);

    }

}
