package cn.maxleap.chatdemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.maxleap.chatdemo.R;
import cn.maxleap.chatdemo.adapter.MyFriendCircleAdapter;
import cn.maxleap.chatdemo.bean.MyFriendCircleBean;
import cn.maxleap.chatdemo.global.Contants;
import cn.maxleap.chatdemo.utils.PrefUtils;
import cn.maxleap.chatdemo.utils.UiUtils;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class MyShuoShuoActivity extends AppCompatActivity implements View.OnClickListener{

    private PtrClassicFrameLayout ptr_frame;
    private ListView mListView;

    private TextView      tv_center;
    private TextView      tv_back;

    private List<MyFriendCircleBean> ls=new ArrayList<>();
    private MyFriendCircleAdapter circleAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_myfriendcircle);
        UiUtils.setSystemBar(this);
        initView();
    }

    private void initView() {
        mListView = (ListView) this.findViewById(R.id.friendcircle_listview);
        ptr_frame = (PtrClassicFrameLayout) this.findViewById(R.id.store_house_ptr_frame);
        tv_back = (TextView) this.findViewById(R.id.tv_back);
        tv_center = (TextView) this.findViewById(R.id.main_center_text);

        tv_center.setVisibility(View.VISIBLE);
        tv_back.setVisibility(View.VISIBLE);
        tv_back.setOnClickListener(this);


        View view = View.inflate(getApplicationContext(), R.layout.my_shuoshuo_headview, null);
        mListView.addHeaderView(view);

        String s = PrefUtils.getString(this, Contants.USERNAME, "");
        if(!TextUtils.isEmpty(s)){
            tv_center.setText(s+"的说说");
        }

        for (int i = 0; i <20 ; i++) {
            MyFriendCircleBean circleBean = new MyFriendCircleBean();
            ls.add(circleBean);
        }
        circleAdapter = new MyFriendCircleAdapter(this, ls);
        setForListView();

    }

    private void setForListView() {
        ptr_frame.setLastUpdateTimeRelateObject(this);
        ptr_frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptr_frame.autoRefresh(true,1000);
            }
        }, 100);


        ptr_frame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mListView.setAdapter(circleAdapter);

                        ptr_frame.refreshComplete();
                    }
                }, 2000);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {

                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                this.finish();
                break;
        }
    }
}