package cn.maxleap.chatdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
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
import cn.maxleap.chatdemo.event.PhotoEvent;
import cn.maxleap.chatdemo.event.XiangCeEvent;
import cn.maxleap.chatdemo.utils.DeviceInfo;


public class PhotoDialogActivity extends Activity implements View.OnClickListener {

    private static final String PATH = "/mnt/sdcard/test.jpg";


    private static final int REQUEST_CODE_PHOTO   = 100;
    private static final int REQUEST_CODE_XIANGCE = 101;

    private Button btn_cancle;
    private Button btn_photo;
    private Button btn_xiangce;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_photo);

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
        btn_photo = (Button) this.findViewById(R.id.btn_photo);
        btn_xiangce = (Button) this.findViewById(R.id.btn_xiangce);

        btn_cancle.setOnClickListener(this);
        btn_photo.setOnClickListener(this);
        btn_xiangce.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancle:
                this.finish();
                break;
            case R.id.btn_photo:
                Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(PATH)));// /mnt/sdcard/test.jpg 是照片存储目录
                //getContext().startActivityForResult(imageCaptureIntent, RESULT_CAPTURE_IMAGE);
                startActivityForResult(imageCaptureIntent, REQUEST_CODE_PHOTO);
                break;
            case R.id.btn_xiangce:

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, REQUEST_CODE_XIANGCE);

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        System.out.println("返回的信息");

        if (requestCode == REQUEST_CODE_PHOTO && resultCode == Activity.RESULT_OK) {
            EventBus.getDefault().postSticky(new PhotoEvent(PATH));
        }

        if (requestCode == REQUEST_CODE_XIANGCE && resultCode == Activity.RESULT_OK) {

            if (null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                EventBus.getDefault().postSticky(new XiangCeEvent(picturePath));


            }
        }
        this.finish();
        overridePendingTransition(0,R.anim.activity_close);
    }
}
