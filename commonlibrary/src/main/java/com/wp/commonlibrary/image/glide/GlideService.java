package com.wp.commonlibrary.image.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wp.commonlibrary.image.DownloadImage;
import com.wp.commonlibrary.image.IImageService;
import com.wp.commonlibrary.network.ProgressManager;

/**
 * Created by WangPing on 2018/1/24.
 */

public class GlideService implements IImageService {
    @Override
    public void loadLocalImage(Context context, DownloadImage image) {
        Glide.with(context).load(image.getImagePath()).into(image.getImageView());
    }

    @Override
    public void loadNetImage(Context context, DownloadImage image) {
        if (image.getListener() != null) {
            ProgressManager.addListener(image.getImagePath(), image.getListener());
        }
        Glide.with(context).load(image.getImagePath()).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(image.getImageView());
    }
}
