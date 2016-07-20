package cn.maxleap.chatdemo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.maxleap.MLUser;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.maxleap.chatdemo.R;
import cn.maxleap.chatdemo.activity2.ShuoActivity;
import cn.maxleap.chatdemo.entiry.FriendCircleEvent;
import cn.maxleap.chatdemo.entiry.LogOutEvent;
import cn.maxleap.chatdemo.entiry.MessageEvent;
import cn.maxleap.chatdemo.entiry.ShowContactsEvent;
import cn.maxleap.chatdemo.entiry.ShowGroupEvent;
import cn.maxleap.chatdemo.fragment.BaseFragment;
import cn.maxleap.chatdemo.fragment.FragmentFactory;
import cn.maxleap.chatdemo.global.Contants;
import cn.maxleap.chatdemo.utils.PrefUtils;
import cn.maxleap.chatdemo.utils.UiUtils;
import cn.maxleap.chatdemo.view.AddFriendDialog;
import cn.maxleap.chatdemo.view.CreateGroupDialog;
import cn.maxleap.chatdemo.view.JoinGroupDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioGroup   mGroup;
    private FrameLayout  mFrameLayout;
    private TextView     mCenterText;
    private TextView     mRightText;
    private TextView     mLeftLogin;
    private TextView     mLeftRegist;
    private LinearLayout mLLButton;
    private Button       btn_friend;
    private Button       btn_group;

    private boolean isFirst = true;
    private Button btn_cancle;
    private Button btn_ok;
    private EditText et_add_friend;
    private AddFriendDialog dialog;

    private CreateGroupDialog createGroupDialog;
    private JoinGroupDialog joinGroupDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        UiUtils.setSystemBar(this);
        EventBus.getDefault().register(this);
        initView();
        if(TextUtils.isEmpty(PrefUtils.getString(this,Contants.USERNAME,""))){
            initTitleForPersonFragmentFirst();
        } else{
            setTitleForPersonFragment();
        }
    }

    private void initView() {

        mCenterText = (TextView) this.findViewById(R.id.main_center_text);
        mRightText = (TextView) this.findViewById(R.id.main_right_text);
        mLeftLogin = (TextView) this.findViewById(R.id.main_left_login);
        mLeftRegist = (TextView) this.findViewById(R.id.main_left_regist);
        mLLButton = (LinearLayout) this.findViewById(R.id.ll_button);
        btn_friend = (Button) this.findViewById(R.id.btn_friend);
        btn_group = (Button) this.findViewById(R.id.btn_group);
        btn_friend.setSelected(true);


        mRightText.setOnClickListener(this);
        mRightText.setTag(mRightText.getText().toString().trim());

        mLeftLogin.setOnClickListener(this);
        mLeftRegist.setOnClickListener(this);
        btn_friend.setOnClickListener(this);
        btn_group.setOnClickListener(this);


        mGroup = (RadioGroup) this.findViewById(R.id.rg_home);
        mFrameLayout = (FrameLayout) this.findViewById(R.id.fl_home);
        mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_person:
                        setCurrentFragmnet(0);
                        break;
                    case R.id.rb_contacts:
                        setCurrentFragmnet(1);
                        break;
                    case R.id.rb_recenttalk:
                        setCurrentFragmnet(2);
                        break;
                    case R.id.rb_friendcircle:
                        setCurrentFragmnet(3);
                        break;
                    case R.id.rb_setting:
                        setCurrentFragmnet(4);
                        break;
                }

            }
        });
        mGroup.check(R.id.rb_person);
    }

    public void setCurrentFragmnet(int position) {
        BaseFragment baseFragment = FragmentFactory.getFragment(position);
        this.getSupportFragmentManager().beginTransaction().replace(R.id.fl_home, baseFragment).commit();

        //MLUser currentUser = MLUser.getCurrentUser();
        String s = PrefUtils.getString(MainActivity.this, Contants.USERNAME, "");
        switch (position) {
            case 0:

                reSetTitle();
                if (TextUtils.isEmpty(s)) {
                    initTitleForPersonFragmentFirst();
                } else {
                    setTitleForPersonFragment();
                }

                break;
            case 1:

                reSetTitle();
                 if(TextUtils.isEmpty(s)){
                     mLLButton.setVisibility(View.VISIBLE);
                 } else{
                     setTitleForContactsFragment();
                 }


                break;
            case 2:

                reSetTitle();
                mCenterText.setVisibility(View.VISIBLE);
                mCenterText.setText("最近聊天");


                break;
            case 3:

                reSetTitle();
                if(TextUtils.isEmpty(s)){
                    mCenterText.setVisibility(View.VISIBLE);
                    mCenterText.setText("朋友圈");
                }  else{
                    setTitleForFriendCircleFragment();
                }
                 EventBus.getDefault().post(new FriendCircleEvent());
                break;
            case 4:

                reSetTitle();
                mCenterText.setVisibility(View.VISIBLE);
                mCenterText.setText("设置");
                break;
        }
    }

    private void setTitleForFriendCircleFragment() {

        mCenterText.setVisibility(View.VISIBLE);
        mCenterText.setText("朋友圈");

        mRightText.setVisibility(View.VISIBLE);
        mRightText.setText("发布说说");
        mRightText.setTag(mRightText.getText().toString().trim());
    }

    private void setTitleForContactsFragment() {

        if(btn_friend.isSelected()){
            reSetTitle();
            mLLButton.setVisibility(View.VISIBLE);
            mRightText.setVisibility(View.VISIBLE);
            mRightText.setText("添加好友");
            mRightText.setTag(mRightText.getText().toString().trim());
        }else{
            reSetTitle();
            mLLButton.setVisibility(View.VISIBLE);
            mRightText.setVisibility(View.VISIBLE);
            mRightText.setText("创建群组");
            mRightText.setTag(mRightText.getText().toString().trim());
            mLeftLogin.setVisibility(View.VISIBLE);
            mLeftLogin.setText("加入群组");
            mLeftLogin.setTag(mLeftLogin.getText().toString().trim());
        }

    }

    private void setTitleForPersonFragment() {

        mRightText.setVisibility(View.VISIBLE);
        mCenterText.setVisibility(View.VISIBLE);
        mRightText.setText("退出登录");
        mRightText.setTag(mRightText.getText().toString().trim());

    }


    private void initTitleForPersonFragmentFirst() {
        mCenterText.setVisibility(View.VISIBLE);
        mCenterText.setText("个人信息");
        mRightText.setVisibility(View.VISIBLE);
        mRightText.setText("其他登录");
        mRightText.setTag(mRightText.getText().toString().trim());

        mLeftLogin.setVisibility(View.VISIBLE);
        mLeftLogin.setText("登录");
        mLeftLogin.setTag(mLeftLogin.getText().toString().trim());

        mLeftRegist.setVisibility(View.VISIBLE);
        mLeftRegist.setText("注册");
        mLLButton.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        System.out.println("tag:" + v.getTag());
        switch (v.getId()) {
            case R.id.main_left_login:

                if("登录".equals(tag)){
                    login();
                } else if("加入群组".equals(tag)){

                    // TODO: 16/7/13

                    showJoinGroupDialog();
                     Toast.makeText(getApplicationContext(),"加入群组",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.main_left_regist:
                Regist();
                break;
            case R.id.main_right_text:

                if ("其他登录".equals(tag)) {
                    otherWayLogin();
                } else  if ("退出登录".equals(tag)){
                    MLUser.logOut();
                    initTitleForPersonFragmentFirst();
                    PrefUtils.putString(this, Contants.USERNAME,"");
                    EventBus.getDefault().postSticky(new LogOutEvent());
                }  else if("添加好友".equals(tag)){

                    showAddFriendDialog();

                    // TODO: 16/7/12
                    Toast.makeText(getApplicationContext(),"添加好友",Toast.LENGTH_SHORT).show();
                } else if("创建群组".equals(tag)){
                    // TODO: 16/7/12
                    showCreateGroupDialog();
                    Toast.makeText(getApplicationContext(),"创建群组",Toast.LENGTH_SHORT).show();
                } else if("发布说说".equals(tag)){
                    // TODO: 16/7/12
                    Toast.makeText(getApplicationContext(),"发布说说",Toast.LENGTH_SHORT).show();

                   //startActivity(new Intent(getApplicationContext(),ShuoShuoActivity.class));
                   startActivity(new Intent(getApplicationContext(), ShuoActivity.class));
                }
                break;
            case R.id.btn_friend:

                btn_friend.setSelected(true);
                btn_friend.setTextColor(Color.parseColor("#7cd12e"));
                btn_group.setTextColor(Color.WHITE);
                btn_group.setSelected(false);

                //MLUser currentUser = MLUser.getCurrentUser();
                if(!TextUtils.isEmpty(PrefUtils.getString(MainActivity.this, Contants.USERNAME, ""))){
                    setTitleForContactsFragment();
                    EventBus.getDefault().postSticky(new ShowContactsEvent());
                }
                /*if(currentUser!=null){
                    setTitleForContactsFragment();
                    EventBus.getDefault().postSticky(new ShowContactsEvent());

                }*/

                break;

            case R.id.btn_group:

                btn_friend.setSelected(false);
                btn_friend.setTextColor(Color.WHITE);

                btn_group.setTextColor(Color.parseColor("#7cd12e"));
                btn_group.setSelected(true);

                /*MLUser currentUser2 = MLUser.getCurrentUser();
                if(currentUser2!=null){
                    setTitleForContactsFragment();
                    EventBus.getDefault().postSticky(new ShowGroupEvent());
                }*/
                if(!TextUtils.isEmpty(PrefUtils.getString(MainActivity.this, Contants.USERNAME, ""))){
                    setTitleForContactsFragment();
                    EventBus.getDefault().postSticky(new ShowGroupEvent());
                }
                break;

        }


    }

    private void showJoinGroupDialog() {
        joinGroupDialog = new JoinGroupDialog(this);
        joinGroupDialog.show();
    }

    private void showCreateGroupDialog() {
        createGroupDialog = new CreateGroupDialog(this);

        createGroupDialog.show();
    }

    private void showAddFriendDialog() {

        dialog = new AddFriendDialog(this);

        //点击空白处dialog不消失
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void otherWayLogin() {
        startActivity(new Intent(MainActivity.this, OtherwayLoginActivity.class));
        overridePendingTransition(R.anim.activity_open, 0);
    }

    private void Regist() {
        startActivity(new Intent(MainActivity.this, RegistActivity.class));
        overridePendingTransition(R.anim.activity_open, 0);
    }

    private void login() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        overridePendingTransition(R.anim.activity_open, 0);
    }


    @Subscribe
    public void onEventMainThread(MessageEvent event) {

        System.out.println("我是MainActivity");
        mGroup.check(R.id.rb_person);
        isFirst = false;
        reSetTitle();
        setTitleForPersonFragment();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

    }


    private void reSetTitle() {
        mLeftLogin.setVisibility(View.GONE);
        mLeftRegist.setVisibility(View.GONE);
        mCenterText.setVisibility(View.GONE);
        mLLButton.setVisibility(View.GONE);
        mRightText.setVisibility(View.GONE);
    }

/*
    @Override
    protected void onResume() {
        super.onResume();
        MLUser currentUser = MLUser.getCurrentUser();
        reSetTitle();
        if (currentUser == null) {
            initTitleForPersonFragmentFirst();
        } else {
            setTitleForPersonFragment();
        }

    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        //MLUser.logOut();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("main得到的信息");

    }

}
