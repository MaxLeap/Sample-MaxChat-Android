package cn.maxleap.chatdemo.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import cn.maxleap.chatdemo.R;
import cn.maxleap.chatdemo.entiry.PhotoEvent;
import cn.maxleap.chatdemo.utils.DeviceInfo;

public class PhotoDialog extends Dialog implements View.OnClickListener{

    private  static  final  String PATH="/mnt/sdcard/test.jpg";

    private Button btn_cancle;
    private Button btn_photo;
    private Button btn_xiangce;

    public PhotoDialog(Context context) {
        super(context, R.style.PhotoDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_photo);
        initView();

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        //window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);

        params.width = DeviceInfo.getWidth(getContext());
        window.setAttributes(params);

    }

    private void initView() {
        btn_cancle = (Button) this.findViewById(R.id.btn_cancle);
        btn_photo = (Button) this.findViewById(R.id.btn_photo);
        btn_xiangce = (Button) this.findViewById(R.id.btn_xiangce);

        btn_cancle.setOnClickListener(this);
        btn_photo.setOnClickListener(this);
        btn_xiangce.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
           switch (v.getId()){
               case  R.id.btn_cancle:
                   this.dismiss();
                   break;
               case  R.id.btn_photo:
                   Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                   imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                           Uri.fromFile(new File(PATH)));// /mnt/sdcard/test.jpg 是照片存储目录
                   //getContext().startActivityForResult(imageCaptureIntent, RESULT_CAPTURE_IMAGE);
                   getContext().startActivity(imageCaptureIntent);
                   this.dismiss();
                   EventBus.getDefault().postSticky(new PhotoEvent(PATH));
                   break;
               case  R.id.btn_xiangce:
                   this.dismiss();
                   break;

           }

    }
}
