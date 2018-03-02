package com.wp.commonlibrary.network.progress;

/**
 * 需要使用网络进度的View实现
 * Created by WangPing on 2018/1/24.
 */

public interface IMainThreadProgressEvent {
    /**
     * 网络请求开始
     *
     * @param url         请求的地址
     * @param totalLength 请求总长度
     */
    void start(String url, long totalLength);

    /**
     * 网络请求进度更新
     *
     * @param progress 进度百分比
     */
    void updateProgress(int progress);

    /**
     * 网络请求结束
     */
    void end();

    /**
     * 网络请求取消
     *
     * @param downloaded 已下载
     */
    void cancel(long downloaded);

    /**
     * 网络连接中断
     *
     * @param downloaded 已下载
     */
    void interrupt(long downloaded);
}
