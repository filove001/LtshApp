package com.ltsh.app.chat.listener;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.AudioRecord;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.ltsh.app.chat.entity.AudioEntity;
import com.ltsh.common.util.LogUtils;

/**
 * Created by Random on 2017/12/29.
 */

public class AudioStartListener implements View.OnLongClickListener {
    private AudioEntity audioEntity;
    private Activity activity;
    public AudioStartListener(AudioEntity audioEntity, Activity activity) {
        this.audioEntity = audioEntity;
        this.activity = activity;
    }

    @Override
    public boolean onLongClick(View v) {
        if (ContextCompat.checkSelfPermission(v.getContext(),
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            LogUtils.info("yes");
        }else{
            //
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    1);
        }
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.RECORD_AUDIO},
                1);
        audioEntity.start();
        return false;
    }
}
