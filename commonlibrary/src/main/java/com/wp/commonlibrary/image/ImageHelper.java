package com.wp.commonlibrary.image;

import android.content.Context;

import com.wp.commonlibrary.image.glide.GlideService;

/**
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
}
