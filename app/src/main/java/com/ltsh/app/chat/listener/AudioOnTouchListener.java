package com.ltsh.app.chat.listener;

import android.view.MotionEvent;
import android.view.View;

import com.ltsh.app.chat.entity.AudioEntity;

/**
 * Created by Random on 2017/12/29.
 */

public class AudioOnTouchListener implements View.OnTouchListener {
    private AudioEntity audioEntity;

    public AudioOnTouchListener(AudioEntity audioEntity) {
        this.audioEntity = audioEntity;
    }
    @Override
    public boolean onTouch(View v,MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
//            if(longClicked) {
//                //快进
//
//            }

        } else if(event.getAction() == MotionEvent.ACTION_UP) {
            audioEntity.stop();
        }
        return false;
    }
}
