package cn.maxleap.chatdemo.bean;

import android.graphics.Bitmap;

public class RecentChatBean {
    public Bitmap bitmap;
    public String username;

    public RecentChatBean(Bitmap bitmap, String username) {
        this.bitmap = bitmap;
        this.username = username;
    }
}
