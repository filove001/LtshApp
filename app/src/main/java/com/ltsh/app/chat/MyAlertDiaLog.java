package com.ltsh.app.chat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

/**
 * Created by Random on 2017/10/16.
 */

public abstract class MyAlertDiaLog {
    AlertDialog alert;
    private AlertDialog.Builder builder = null;
    public void showDialog(final Activity mContext, String message) {
        alert = null;
        builder = new AlertDialog.Builder(mContext);
        alert = builder
//                .setIcon(R.mipmap.ic_icon_fish)
                .setTitle("系统提示：")
                .setMessage(message)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onConfirm(mContext);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onCancel(mContext);
                    }
                })

                .create();             //创建AlertDialog对象
        alert.show();                    //显示对话框
    }
    public abstract void onConfirm(Activity mContext);
    public abstract void onCancel(Activity mContext);
}
