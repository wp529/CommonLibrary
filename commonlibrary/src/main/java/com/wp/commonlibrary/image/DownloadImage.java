package com.wp.commonlibrary.image;

import android.widget.ImageView;

import com.wp.commonlibrary.network.DefaultProgressListener;
import com.wp.commonlibrary.network.ProgressListener;
import com.wp.commonlibrary.views.IViewProgressEvent;

/**
 * Image请求信息
 * Created by WangPing on 2018/1/24.
 */

public class DownloadImage {
    private String imagePath;
    private ImageView imageView;
    private ProgressListener listener;

    public DownloadImage(String imagePath, ImageView imageView) {
        this(imagePath, imageView, null);
    }


    public DownloadImage(String imagePath, ImageView imageView, ProgressListener listener) {
        this.imagePath = imagePath;
        this.imageView = imageView;
        if (imageView instanceof IViewProgressEvent) {
            this.listener = new DefaultProgressListener((IViewProgressEvent) imageView);
        } else {
            this.listener = listener;
        }
    }

    public ProgressListener getListener() {
        return listener;
    }

    public void setListener(ProgressListener listener) {
        this.listener = listener;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

}
