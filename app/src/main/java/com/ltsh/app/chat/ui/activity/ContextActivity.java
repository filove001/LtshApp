package com.ltsh.app.chat.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ltsh.app.chat.ui.fragment.UserInfoFragment;
import com.ltsh.app.chat.utils.timer.TimerUtils;
import com.ltsh.app.chat.handler.CallbackHandler;
import com.ltsh.app.chat.config.AppConstants;
import com.ltsh.app.chat.config.EntityCache;
import com.ltsh.app.chat.db.BaseDao;
import com.ltsh.app.chat.entity.UserFriend;
import com.ltsh.app.chat.entity.UserGroup;
import com.ltsh.app.chat.entity.UserGroupRel;
import com.ltsh.app.chat.entity.UserToken;
import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.handler.LoadEntityCallHandler;
import com.ltsh.app.chat.times.LoadDataTimerTask;
import com.ltsh.app.chat.times.ReceiveMsgTimerTask;
import com.ltsh.app.chat.ui.fragment.FriendFragment;
import com.ltsh.app.chat.ui.fragment.ChatListFragment;
import com.ltsh.app.chat.R;
import com.ltsh.app.chat.config.CacheObject;
import com.ltsh.app.chat.utils.cache.DiskLruCache;
import com.ltsh.app.chat.utils.http.AppHttpClient;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Random on 2017/10/13.
 */

public class ContextActivity extends BaseActivity implements View.OnClickListener {

    //Activity UI Object
    private LinearLayout ly_tab_menu_home;
    private TextView tab_menu_home;
    private TextView tab_menu_home_num;
    private LinearLayout ly_tab_menu_friend;
    private TextView tab_menu_friend;
    private TextView tab_menu_friend_num;
    private LinearLayout ly_tab_menu_dynamic;
    private TextView tab_menu_dynamic;
    private TextView tab_menu_dynamic_num;
    private LinearLayout ly_tab_menu_my;
    private TextView tab_menu_my;
    private ImageView tab_menu_my_num;
    private FragmentManager fManager;
    //    private FragmentTransaction fTransaction;
    private Fragment chatListFragment, friendFragment, fg3, userInfoFragment;
    private Fragment addFriendFragment;

    private Intent receiveMessageService;
    private Intent msgListService;

    @Override
     public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //填充选项菜单（读取XML文件、解析、加载到Menu组件上）
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //四个参数的含义。1，group的id,2,item的id,3,是否排序，4，将要显示的内容
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.group_one:
                Toast.makeText(ContextActivity.this, "添加好友", Toast.LENGTH_SHORT).show();

                Intent addFriend = new Intent("android.intent.action.ADD_FRIEND");
                addFriend.setClassName(this, AddFriendActivity.class.getName());
                startActivity(addFriend);
                break;
            case R.id.group_two:
                Toast.makeText(ContextActivity.this, "用户信息", Toast.LENGTH_SHORT).show();
                break;
            case R.id.group_three:
                if(CacheObject.msgListAdapter != null) {
                    CacheObject.msgListAdapter.clear();
                }
                if(CacheObject.chatAdapter != null) {
                    CacheObject.chatAdapter.clear();
                }
                if(CacheObject.friendAdapter != null) {
                    CacheObject.friendAdapter.clear();
                }
                Toast.makeText(ContextActivity.this, "退出登录", Toast.LENGTH_SHORT).show();
                CacheObject.userToken = null;
                BaseDao.delete(UserToken.class, null, null);
                super.loginOut();
                break;
            case R.id.group_dowm:
                final File cacheDir = getCacheDir();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url = "http://pic4.nipic.com/20091217/3885730_124701000519_2.jpg";
                        String key = DiskLruCache.Util.hashKeyForDisk(url);
                        try {
                            DiskLruCache.Snapshot snapshot = CacheObject.diskLruCache.get(key);
                            if(snapshot == null) {

                            }
                            DiskLruCache.Editor edit = CacheObject.diskLruCache.edit(key);
                            if(edit != null) {
                                OutputStream outputStream = edit.newOutputStream(0);
                                OkHttpUtils.download(url, outputStream);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_context);
        bindViews();
        fManager = getFragmentManager();


        TimerUtils.schedule(ReceiveMsgTimerTask.class, new ReceiveMsgTimerTask(this), 0, AppConstants.TIMER_INTERVAL);
        TimerUtils.schedule(LoadDataTimerTask.class, new LoadDataTimerTask(CacheObject.handler), 0, AppConstants.TIMER_INTERVAL);

        initFragment();
        loadData();
        ly_tab_menu_home.performClick();

    }

    private void bindViews() {
        //底部菜单初始化
        ly_tab_menu_home = (LinearLayout) findViewById(R.id.ly_tab_menu_home);
        tab_menu_home = (TextView) findViewById(R.id.tab_menu_home);
        tab_menu_home_num = (TextView) findViewById(R.id.tab_menu_home_num);
        ly_tab_menu_friend = (LinearLayout) findViewById(R.id.ly_tab_menu_friend);
        tab_menu_friend = (TextView) findViewById(R.id.tab_menu_friend);
        tab_menu_friend_num = (TextView) findViewById(R.id.tab_menu_friend_num);
        ly_tab_menu_dynamic = (LinearLayout) findViewById(R.id.ly_tab_menu_dynamic);
        tab_menu_dynamic = (TextView) findViewById(R.id.tab_menu_dynamic);
        tab_menu_dynamic_num = (TextView) findViewById(R.id.tab_menu_dynamic_num);
        ly_tab_menu_my = (LinearLayout) findViewById(R.id.ly_tab_menu_my);
        tab_menu_my = (TextView) findViewById(R.id.tab_menu_my);
        tab_menu_my_num = (ImageView) findViewById(R.id.tab_menu_my_num);


        ly_tab_menu_home.setOnClickListener(this);
        ly_tab_menu_friend.setOnClickListener(this);
        ly_tab_menu_dynamic.setOnClickListener(this);
        ly_tab_menu_my.setOnClickListener(this);

    }

    private void loadData() {
        if(AppConstants.isInit) {
            Map<String, Object> map = new HashMap<>();
            map.put("pageNumber", "1");
            map.put("pageSize", "10000");
            final LoadEntityCallHandler callHandler = new LoadEntityCallHandler();
            AppHttpClient.threadPost(AppConstants.SERVLCE_URL, AppConstants.GET_FRIEND_URL, map, this, new CallbackHandler() {
                @Override
                public void callBack(Result result) {
                    callHandler.callBack(result, UserFriend.class);
                }

                @Override
                public void error(Result result) {

                }
            });
            map = new HashMap<>();
            map.put("pageNumber", "1");
            map.put("pageSize", "10000");
            AppHttpClient.threadPost(AppConstants.SERVLCE_URL, AppConstants.GET_GROUP_URL, map, this, new CallbackHandler() {
                @Override
                public void callBack(Result result) {
                    callHandler.callBack(result, UserGroup.class);
                }

                @Override
                public void error(Result result) {

                }
            });
            map = new HashMap<>();
            map.put("pageNumber", "1");
            map.put("pageSize", "10000");
            AppHttpClient.threadPost(AppConstants.SERVLCE_URL, AppConstants.GET_GROUP_REL_URL, map, this, new CallbackHandler() {
                @Override
                public void callBack(Result result) {
                    callHandler.callBack(result, UserGroupRel.class);
                }

                @Override
                public void error(Result result) {

                }
            });
        }


        List<UserFriend> userFriendList = BaseDao.queryMyList(UserFriend.class, "id desc");
        EntityCache.init(UserFriend.class, userFriendList);
        List<UserGroup> userGroups = BaseDao.queryMyList(UserGroup.class, "id desc");
        EntityCache.init(UserGroup.class, userGroups);
        List<UserGroupRel> userGroupRels = BaseDao.queryMyList(UserGroupRel.class, "id desc");
        EntityCache.init(UserGroupRel.class, userGroupRels);
//        OkHttpUtils
    }

    private void initFragment() {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        chatListFragment = new ChatListFragment();
        fTransaction.add(R.id.ly_content, chatListFragment);
        friendFragment = new FriendFragment();
        fTransaction.add(R.id.ly_content, friendFragment);
        hideAllFragment(fTransaction);
        fTransaction.commit();
    }
    @Override
    public void onClick(View v) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (v.getId()) {
            case R.id.ly_tab_menu_home:
                setSelected();
                tab_menu_home.setSelected(true);
                tab_menu_home_num.setVisibility(View.INVISIBLE);
                if (chatListFragment == null) {
                    chatListFragment = new ChatListFragment();
                    fTransaction.add(R.id.ly_content, chatListFragment);
//                    View view = fg1.getView();

                } else {
                    fTransaction.show(chatListFragment);
                }

                break;
            case R.id.ly_tab_menu_friend:
                setSelected();
                tab_menu_friend.setSelected(true);
                tab_menu_friend_num.setVisibility(View.INVISIBLE);
                if (friendFragment == null) {
                    friendFragment = new FriendFragment();
                    fTransaction.add(R.id.ly_content, friendFragment);
                } else {
                    fTransaction.show(friendFragment);
                }

                break;
            case R.id.ly_tab_menu_dynamic:
                setSelected();
                tab_menu_dynamic.setSelected(true);
                tab_menu_dynamic_num.setVisibility(View.INVISIBLE);
//                if(fg3 == null){
//                    fg3 = new LtshChatFragment("第三个Fragment");
//                    fTransaction.add(R.id.ly_content,fg3);
//                }else{
//                    fTransaction.show(fg3);
//
                break;
            case R.id.ly_tab_menu_my:
                setSelected();
                tab_menu_my.setSelected(true);
                tab_menu_my_num.setVisibility(View.INVISIBLE);
                if (userInfoFragment == null) {
                    userInfoFragment = new UserInfoFragment();
                    fTransaction.add(R.id.ly_content, userInfoFragment);
                } else {
                    fTransaction.show(userInfoFragment);
                }

                break;
        }

        fTransaction.commit();
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (chatListFragment != null) fragmentTransaction.hide(chatListFragment);
        if (friendFragment != null) fragmentTransaction.hide(friendFragment);
        if (fg3 != null) fragmentTransaction.hide(fg3);
        if (userInfoFragment != null) fragmentTransaction.hide(userInfoFragment);
    }

    //重置所有文本的选中状态
    private void setSelected() {
        tab_menu_home.setSelected(false);
        tab_menu_friend.setSelected(false);
        tab_menu_dynamic.setSelected(false);
        tab_menu_my.setSelected(false);

    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onDestroy() {
        if(receiveMessageService != null) {
            stopService(receiveMessageService);
        }
        super.onDestroy();

    }
}