package com.ltsh.app.chat.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArraySet;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.ltsh.app.chat.MyAlertDiaLog;
import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.utils.LogUtils;

import java.util.Set;

/**
 * Created by Random on 2017/10/16.
 */

public class BaseActivity extends Activity {
    public static Set<Activity> activitySet = new ArraySet<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        onCreate(savedInstanceState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySet.add(this);
    }

    /**
     * 监听Back键按下事件,方法1:
     * 注意:
     * super.onBackPressed()会自动调用finish()方法,关闭
     * 当前Activity.
     * 若要屏蔽Back键盘,注释该行代码即可
     */
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        LogUtils.i("按下了back键   onBackPressed()");
        new MyAlertDiaLog(){
            @Override
            public void onConfirm(Activity mContext) {
                for (Activity activity : activitySet) {
                    activity.finish();
                }
                activitySet.clear();
            }

            @Override
            public void onCancel(Activity mContext) {

            }
        }.showDialog(this, "即将退出应用");



    }


    /**
     * 监听Back键按下事件,方法2:
     * 注意:
     * 返回值表示:是否能完全处理该事件
     * 在此处返回false,所以会继续传播该事件.
     * 在具体项目中此处的返回值视情况而定.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
//            LogUtils.i(TAG, "按下了back键   onKeyDown()");
//            return false;
//        }else {
//            return super.onKeyDown(keyCode, event);
//        }
        return super.onKeyDown(keyCode, event);
    }
}
