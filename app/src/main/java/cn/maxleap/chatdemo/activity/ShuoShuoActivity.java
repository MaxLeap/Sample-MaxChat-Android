package cn.maxleap.chatdemo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;

import cn.maxleap.chatdemo.R;
import cn.maxleap.chatdemo.event.ShuoShuoPhotoEvent;
import cn.maxleap.chatdemo.event.ShuoShuoXiangCeEvent;
import cn.maxleap.chatdemo.utils.UiUtils;

public class ShuoShuoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_back;
    private TextView tv_center;
    private TextView tv_right;
    private EditText et_shuoshuo;
    private ImageView iv_add_photo;
    private GridView mGridView;

    private Bitmap                             bmp;                      //导入临时图片
    private ArrayList<HashMap<String, Object>> imageItem;
    private SimpleAdapter                      simpleAdapter;     //适配器

    private  String imagePath="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuoshuo2);
        UiUtils.setSystemBar(ShuoShuoActivity.this);
        EventBus.getDefault().register(this);
        initView();
        setView();
    }


    private void setView() {
        bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.add);
        imageItem = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("itemImage", bmp);
        imageItem.add(map);

        simpleAdapter=new SimpleAdapter(this,imageItem,R.layout.griditem_addpic,new String[]{"itemImage"},new int[]{R.id.imageView1});

         /*
         * HashMap载入bmp图片在GridView中不显示,但是如果载入资源ID能显示 如
         * map.put("itemImage", R.drawable.img);
         * 解决方法:
         *              1.自定义继承BaseAdapter实现
         *              2.ViewBinder()接口实现
         *  参考 http://blog.csdn.net/admin_/article/details/7257901
         */
        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {

            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                // TODO Auto-generated method stub
                if(view instanceof ImageView && data instanceof Bitmap){
                    ImageView i = (ImageView)view;
                    i.setImageBitmap((Bitmap) data);
                    return true;
                }
                return false;
            }
        });

        mGridView.setAdapter(simpleAdapter);

       mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               if(position==0){
                     startActivity(new Intent(getApplicationContext(),PhotoDialogActivity2.class));
               } else{
                   Toast.makeText(getApplicationContext(),"大图",Toast.LENGTH_SHORT).show();
               }
           }
       });
        mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    showDeleteDialog(position);
                }
                return true;
            }
        });


    }

    private void showDeleteDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ShuoShuoActivity.this);
        builder.setMessage("确认移除已添加图片吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                imageItem.remove(position);
                simpleAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


    private void initView() {
        tv_back = (TextView) this.findViewById(R.id.tv_back);
        tv_center = (TextView) this.findViewById(R.id.main_center_text);
        tv_right = (TextView) this.findViewById(R.id.main_right_text);
        et_shuoshuo = (EditText) this.findViewById(R.id.et_shuoshuo);
        //iv_add_photo = (ImageView) this.findViewById(R.id.iv_add_photo);
        mGridView = (GridView) this.findViewById(R.id.gridView1);


        tv_back.setVisibility(View.VISIBLE);
        tv_center.setVisibility(View.VISIBLE);
        tv_right.setVisibility(View.VISIBLE);
        tv_center.setText("创建说说");
        tv_right.setText("发表");

        tv_right.setOnClickListener(this);
        tv_back.setOnClickListener(this);
        //iv_add_photo.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.tv_back:
               this.finish();
                break;
            case R.id.main_right_text:

                String shuoshuo = et_shuoshuo.getText().toString().trim();
                    if(TextUtils.isEmpty(shuoshuo)){
                        Toast.makeText(getApplicationContext(),"内容不能为空",Toast.LENGTH_SHORT).show();
                    }

                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void receivePictureFromCamera(ShuoShuoPhotoEvent event){
        if(!TextUtils.isEmpty(event.path)){
            imagePath=event.path;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void  receivePictureFromxiangce(ShuoShuoXiangCeEvent event){

        if(!TextUtils.isEmpty(event.path)){
             imagePath=event.path;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!TextUtils.isEmpty(imagePath)){
            Bitmap addbmp = UiUtils.convertToBitmap(imagePath, 60, 60);
            HashMap<String, Object> map = new HashMap<>();
            map.put("itemImage", addbmp);
            imageItem.add(map);
            simpleAdapter = new SimpleAdapter(this,
                    imageItem, R.layout.griditem_addpic,
                    new String[] { "itemImage"}, new int[] { R.id.imageView1});
            simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Object data,
                                            String textRepresentation) {
                    // TODO Auto-generated method stub
                    if(view instanceof ImageView && data instanceof Bitmap){
                        ImageView i = (ImageView)view;
                        i.setImageBitmap((Bitmap) data);
                        return true;
                    }
                    return false;
                }
            });
            mGridView.setAdapter(simpleAdapter);
            simpleAdapter.notifyDataSetChanged();
            //刷新后释放防止手机休眠后自动添加
            imagePath = null;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
