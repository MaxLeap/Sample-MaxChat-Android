package org.kymjs.chat;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class MyApp extends Application {

    public List<Activity>  ls=null;
    @Override
    public void onCreate() {
        super.onCreate();
        this.ls=new ArrayList<>();
    }
}
