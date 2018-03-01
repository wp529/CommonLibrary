package com.wp.commonlibrary.network;

import java.io.File;

/**
 * 文件下载回调
 * Created by WangPing on 2018/1/26.
 */

public interface FileCallBack {
    /**
     * 文件下载成功
     *
     * @param file 文件对象
     */
    void downloadSuccess(File file);

    /**
     * 文件下载失败
     *
     * @param e 失败原因
     */
    void downloadFail(Throwable e);
}
