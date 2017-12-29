package com.ltsh.app.chat.entity;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;

import com.ltsh.app.chat.utils.AudioRecoderUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Random on 2017/12/29.
 */

public class AudioEntity {
    private boolean isRecording;
    private int frequence = 8000;// 采样率 8000
    private int channelInConfig = AudioFormat.CHANNEL_IN_MONO;// 定义采样通道
    private int channelInConfig1 = AudioFormat.CHANNEL_CONFIGURATION_MONO;
    private int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;// 定义音频编码（16位）
    private byte[] buffer = null;// 录制的缓冲数组
    private AudioRecord audioRecord;
    private AudioTrack track;
    private AudioRecoderUtils audioRecoderUtils = new AudioRecoderUtils();
    public AudioEntity() {
        this.isRecording = false;
        int bufferSize = AudioRecord.getMinBufferSize(frequence, channelInConfig1, audioEncoding);
        // 实例化AudioRecord
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, frequence, channelInConfig, audioEncoding, bufferSize);
        // 定义缓冲数组
        buffer = new byte[bufferSize];

        // 获取缓冲 大小
//        bufferSize = AudioTrack.getMinBufferSize(frequence, channelInConfig,
//                audioEncoding);
        // 实例AudioTrack
        track = new AudioTrack(AudioManager.STREAM_MUSIC, frequence,
                channelInConfig1, audioEncoding, bufferSize,
                AudioTrack.MODE_STREAM);

    }

    public void start() {
        audioRecoderUtils.startRecord();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                audioRecord.startRecording();// 开始录制
//                isRecording = true;// 设置录制标记为true
//
//                // 开始录制
//                while (isRecording) {
//                    // 录制的内容放置到了buffer中，result代表存储长度
//                    int result = audioRecord.read(buffer, 0, buffer.length);
//
//            /*.....result为buffer中录制数据的长度(貌似基本上都是640)。
//            剩下就是处理buffer了，是发送出去还是直接播放，这个随便你。*/
//                    track.write(buffer, 0, buffer.length);
//                }
//            }
//        }).start();

    }
    public void stop() {
        audioRecoderUtils.stopRecord();
//        isRecording = false;
//        audioRecord.stop();
    }
    public void clear() {
        buffer = null;
        int bufferSize = AudioRecord.getMinBufferSize(frequence, channelInConfig, audioEncoding);
        buffer = new byte[bufferSize];
    }

    public void play() {
        //将语音数据写入即可。
        track.write(buffer, 0, buffer.length);
    }
    public byte[] getBuffer() {
        return buffer;
    }
}
