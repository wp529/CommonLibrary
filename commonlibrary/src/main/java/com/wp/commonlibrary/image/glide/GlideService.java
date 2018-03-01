package com.wp.commonlibrary.image.glide;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wp.commonlibrary.image.DownloadImage;
import com.wp.commonlibrary.image.GetImageCacheAsyncTask;
import com.wp.commonlibrary.image.IImageService;
import com.wp.commonlibrary.network.FileCallBack;
import com.wp.commonlibrary.network.ProgressManager;
import com.wp.commonlibrary.utils.LogUtils;

/**
 * 图片服务提供者
 * 这里由Glide提供
 * Created by WangPing on 2018/1/24.
 */

public class GlideService implements IImageService {
    @Override
    public void loadImage(Context context, DownloadImage image) {
        if (image.getListener() != null) {
            ProgressManager.addListener(image.getImagePath(), image.getListener());
        }
        DrawableRequestBuilder<String> loader = Glide.with(context).load(image.getImagePath())
                .placeholder(image.getPlaceHolder())
                .error(image.getError())
                .skipMemoryCache(!image.isMemoryCache())
                .priority(image.getPriority())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        LogUtils.e(e.getMessage());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        //计算网络图片比例,为ImageView重新设置高
                        ImageView imageView = image.getImageView();
                        if (imageView == null) {
                            return false;
                        }
                        DisplayMetrics metric = new DisplayMetrics();
                        Activity activity = (Activity) context;
                        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
                        int pxWidth = metric.widthPixels;
                        float ratio = (float) pxWidth / (float) resource.getIntrinsicWidth();
                        float imageHeight = resource.getIntrinsicHeight() * ratio;
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        ViewGroup.LayoutParams lp = imageView.getLayoutParams();
                        lp.width = pxWidth;
                        lp.height = (int) imageHeight;
                        imageView.setLayoutParams(lp);
                        return false;
                    }
                });
        if (!image.isAnimate()) {
            loader.dontAnimate();
        }
        if (!image.isDiskCache()) {
            loader.diskCacheStrategy(DiskCacheStrategy.NONE);
        } else {
            loader.diskCacheStrategy(DiskCacheStrategy.ALL);
        }
        if (image.getTrans() != null) {
            loader.transform(image.getTrans());
        }
        if (image.getAnimator() != null) {
            loader.animate(image.getAnimator());
        }
        loader.into(image.getImageView());
    }

    @Override
    public void getCacheImage(String url, FileCallBack callBack) {
        new GetImageCacheAsyncTask(callBack).execute(url);
    }

}
