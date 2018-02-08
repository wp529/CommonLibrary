package com.wp.commonlibrary.image;

import android.content.Context;

import com.wp.commonlibrary.network.FileCallBack;

/**
 * 图片加载服务
 * Created by WangPing on 2018/1/24.
 */

public interface IImageService {
    void loadImage(Context context, DownloadImage image);

    void getCacheImage(Context context, String url, FileCallBack callBack);
}
