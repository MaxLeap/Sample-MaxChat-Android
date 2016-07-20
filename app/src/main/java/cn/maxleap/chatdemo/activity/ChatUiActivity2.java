package cn.maxleap.chatdemo.activity;

import org.kymjs.chat.ChatActivity;
import org.kymjs.kjframe.KJActivity;

import cn.maxleap.chatdemo.R;

public class ChatUiActivity2 extends KJActivity {
    @Override
    public void setRootView() {
       setContentView(R.layout.activity_chat2);
        showActivity(aty, ChatActivity.class);
    }
}
