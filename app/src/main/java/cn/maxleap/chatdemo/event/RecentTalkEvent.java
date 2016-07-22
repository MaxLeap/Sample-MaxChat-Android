package cn.maxleap.chatdemo.event;

import android.graphics.Bitmap;

public class RecentTalkEvent {
    public Bitmap bitmap;
    public String name;

    public RecentTalkEvent(String name, Bitmap bitmap) {
        this.name = name;
        this.bitmap = bitmap;
    }
}
