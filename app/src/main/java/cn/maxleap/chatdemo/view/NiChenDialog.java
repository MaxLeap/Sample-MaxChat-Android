package cn.maxleap.chatdemo.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.maxleap.chatdemo.R;
import cn.maxleap.chatdemo.entiry.NiChenBackDialogEvent;
import cn.maxleap.chatdemo.entiry.NiChenEvent;
import cn.maxleap.chatdemo.global.Contants;
import cn.maxleap.chatdemo.utils.DeviceInfo;
import cn.maxleap.chatdemo.utils.PrefUtils;

public class NiChenDialog extends Dialog implements View.OnClickListener{

    private EditText et_add_friend;
    private Button btn_ok;
    private Button btn_cancle;

    public NiChenDialog(Context context) {
        super(context, R.style.Dialog);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_friend);

        EventBus.getDefault().register(this);

        initView();

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        //window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setGravity(Gravity.CENTER);


        //dialog的大小
        params.width = DeviceInfo.getWidth(getContext());
        window.setAttributes(params);

    }

    private void initView() {
        et_add_friend = (EditText) this.findViewById(R.id.et_add_friend);
        btn_ok = (Button) this.findViewById(R.id.btn_ok);
        btn_cancle = (Button) this.findViewById(R.id.btn_cancle);
        TextView tv_title = (TextView) this.findViewById(R.id.tv_title);
        tv_title.setText("请输入您的昵称:");

        btn_ok.setOnClickListener(this);
        btn_cancle.setOnClickListener(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void receiveNiChenFromFragmnet(NiChenBackDialogEvent event){
        System.out.println("你好  世界 ");

        et_add_friend.setText(event.nichen);
        et_add_friend.requestFocus();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancle:
                this.dismiss();
            break;
            case R.id.btn_ok:
                // TODO: 16/7/12

                String et_nichen = et_add_friend.getText().toString().trim();
                if(!TextUtils.isEmpty(et_nichen)){
                    PrefUtils.putString(getContext(), Contants.NI_CHEN,et_nichen);
                    EventBus.getDefault().postSticky(new NiChenEvent(et_nichen));
                    this.dismiss();
                }

                break;

        }
    }
}
