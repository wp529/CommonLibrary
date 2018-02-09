package com.wp.commonlibrary.views;

/**
 * 需要使用网络进度的View实现
 * Created by WangPing on 2018/1/24.
 */

public interface IViewProgressEvent {
    void start();

    void updateProgress(int progress);

    void end();
}
