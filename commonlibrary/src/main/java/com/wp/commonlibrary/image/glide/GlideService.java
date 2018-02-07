package com.wp.commonlibrary.image.glide;

import android.content.Context;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wp.commonlibrary.image.DownloadImage;
import com.wp.commonlibrary.image.IImageService;
import com.wp.commonlibrary.network.ProgressManager;
import com.wp.commonlibrary.utils.LogUtils;

/**
 * Created by WangPing on 2018/1/24.
 */

public class GlideService implements IImageService {
    private RequestListener<String, GlideDrawable> glideListener = new RequestListener<String, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
            LogUtils.e(e.getMessage());
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            return false;
        }
    };

    @Override
    public void loadImage(Context context, DownloadImage image) {
        if (image.getListener() != null) {
            ProgressManager.addListener(image.getImagePath(), image.getListener());
        }
        DrawableRequestBuilder<String> loader = Glide.with(context)
                .load(image.getImagePath())
                .placeholder(image.getPlaceHolder())
                .error(image.getError())
                .skipMemoryCache(!image.isMemoryCache())
                .priority(image.getPriority())
                .listener(glideListener);
        if (!image.isAnimate())
            loader.dontAnimate();
        if (!image.isDiskCache())
            loader.diskCacheStrategy(DiskCacheStrategy.NONE);
        if (image.getTrans() != null)
            loader.transform(image.getTrans());
        if (image.getAnimator() != null)
            loader.animate(image.getAnimator());
        loader.into(image.getImageView());
    }
}
