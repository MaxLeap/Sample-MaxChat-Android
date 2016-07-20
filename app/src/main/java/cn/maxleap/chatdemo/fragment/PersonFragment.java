package cn.maxleap.chatdemo.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.maxleap.chatdemo.activity.PhotoDialogActivity;
import cn.maxleap.chatdemo.R;
import cn.maxleap.chatdemo.entiry.LogOutEvent;
import cn.maxleap.chatdemo.entiry.MessageEvent;
import cn.maxleap.chatdemo.entiry.NiChenBackDialogEvent;
import cn.maxleap.chatdemo.entiry.NiChenEvent;
import cn.maxleap.chatdemo.entiry.PhotoEvent;
import cn.maxleap.chatdemo.entiry.XiangCeEvent;
import cn.maxleap.chatdemo.global.Contants;
import cn.maxleap.chatdemo.utils.PrefUtils;
import cn.maxleap.chatdemo.utils.UiUtils;
import cn.maxleap.chatdemo.view.NiChenDialog;

public class PersonFragment extends BaseFragment implements View.OnClickListener{

    private ListView mListView;

    private List<String> ls = new ArrayList<>();
    private LinearLayout login_success;

    private  boolean isfirst=true;
    private RelativeLayout rel_uname;
    private RelativeLayout rel_number;
    private RelativeLayout rel_nichen;
    private RelativeLayout rel_image;
    private TextView tv_name;
    private TextView tv_number;
    private TextView tv_nichen;
    private ImageView iv_person_pic;


    //接受传递过来的参数
    private  String uName="";
    private  String phoneNumber="";

    @Override
    public View initView() {
        view = View.inflate(getActivity(), R.layout.fragment_person, null);
        setView();
        EventBus.getDefault().register(this);

        //MLUser currentUser = MLUser.getCurrentUser();
        if(TextUtils.isEmpty(PrefUtils.getString(getActivity(),Contants.USERNAME,""))){
            System.out.println("当前用户为空");
            login_success.setVisibility(View.GONE);

        }   else{

           /* tv_name.setText(uName);
            tv_number.setText(phoneNumber);
            login_success.setVisibility(View.VISIBLE);*/
            initName();
            initNiChen();
            initPhoto();

        }


        return view;
    }

    private void initPhoto() {
        Bitmap bitmap = PrefUtils.getBitmap(getActivity(), Contants.BITMAP);
        if(bitmap!=null){
            iv_person_pic.setImageBitmap(bitmap);
        }
    }


    private void initNiChen() {

        String nichen = PrefUtils.getString(getActivity(), Contants.NI_CHEN, "");

        if(!TextUtils.isEmpty(nichen)){
              tv_nichen.setText(nichen);
        }

    }

    private void initName() {
        String uName = PrefUtils.getString(getActivity(), Contants.USERNAME, "");
        if(!TextUtils.isEmpty(uName)){
            login_success.setVisibility(View.VISIBLE);
            tv_name.setText(uName);
        }
    }

    @Subscribe (threadMode = ThreadMode.MAIN,sticky = true)
    public void  showPersonPhoto(final PhotoEvent event) {

        File file = new File(event.path);
        if (file.exists()) {

            Bitmap bitmap =UiUtils.convertToBitmap(event.path,30,30);
            PrefUtils.putBitmap(getActivity(),Contants.BITMAP,bitmap);

            if(bitmap!=null){
                iv_person_pic.setImageBitmap(bitmap);
            }
        }
    }



    @Subscribe (threadMode = ThreadMode.MAIN,sticky = true)
    public void receiveNiChen(NiChenEvent event){
        if(event.nichen!=null){
            tv_nichen.setText(event.nichen);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void receivePictureFromXiangCe(XiangCeEvent event){
        if(!TextUtils.isEmpty(event.path)){
            Bitmap bitmap = UiUtils.convertToBitmap(event.path, 30, 30);
            iv_person_pic.setImageBitmap(bitmap);
        }
    }



    @Subscribe
    public void onEventMainThread(MessageEvent event){
        System.out.println("我是personFragment");
        uName=event.uName;
         phoneNumber=event.uPwd;
        isfirst =false;
        tv_name.setText(event.uName);
        tv_number.setText(event.uPwd);
        login_success.setVisibility(View.VISIBLE);
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(LogOutEvent event){
        System.out.println("哈哈  我退出登录了");
        login_success.setVisibility(View.GONE);
        PrefUtils.putString(getActivity(),Contants.USERNAME,"");
    }



    private void setView() {
        // mListView = (ListView) view.findViewById(R.id.listview);
        login_success = (LinearLayout) view.findViewById(R.id.ll_login_success);

        rel_uname = (RelativeLayout) view.findViewById(R.id.uname);
        rel_number = (RelativeLayout) view.findViewById(R.id.phonenumber);
        rel_nichen = (RelativeLayout) view.findViewById(R.id.nichen);
        rel_image = (RelativeLayout) view.findViewById(R.id.person_image);

        rel_nichen.setOnClickListener(this);
        rel_image.setOnClickListener(this);


        tv_name = (TextView) view.findViewById(R.id.tv_person_name);
        tv_number = (TextView) view.findViewById(R.id.tv_person_number);
        tv_nichen = (TextView) view.findViewById(R.id.tv_person_nichen);
        iv_person_pic = (ImageView) view.findViewById(R.id.iv_person_pic);

    }

    @Override
    protected void initData() {
        ls.clear();
        ls.add("用户名");
        ls.add("手机号");
        ls.add("昵称");
        ls.add("头像");

        //mListView.setAdapter(new PersonFragmentAdapter(getActivity(), ls));

    }

    @Override
    public void onResume() {
        super.onResume();
        initName();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
         switch(v.getId()){
             case R.id.nichen:

                 showNiChenDialog();
                 EventBus.getDefault().postSticky(new NiChenBackDialogEvent(tv_nichen.getText().toString().trim()));

                 break;
             case R.id.person_image:
                 showPhotoDialog();
                 break;
         }
    }

    private void showPhotoDialog() {

      /*  PhotoDialog photoDialog = new PhotoDialog(getActivity());
        photoDialog.show();*/

        Intent intent = new Intent(getActivity(), PhotoDialogActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.activity_open,0);
    }


    private void showNiChenDialog() {
        NiChenDialog niChenDialog = new NiChenDialog(getActivity());
        niChenDialog.show();
    }
}
