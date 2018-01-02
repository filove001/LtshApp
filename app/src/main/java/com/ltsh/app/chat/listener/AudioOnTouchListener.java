package com.ltsh.app.chat.listener;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.ltsh.app.chat.entity.AudioEntity;
import com.ltsh.app.chat.utils.AudioRecoderUtils;

/**
 * Created by Random on 2017/12/29.
 */

public class AudioOnTouchListener implements View.OnTouchListener {
    private AudioRecoderUtils audioRecoderUtils;
    public AudioOnTouchListener(AudioRecoderUtils audioRecoderUtils) {
        this.audioRecoderUtils = audioRecoderUtils;
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                ((Button)v).setText("松开保存");
                audioRecoderUtils.startRecord();
                break;
            case MotionEvent.ACTION_UP:
                audioRecoderUtils.stopRecord();        //结束录音（保存录音文件）
//                        mAudioRecoderUtils.cancelRecord();    //取消录音（不保存录音文件）
//                        mPop.dismiss();
                ((Button)v).setText("按住说话");
                break;
        }
        return true;
    }
}
