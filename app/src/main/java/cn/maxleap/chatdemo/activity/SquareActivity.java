package cn.maxleap.chatdemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.maxleap.chatdemo.R;
import cn.maxleap.chatdemo.adapter.SquareAdapter;
import cn.maxleap.chatdemo.bean.SquareBean;
import cn.maxleap.chatdemo.utils.UiUtils;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class SquareActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView      tv_center;
    private TextView      tv_back;
    private ListView      mListView;
    private SquareAdapter squareAdapter;

    private List<SquareBean> ls = new ArrayList<>();
    private PtrClassicFrameLayout ptr_frame;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square);
        UiUtils.setSystemBar(this);
        initView();
    }

    private void initView() {
        tv_back = (TextView) this.findViewById(R.id.tv_back);
        tv_center = (TextView) this.findViewById(R.id.main_center_text);
        mListView = (ListView) this.findViewById(R.id.square_listview);
        ptr_frame = (PtrClassicFrameLayout) this.findViewById(R.id.store_house_ptr_frame);
        setForListView();

        tv_back.setVisibility(View.VISIBLE);
        tv_center.setVisibility(View.VISIBLE);
        tv_center.setText("广场");

        tv_back.setOnClickListener(this);

        for (int i = 0; i < 20; i++) {
            SquareBean squareBean = new SquareBean();
            ls.add(squareBean);
        }

        squareAdapter = new SquareAdapter(this, ls);


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
                        mListView.setAdapter(squareAdapter);

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
        switch (v.getId()) {
            case R.id.tv_back:
                this.finish();
                break;

        }
    }
}
