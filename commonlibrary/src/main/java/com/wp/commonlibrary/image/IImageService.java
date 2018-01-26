package com.wp.commonlibrary.image;

import android.content.Context;

/**
 * 图片加载服务
 * Created by WangPing on 2018/1/24.
 */

public interface IImageService {
    void loadLocalImage(Context context, DownloadImage image);

    void loadNetImage(Context context, DownloadImage image);
}
