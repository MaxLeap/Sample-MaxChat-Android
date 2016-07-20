package cn.maxleap.chatdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import cn.maxleap.chatdemo.R;
import cn.maxleap.chatdemo.utils.DeviceInfo;


public class ContactsDialogActivity extends Activity implements View.OnClickListener {

    private static final String PATH = "/mnt/sdcard/test.jpg";


    private static final int REQUEST_CODE_PHOTO   = 100;
    private static final int REQUEST_CODE_XIANGCE = 101;

    private Button btn_cancle;
    private Button btn_xiangce;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_contacts);

        initView();

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        //window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setGravity(Gravity.BOTTOM);

        params.width = DeviceInfo.getWidth(this);
        window.setAttributes(params);

    }

    private void initView() {
        btn_cancle = (Button) this.findViewById(R.id.btn_cancle);
        btn_xiangce = (Button) this.findViewById(R.id.btn_xiangce);

        btn_cancle.setOnClickListener(this);
        btn_xiangce.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancle:
                this.finish();
                break;
            case R.id.btn_xiangce:
                Toast.makeText(getApplicationContext(),"删除了",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

}
