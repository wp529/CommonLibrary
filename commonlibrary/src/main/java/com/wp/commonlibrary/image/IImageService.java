package com.wp.commonlibrary.image;

import android.content.Context;

import com.wp.commonlibrary.network.callback.FileCallBack;

/**
 * 图片加载服务
 * Created by WangPing on 2018/1/24.
 */

public interface IImageService {
    /**
     * 加载图片
     * @param context 上下文
     * @param image 图片信息
     */
    void loadImage(Context context, DownloadImage image);

    /**
     * 获取缓存图片
     * @param url 被缓存图片的url
     * @param callBack 缓存图片获取结果回调
     */
    void getCacheImage(String url, FileCallBack callBack);
}
