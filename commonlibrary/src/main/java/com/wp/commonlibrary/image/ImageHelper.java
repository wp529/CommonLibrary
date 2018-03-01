package com.wp.commonlibrary.image;

import android.content.Context;

import com.wp.commonlibrary.image.glide.GlideService;
import com.wp.commonlibrary.network.callback.FileCallBack;

/**
 * 图片加载入口
 * Created by WangPing on 2018/2/6.
 */

public class ImageHelper {
    private static ImageHelper helper;
    private static IImageService service;

    private ImageHelper() {
        service = new GlideService();
    }

    public static ImageHelper getDefault() {
        if (helper == null) {
            synchronized (ImageHelper.class) {
                if (helper == null) {
                    helper = new ImageHelper();
                }
            }
        }
        return helper;
    }

    public void loadImage(Context context, DownloadImage image) {
        service.loadImage(context, image);
    }

    public void getCacheImage(String url, FileCallBack callBack) {
        service.getCacheImage(url, callBack);
    }
}
