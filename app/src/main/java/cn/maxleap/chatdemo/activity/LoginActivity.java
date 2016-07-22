package cn.maxleap.chatdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.maxleap.LogInCallback;
import com.maxleap.MLUser;
import com.maxleap.MLUserManager;
import com.maxleap.exception.MLException;

import org.greenrobot.eventbus.EventBus;

import cn.maxleap.chatdemo.R;
import cn.maxleap.chatdemo.event.FriendCircleEvent;
import cn.maxleap.chatdemo.event.MessageEvent;
import cn.maxleap.chatdemo.global.Contants;
import cn.maxleap.chatdemo.utils.PrefUtils;
import cn.maxleap.chatdemo.utils.UiUtils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_login;
    private EditText et_username;
    private EditText et_password;

    private String   username="";
    private String   password="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UiUtils.setSystemBar(this);

        initView();
    }

    private void initView() {
        TextView mCenterText = (TextView) this.findViewById(R.id.main_center_text);
        mCenterText.setVisibility(View.VISIBLE);
        mCenterText.setText("登录");
        TextView mRightText = (TextView) this.findViewById(R.id.main_right_text);
        mRightText.setVisibility(View.VISIBLE);
        mRightText.setText("取消");
        btn_login = (Button) this.findViewById(R.id.btn_login);
        et_username = (EditText) this.findViewById(R.id.et_username);
        et_password = (EditText) this.findViewById(R.id.et_password);

        isButtonEnable();

        mRightText.setOnClickListener(this);
        btn_login.setOnClickListener(this);


    }

    private void isButtonEnable() {

         et_username.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

             }

             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {
                 username = et_username.getText().toString().trim();
                 if(!TextUtils.isEmpty(username)&&!TextUtils.isEmpty(password)){
                     btn_login.setEnabled(true);
                 }
             }

             @Override
             public void afterTextChanged(Editable s) {
                 username = et_username.getText().toString().trim();
                    if(TextUtils.isEmpty(username)){
                            btn_login.setEnabled(false);
                    }
             }
         });

        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password = et_password.getText().toString().trim();
                if(!TextUtils.isEmpty(username)&&!TextUtils.isEmpty(password)){
                    btn_login.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                password = et_password.getText().toString().trim();
                if(TextUtils.isEmpty(password)){
                    btn_login.setEnabled(false);
                }

            }
        });



    }

    public void finish() {
        // TODO Auto-generated method stub
        super.finish();
        //关闭窗体动画显示
        this.overridePendingTransition(0,R.anim.activity_close);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_right_text:
                finish();
                break;
            case R.id.btn_login:
                login();
                break;
        }

    }

    private void login() {
                username=et_username.getText().toString().trim();
                password=et_password.getText().toString().trim();
        MLUserManager.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(MLUser mlUser, MLException e) {

                if(mlUser!=null){
                    Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(new MessageEvent(username,""));
                    PrefUtils.putString(getApplicationContext(), Contants.USERNAME,username);

                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();

                    EventBus.getDefault().postSticky(new FriendCircleEvent());


                } else{
                    Toast.makeText(getApplicationContext(),"您输入的用户名或密码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
