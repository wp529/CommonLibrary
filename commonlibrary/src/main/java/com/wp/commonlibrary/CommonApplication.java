package com.wp.commonlibrary;

import android.app.Application;
import android.content.Context;

import com.wp.commonlibrary.module.ModuleConfig;

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
        modulesApplicationInit();
    }

    private void modulesApplicationInit() {
        for (String moduleImpl : ModuleConfig.MODULES_LIST) {
            try {
                Class<?> clazz = Class.forName(moduleImpl);
                Object obj = clazz.newInstance();
                if (obj instanceof IApplication) {
                    ((IApplication) obj).onCreate(this);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}
