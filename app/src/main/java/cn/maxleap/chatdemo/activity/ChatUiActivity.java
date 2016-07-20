package cn.maxleap.chatdemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.maxleap.chatdemo.R;
import cn.maxleap.chatdemo.adapter.ChatUiAdapter;
import cn.maxleap.chatdemo.bean.ChatMessageBean;
import cn.maxleap.chatdemo.utils.UiUtils;

public class ChatUiActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_back;
    private TextView tv_center;
    private ListView mListView;
    private EditText et_send;
    private Button btn_send;
    private ImageView iv_add;


    private String mCenter;

    private List<ChatMessageBean> ls=new ArrayList<>();

    public final static int           OTHER          = 1;
    public final static int           ME             = 0;
    private ChatUiAdapter chatUiAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mCenter = getIntent().getStringExtra("name");
        UiUtils.setSystemBar(this);
        initView();
    }

    private void initView() {

        tv_back = (TextView) this.findViewById(R.id.tv_back);
        tv_center = (TextView) this.findViewById(R.id.main_center_text);
        tv_back.setVisibility(View.VISIBLE);
        tv_back.setText("返回");

        tv_center.setVisibility(View.VISIBLE);
        tv_center.setText(mCenter);

        mListView = (ListView) this.findViewById(R.id.chat_listview);
        et_send = (EditText) this.findViewById(R.id.et_send);
        btn_send = (Button) this.findViewById(R.id.btn_send);
        iv_add = (ImageView) this.findViewById(R.id.iv_add);

        tv_back.setOnClickListener(this);
        btn_send.setOnClickListener(this);

        chatUiAdapter = new ChatUiAdapter(this, ls);
        mListView.setAdapter(chatUiAdapter);
        mListView.setSelection(ls.size()-1);

    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.tv_back:
               this.finish();
               break;
           case R.id.btn_send:
               String message_send = et_send.getText().toString().trim();
               if(!TextUtils.isEmpty(message_send)){
                   ChatMessageBean bean = new ChatMessageBean(null, message_send, true);
                      ls.add(bean);
                   chatUiAdapter.notifyDataSetChanged();
               }
               break;
       }
    }
}
