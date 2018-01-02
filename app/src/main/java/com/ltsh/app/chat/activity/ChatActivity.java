package com.ltsh.app.chat.activity;

import android.Manifest;
import android.graphics.drawable.Drawable;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ltsh.app.chat.R;
import com.ltsh.app.chat.adapter.ChatAdapter;
import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.dao.BaseDao;
import com.ltsh.app.chat.dao.MessageItemDao;
import com.ltsh.app.chat.entity.AudioEntity;
import com.ltsh.app.chat.entity.MessageInfo;
import com.ltsh.app.chat.entity.UserFriend;
import com.ltsh.app.chat.listener.AudioOnTouchListener;
import com.ltsh.app.chat.listener.AudioStartListener;
import com.ltsh.app.chat.listener.SendBtnOnClickListener;

import com.ltsh.app.chat.utils.AudioRecoderUtils;
import com.ltsh.common.util.JsonUtils;

import java.util.List;

/**
 * Created by Random on 2017/10/13.
 */

public class ChatActivity extends BaseActivity {

    private TextView titleView;
    private Button backBtn;
    private Button sendBtn;
    private ListView chat_list;
    private EditText ed_send_msg_input;
    private Button btn_audio;
    private Button tab_menu_msg_switch;

    private AudioRecoderUtils audioRecoderUtils = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        bindViews();
        initAudio();
        String friendData = this.getIntent().getStringExtra("friendData");
        final UserFriend userFriend = JsonUtils.fromJson(friendData, UserFriend.class);
        titleView.setText(userFriend.getName());
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sendBtn.setOnClickListener(new SendBtnOnClickListener(this, userFriend));
        chat_list = (ListView) findViewById(R.id.chat_list);
        if(CacheObject.chatAdapter == null) {
            CacheObject.chatAdapter = new ChatAdapter(this, chat_list);
        }
        chat_list.setAdapter(CacheObject.chatAdapter);

        CacheObject.handler.post(new Runnable() {
            @Override
            public void run() {
                final List<MessageInfo> messageInfos = BaseDao.query(MessageInfo.class, "(create_by=? and to_user=?) or (create_by=? and to_user=?)", new String[]{userFriend.getFriendUserId() + "", CacheObject.userToken.getId() + "", CacheObject.userToken.getId() + "", userFriend.getFriendUserId() + ""}, null);
                CacheObject.chatAdapter.addAll(messageInfos);
            }
        });
        MessageItemDao.updateMessageRead(CacheObject.userToken.getId(), userFriend.getFriendUserId());
        tab_menu_msg_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn_audio.getVisibility() == View.GONE) {
                    Drawable top = getResources().getDrawable(R.mipmap.form);// 获取res下的图片drawable
                    top.setBounds(0, 0, top.getMinimumWidth(), top.getMinimumHeight());// 一定要设置setBounds();
                    tab_menu_msg_switch.setCompoundDrawables(null, top,null, null);
                    btn_audio.setVisibility(View.VISIBLE);
                    ed_send_msg_input.setVisibility(View.GONE);
                } else {
                    Drawable top = getResources().getDrawable(R.mipmap.sound);// 获取res下的图片drawable
                    top.setBounds(0, 0, top.getMinimumWidth(), top.getMinimumHeight());// 一定要设置setBounds();
                    tab_menu_msg_switch.setCompoundDrawables(null, top,null, null);
//                    tab_menu_msg_switch.setBackground(getResources().getDrawable(R.mipmap.form));
                    btn_audio.setVisibility(View.GONE);
                    ed_send_msg_input.setVisibility(View.VISIBLE);
                }
            }
        });
//        chat_list.setOnApplyWindowInsetsListener();


    }
    private void initAudio(){
        int MY_PERMISSIONS_REQUEST_CALL_PHONE = 100;
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                MY_PERMISSIONS_REQUEST_CALL_PHONE);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS},
                MY_PERMISSIONS_REQUEST_CALL_PHONE);
        audioRecoderUtils = new AudioRecoderUtils();
        audioRecoderUtils.setOnAudioStatusUpdateListener(new AudioRecoderUtils.OnAudioStatusUpdateListener() {
            @Override
            public void onUpdate(double db, long time) {
                //根据分贝值来设置录音时话筒图标的上下波动，下面有讲解
//                mImageView.getDrawable().setLevel((int) (3000 + 6000 * db / 100));
//                mTextView.setText(TimeUtils.long2String(time));
            }

            @Override
            public void onStop(String filePath) {
                Toast.makeText(ChatActivity.this, "录音保存在：" + filePath, Toast.LENGTH_SHORT).show();

//                mTextView.setText(0);
            }
        });

        //Button的touch监听
        btn_audio.setOnTouchListener(new AudioOnTouchListener(audioRecoderUtils));
    }
    private boolean isStart = false;
    private void bindViews() {
        titleView = (TextView)findViewById(R.id.txt_title);
        backBtn = (Button)findViewById(R.id.btn_back);
        sendBtn = (Button)findViewById(R.id.btn_send);
        ed_send_msg_input = (EditText) findViewById(R.id.ed_send_msg_input);
        btn_audio = (Button) findViewById(R.id.btn_audio);
        tab_menu_msg_switch = (Button)findViewById(R.id.tab_menu_msg_switch);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}