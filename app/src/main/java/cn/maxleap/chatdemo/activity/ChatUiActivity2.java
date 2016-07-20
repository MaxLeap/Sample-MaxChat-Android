package cn.maxleap.chatdemo.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import org.kymjs.chat.ChatActivity;
import org.kymjs.kjframe.KJActivity;

import cn.maxleap.chatdemo.R;

public class ChatUiActivity2 extends KJActivity {

    private MyBroadCastReceiver receiver;

    @Override
    public void setRootView() {
       setContentView(R.layout.activity_chat2);
        showActivity(aty, ChatActivity.class);

        receiver = new MyBroadCastReceiver();
        registerReceiver(receiver,new IntentFilter("cn.maxleap.chatdemo"));
    }

    class MyBroadCastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            ChatUiActivity2.this.finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
