package com.wp.commonlibrary.image.glide;

import android.content.Context;

import com.wp.commonlibrary.image.DownloadImage;

/**
 * Glide使用入口类
 * Created by WangPing on 2018/1/24.
 */

public class GlideHelper {
    private static GlideHelper helper;
    private static GlideService service;

    private GlideHelper() {
        service = new GlideService();
    }


    public static GlideHelper getDefault() {
        if (helper == null) {
            synchronized (GlideHelper.class) {
                if (helper == null) {
                    helper = new GlideHelper();
                }
            }
        }
        return helper;
    }

    public void loadLocalImage(Context context, DownloadImage image) {
        service.loadLocalImage(context, image);
    }

    public void loadNetImage(Context context, DownloadImage image) {
        service.loadNetImage(context, image);
    }
}
