package cn.maxleap.chatdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.maxleap.LogInCallback;
import com.maxleap.MLQQUtils;
import com.maxleap.MLUser;
import com.maxleap.MLUserManager;
import com.maxleap.RequestSmsCodeCallback;
import com.maxleap.exception.MLException;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

import cn.maxleap.chatdemo.R;
import cn.maxleap.chatdemo.entiry.MessageEvent;
import cn.maxleap.chatdemo.global.Contants;
import cn.maxleap.chatdemo.utils.PrefUtils;
import cn.maxleap.chatdemo.utils.UiUtils;

public class OtherwayLoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_login;
    private EditText et_username;
    private EditText et_password;

    private String   username="";
    private String   password="";
    private Button btn_yanzhengma;
    private Button btn_time;

    private   int COUNT=60;
    private ImageView qq_login;
    private ImageView weibo_login;
    private ImageView wechat_login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otherwaylogin);

        UiUtils.setSystemBar(this);
        initView();
    }

    private void initView() {
        TextView mCenterText = (TextView) this.findViewById(R.id.main_center_text);
        mCenterText.setVisibility(View.VISIBLE);
        mCenterText.setText("其他登录");
        TextView mRightText = (TextView) this.findViewById(R.id.main_right_text);
        mRightText.setVisibility(View.VISIBLE);
        mRightText.setText("取消");

        btn_login = (Button) this.findViewById(R.id.btn_login);
        et_username = (EditText) this.findViewById(R.id.et_username);
        et_password = (EditText) this.findViewById(R.id.et_password);
        btn_yanzhengma = (Button) this.findViewById(R.id.btn_yanzhengma);
        btn_time = (Button) this.findViewById(R.id.btn_time);
        qq_login = (ImageView) this.findViewById(R.id.qq_loginn);
        weibo_login = (ImageView) this.findViewById(R.id.weibo_login);
        wechat_login = (ImageView) this.findViewById(R.id.wechat_login);

        isButtonEnable();

        mRightText.setOnClickListener(this);
        btn_login.setOnClickListener(this);

        btn_yanzhengma.setOnClickListener(this);


    }

    private void isButtonEnable() {

         et_username.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

             }

             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {
                 username = et_username.getText().toString().trim();
                 if(username.length()==11){
                     btn_yanzhengma.setEnabled(true);
                 }
             }

             @Override
             public void afterTextChanged(Editable s) {
                 username = et_username.getText().toString().trim();
                    if(username.length()!=11){
                        btn_yanzhengma.setEnabled(false);
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
                if(password.length()==6){
                    btn_login.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                password = et_password.getText().toString().trim();
                if(password.length()!=6){
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

    private  TimerTask timerTask=null;
    private  Timer timer;
    
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_right_text:
                finish();
                break;
            case  R.id.btn_yanzhengma:
                yanZhengMa();
                break;
            case R.id.qq_loginn:
                qqLogin();
                break;
            case R.id.weibo_login:
                  weiboLogin();
                break;
            case R.id.wechat_login:
                wechatLogin();
                break;
            case R.id.btn_login:
                SMSCodeLogin();
                break;
        }

    }
     //短信登录
    private void SMSCodeLogin() {
        final String phoneNumber = et_username.getText().toString().trim();
        final String smsCode = et_password.getText().toString().trim();

        MLUserManager.loginWithSmsCodeInBackground(phoneNumber, smsCode, new LogInCallback() {
            @Override
            public void done(MLUser mlUser, MLException e) {
                if(e!=null){
                    Toast.makeText(getApplicationContext(),"登录失败，请重试",Toast.LENGTH_SHORT).show();

                } else{

                   Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                    EventBus.getDefault().post(new MessageEvent(phoneNumber,phoneNumber));
                    PrefUtils.putString(getApplicationContext(), Contants.USERNAME,phoneNumber);
                }

            }
        });
    }

    // TODO: 16/7/8  
    private void wechatLogin() {
    }

    // TODO: 16/7/8  
    private void weiboLogin() {
    }

    //TODO: 16/7/8  
    private void qqLogin() {

        MLQQUtils.logInInBackground(this, new LogInCallback() {
            @Override
            public void done(MLUser user, MLException err) {
                if (user == null) {
                    //用户取消了使用QQ账号登录
                } else if (user.isNew()) {
                    //用户第一次使用QQ账号登录，成功注册并绑定user用户
                } else {
                    //用户使用QQ账号登录成功

                    Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void yanZhengMa() {
        uiChange();
        sendSMSCode();
    }

     //发送短信验证码
    private void sendSMSCode() {
        String phoneNumber = et_username.getText().toString().trim();
        MLUserManager.requestLoginSmsCodeInBackground(phoneNumber, new RequestSmsCodeCallback() {
            @Override
            public void done(MLException e) {
                if(e!=null){
                    Toast.makeText(getApplicationContext(),"发送失败，请重试",Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(),"发送成功",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //点击发送验证码后，界面的变化
    private void uiChange() {
        btn_yanzhengma.setVisibility(View.GONE);
        btn_time.setVisibility(View.VISIBLE);
        timer = new Timer();
        timerTask = new TimerTask() {
           @Override
           public void run() {
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       COUNT--;
                       btn_time.setText(COUNT+" s");
                       if(COUNT<0){
                           stopTimer();
                           stopTimerTask();
                           COUNT=60;
                           btn_yanzhengma.setVisibility(View.VISIBLE);
                           btn_yanzhengma.setEnabled(true);
                           btn_time.setVisibility(View.GONE);
                       }
                   }
               });

           }
       };
        timer.schedule(timerTask, 0, 1000);
    }

    private void stopTimerTask() {
        if(timerTask!=null){
            timerTask.cancel();
            timerTask=null;
        }
    }

    private void stopTimer() {
        if(timer!=null){
            timer.cancel();
            timer=null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
        stopTimerTask();
    }
}
