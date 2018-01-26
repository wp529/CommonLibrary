package com.wp.commonlibrary.views;

/**
 * Created by WangPing on 2018/1/24.
 */

public interface IViewProgressEvent {
    void start();

    void updateProgress(int progress);

    void end();
}
