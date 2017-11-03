package com.ltsh.app.chat.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ltsh.app.chat.fragment.FriendFragment;
import com.ltsh.app.chat.fragment.ChatListFragment;
import com.ltsh.app.chat.R;
import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.service.MsgListService;
import com.ltsh.app.chat.service.ReceiveMsgService;

/**
 * Created by Random on 2017/10/13.
 */

public class ContextActivity extends BaseActivity implements View.OnClickListener {

    //Activity UI Object
    private LinearLayout ly_tab_menu_channel;
    private TextView tab_menu_channel;
    private TextView tab_menu_channel_num;
    private LinearLayout ly_tab_menu_message;
    private TextView tab_menu_message;
    private TextView tab_menu_message_num;
    private LinearLayout ly_tab_menu_better;
    private TextView tab_menu_better;
    private TextView tab_menu_better_num;
    private LinearLayout ly_tab_menu_setting;
    private TextView tab_menu_setting;
    private ImageView tab_menu_setting_partner;
    private FragmentManager fManager;
    //    private FragmentTransaction fTransaction;
    private Fragment chatListFragment, friendFragment, fg3, fg4;
    private String url = "htpp://127.0.0.1:8080/";

    private Intent receiveMessageService;
    private Intent msgListService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_context);
        bindViews();
        fManager = getFragmentManager();
        ly_tab_menu_channel.performClick();
        if(receiveMessageService == null) {
            receiveMessageService = new Intent(this, ReceiveMsgService.class);
            receiveMessageService.setAction("com.ltsh.app.chat.RECEIVE_MSG_SERVICE");
            startService(receiveMessageService);
        }
        if(msgListService == null) {
            msgListService = new Intent(this, MsgListService.class);
            msgListService.setAction("com.ltsh.app.chat.MSG_LIST_SERVICE");
            startService(msgListService);
        }


        CacheObject.handler = new Handler();


    }

    private void bindViews() {
        //底部菜单初始化
        ly_tab_menu_channel = (LinearLayout) findViewById(R.id.ly_tab_menu_channel);
        tab_menu_channel = (TextView) findViewById(R.id.tab_menu_channel);
        tab_menu_channel_num = (TextView) findViewById(R.id.tab_menu_channel_num);
        ly_tab_menu_message = (LinearLayout) findViewById(R.id.ly_tab_menu_message);
        tab_menu_message = (TextView) findViewById(R.id.tab_menu_message);
        tab_menu_message_num = (TextView) findViewById(R.id.tab_menu_message_num);
        ly_tab_menu_better = (LinearLayout) findViewById(R.id.ly_tab_menu_better);
        tab_menu_better = (TextView) findViewById(R.id.tab_menu_better);
        tab_menu_better_num = (TextView) findViewById(R.id.tab_menu_better_num);
        ly_tab_menu_setting = (LinearLayout) findViewById(R.id.ly_tab_menu_setting);
        tab_menu_setting = (TextView) findViewById(R.id.tab_menu_setting);
        tab_menu_setting_partner = (ImageView) findViewById(R.id.tab_menu_setting_partner);


        ly_tab_menu_channel.setOnClickListener(this);
        ly_tab_menu_message.setOnClickListener(this);
        ly_tab_menu_better.setOnClickListener(this);
        ly_tab_menu_setting.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (v.getId()) {
            case R.id.ly_tab_menu_channel:
                setSelected();
                tab_menu_channel.setSelected(true);
                tab_menu_channel_num.setVisibility(View.INVISIBLE);
                if (chatListFragment == null) {
                    chatListFragment = new ChatListFragment();
                    fTransaction.add(R.id.ly_content, chatListFragment);
//                    View view = fg1.getView();

                } else {
                    fTransaction.show(chatListFragment);
                }

                break;
            case R.id.ly_tab_menu_message:
                setSelected();
                tab_menu_message.setSelected(true);
                tab_menu_message_num.setVisibility(View.INVISIBLE);
                if (friendFragment == null) {
                    friendFragment = new FriendFragment();
                    fTransaction.add(R.id.ly_content, friendFragment);
                } else {
                    fTransaction.show(friendFragment);
                }

                break;
            case R.id.ly_tab_menu_better:
                setSelected();
                tab_menu_better.setSelected(true);
                tab_menu_better_num.setVisibility(View.INVISIBLE);
//                if(fg3 == null){
//                    fg3 = new LtshChatFragment("第三个Fragment");
//                    fTransaction.add(R.id.ly_content,fg3);
//                }else{
//                    fTransaction.show(fg3);
//                }
                break;
            case R.id.ly_tab_menu_setting:
                setSelected();
                tab_menu_setting.setSelected(true);
                tab_menu_setting_partner.setVisibility(View.INVISIBLE);

//                if(fg4 == null){
//                    fg4 = new LtshChatFragment("第四个Fragment");
//                    fTransaction.add(R.id.ly_content,fg4);
//                }else{
//                    fTransaction.show(fg4);
//                }
                break;
        }

        fTransaction.commit();
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (chatListFragment != null) fragmentTransaction.hide(chatListFragment);
        if (friendFragment != null) fragmentTransaction.hide(friendFragment);
        if (fg3 != null) fragmentTransaction.hide(fg3);
        if (fg4 != null) fragmentTransaction.hide(fg4);
    }

    //重置所有文本的选中状态
    private void setSelected() {
        tab_menu_channel.setSelected(false);
        tab_menu_message.setSelected(false);
        tab_menu_better.setSelected(false);
        tab_menu_setting.setSelected(false);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(receiveMessageService != null) {
            stopService(receiveMessageService);
        }
        if(msgListService != null) {
            stopService(msgListService);
        }
    }
}