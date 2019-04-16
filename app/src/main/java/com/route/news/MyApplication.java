package com.route.news;

import android.app.Application;

import com.route.news.Database.Mydatabase;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Mydatabase.init(this);
    }
}
