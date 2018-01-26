package com.wp.commonlibrary;

import android.app.Application;
import android.content.Context;

/**
 * 通用的第三方库的初始化
 * 需要继承此Application
 * Created by WangPing on 2018/1/16.
 */

public class CommonApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
