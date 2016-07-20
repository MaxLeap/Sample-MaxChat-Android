package cn.maxleap.chatdemo.activity;

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

import com.maxleap.MLUser;
import com.maxleap.MLUserManager;
import com.maxleap.SignUpCallback;
import com.maxleap.exception.MLException;

import org.greenrobot.eventbus.EventBus;

import cn.maxleap.chatdemo.R;
import cn.maxleap.chatdemo.entiry.MessageEvent;
import cn.maxleap.chatdemo.global.Contants;
import cn.maxleap.chatdemo.utils.PrefUtils;
import cn.maxleap.chatdemo.utils.UiUtils;

public class RegistActivity extends AppCompatActivity implements View.OnClickListener {

    private Button   btn_login;
    private EditText et_username;
    private EditText et_password;

    private String username = "";
    private String password = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        UiUtils.setSystemBar(this);
        initView();
    }


    private void initView() {
        TextView mCenterText = (TextView) this.findViewById(R.id.main_center_text);
        mCenterText.setVisibility(View.VISIBLE);
        mCenterText.setText("注册");
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
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                    btn_login.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                username = et_username.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
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
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                    btn_login.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                password = et_password.getText().toString().trim();
                if (TextUtils.isEmpty(password)) {
                    btn_login.setEnabled(false);
                }

            }
        });


    }

    public void finish() {
        // TODO Auto-generated method stub
        super.finish();
        //关闭窗体动画显示
        this.overridePendingTransition(0, R.anim.activity_close);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_right_text:
                finish();
                break;
            case R.id.btn_login:
                regist();
                break;
        }

    }


    private void regist() {

        final String uName = et_username.getText().toString().trim();
        final String pWord = et_password.getText().toString().trim();

        final MLUser user = new MLUser();
        user.setUserName(uName);
        user.setPassword(pWord);


        MLUserManager.signUpInBackground(user, new SignUpCallback() {

            @Override
            public void done(MLException e) {

                if (e == null) {
                    Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();

                    PrefUtils.putString(getApplicationContext(),Contants.USERNAME,uName);

                    //startActivity(new Intent(RegistActivity.this,MainActivity.class));
                    finish();
                    EventBus.getDefault().post(new MessageEvent(uName,""));

                } else {
                    Toast.makeText(getApplicationContext(), " 用户名已经存在", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


}
