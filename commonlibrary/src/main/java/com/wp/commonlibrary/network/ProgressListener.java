package com.wp.commonlibrary.network;

/**
 * Created by WangPing on 2018/1/24.
 */

public interface ProgressListener {
    void onStart(long totalLength);

    void onProgress(int progress);

    void onEnd(String url);
}
