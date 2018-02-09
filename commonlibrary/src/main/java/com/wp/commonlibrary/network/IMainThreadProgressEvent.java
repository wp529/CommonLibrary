package com.wp.commonlibrary.network;

/**
 * 需要使用网络进度的View实现
 * Created by WangPing on 2018/1/24.
 */

public interface IMainThreadProgressEvent {
    void start(long totalLength);

    void updateProgress(int progress);

    void end();
}
