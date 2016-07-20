package cn.maxleap.chatdemo.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.maxleap.chatdemo.R;
import cn.maxleap.chatdemo.utils.DeviceInfo;

public class JoinGroupDialog extends Dialog implements View.OnClickListener{

    private EditText et_add_friend;
    private Button btn_ok;
    private Button btn_cancle;

    public JoinGroupDialog(Context context) {
        super(context, R.style.Dialog);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_friend);
        initView();

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        //window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);

        params.y = 200;

        //dialog的大小
        params.width = DeviceInfo.getWidth(getContext());


        window.setAttributes(params);

    }

    private void initView() {
        et_add_friend = (EditText) this.findViewById(R.id.et_add_friend);
        btn_ok = (Button) this.findViewById(R.id.btn_ok);
        btn_cancle = (Button) this.findViewById(R.id.btn_cancle);
        TextView tv_title = (TextView) this.findViewById(R.id.tv_title);
        tv_title.setText("请输入您要加入的群组ID");

        btn_ok.setOnClickListener(this);
        btn_cancle.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancle:
                this.dismiss();
            break;
            case R.id.btn_ok:
                // TODO: 16/7/12


            break;

        }
    }
}
