package com.wp.commonlibrary.network;

/**
 * 需要使用网络进度的View实现
 * Created by WangPing on 2018/1/24.
 */

public interface IMainThreadProgressEvent {
    /**
     * 网络请求开始
     *
     * @param totalLength 请求总长度
     */
    void start(long totalLength);

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
     */
    void cancel();
}
