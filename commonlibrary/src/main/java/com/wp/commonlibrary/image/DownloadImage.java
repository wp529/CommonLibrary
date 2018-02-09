package com.wp.commonlibrary.image;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.wp.commonlibrary.R;
import com.wp.commonlibrary.network.DefaultProgressListener;
import com.wp.commonlibrary.network.ProgressListener;
import com.wp.commonlibrary.views.IViewProgressEvent;

/**
 * 加载Image时需要构造的信息
 * Created by WangPing on 2018/1/24.
 */

public class DownloadImage {
    private String imagePath;
    private ImageView imageView;
    private ProgressListener listener;
    private int placeHolder;
    private int error;
    private boolean isAnimate;
    private boolean memoryCache;
    private boolean diskCache;
    private Priority priority;
    private Transformation trans;
    private ViewPropertyAnimation.Animator animator;

    private DownloadImage(Builder builder) {
        if (TextUtils.isEmpty(builder.path) || builder.imageView == null)
            throw new IllegalArgumentException("图片路径和加载图片的容器不能为空");
        this.imagePath = builder.path;
        this.imageView = builder.imageView;
        if (builder.imageView instanceof IViewProgressEvent) {
            this.listener = new DefaultProgressListener((IViewProgressEvent) imageView);
        } else {
            this.listener = builder.listener;
        }
        this.placeHolder = builder.placeHolder;
        this.error = builder.error;
        this.isAnimate = builder.isAnimate;
        this.memoryCache = builder.memoryCache;
        this.diskCache = builder.diskCache;
        this.priority = builder.priority;
        this.trans = builder.trans;
        this.animator = builder.animator;

    }

    public int getPlaceHolder() {
        return placeHolder;
    }

    public int getError() {
        return error;
    }

    public boolean isAnimate() {
        return isAnimate;
    }

    public boolean isMemoryCache() {
        return memoryCache;
    }

    public boolean isDiskCache() {
        return diskCache;
    }

    public Priority getPriority() {
        return priority;
    }

    public Transformation getTrans() {
        return trans;
    }

    public ViewPropertyAnimation.Animator getAnimator() {
        return animator;
    }

    public ProgressListener getListener() {
        return listener;
    }

    public String getImagePath() {
        return imagePath;
    }


    public ImageView getImageView() {
        return imageView;
    }


    public static class Builder {
        private String path;
        private ImageView imageView;
        private ProgressListener listener;
        private int placeHolder = R.drawable.common_library_shape_gray_rect;
        private int error = R.drawable.common_library_img_error;
        private boolean isAnimate = true;
        private boolean memoryCache = true;
        private boolean diskCache = true;
        private Priority priority = Priority.NORMAL;
        private Transformation trans;
        private ViewPropertyAnimation.Animator animator;

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Builder targetView(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public Builder progess(ProgressListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder placeHolder(int placeHolder) {
            this.placeHolder = placeHolder;
            return this;
        }

        public Builder errorPic(int error) {
            this.error = error;
            return this;
        }

        public Builder animate(boolean isAnimate) {
            this.isAnimate = isAnimate;
            return this;
        }

        public Builder memoryCache(boolean memoryCache) {
            this.memoryCache = memoryCache;
            return this;
        }

        public Builder diskCache(boolean diskCache) {
            this.diskCache = diskCache;
            return this;
        }

        public Builder priority(Priority priority) {
            this.priority = priority;
            return this;
        }

        public Builder transformation(Transformation trans) {
            this.trans = trans;
            return this;
        }

        public Builder animator(ViewPropertyAnimation.Animator animator) {
            this.animator = animator;
            return this;
        }

        public DownloadImage build() {
            return new DownloadImage(this);
        }
    }
}
