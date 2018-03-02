package com.wp.commonlibrary.network.progress;

/**
 * 网络请求进度回调
 * Created by WangPing on 2018/1/24.
 */

public interface ProgressListener {
    /**
     * 网络请求开始
     *
     * @param url         请求地址
     * @param totalLength 总长度
     */
    void onStart(String url, long totalLength);

    /**
     * 网络请求进度更新
     *
     * @param progress 进度百分比
     */
    void onProgress(int progress);

    /**
     * 网络请求结束
     *
     * @param url 请求url
     */
    void onEnd(String url);

    /**
     * 网络请求取消
     *
     * @param url 请求url
     */
    void cancel(String url);

    /**
     * 网络连接中断
     *
     * @param url 请求url
     */
    void networkInterrupt(String url);
}
