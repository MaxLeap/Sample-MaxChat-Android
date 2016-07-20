package cn.maxleap.chatdemo.bean;

import android.graphics.Bitmap;

public class ChatMessageBean {

   public Bitmap bitmap;
   public String text;
   public boolean isme;

    public ChatMessageBean(Bitmap bitmap, String text, boolean isme) {
        this.bitmap = bitmap;
        this.text = text;
        this.isme = isme;
    }
}
