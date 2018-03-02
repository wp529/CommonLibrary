package com.wp.commonlibrary.network.progress;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * 在主线程中回调的Listener
 * Created by WangPing on 2018/2/9.
 */

public abstract class MainThreadProgressListener implements ProgressListener, IMainThreadProgressEvent {
    private Handler handler;


    public MainThreadProgressListener() {
        handler = new MainThreadProgressHandler(this);
    }

    @Override
    public void onStart(String url, long totalLength) {
        Message msg = Message.obtain();
        msg.what = MainThreadProgressHandler.START;
        Bundle bundle = new Bundle();
        bundle.putString(MainThreadProgressHandler.URL, url);
        bundle.putLong(MainThreadProgressHandler.TOTAL_LENGTH, totalLength);
        msg.setData(bundle);
        handler.sendMessage(msg);
    }

    @Override
    public void onProgress(int progress) {
        Message msg = Message.obtain();
        msg.what = MainThreadProgressHandler.UPDATE;
        msg.obj = progress;
        handler.sendMessage(msg);
    }

    @Override
    public void onEnd(String url) {
        handler.sendEmptyMessage(MainThreadProgressHandler.END);
    }


    @Override
    public void cancel(String url) {
        handler.sendEmptyMessage(MainThreadProgressHandler.CANCEL);
    }

    @Override
    public void networkInterrupt(String url) {
        handler.sendEmptyMessage(MainThreadProgressHandler.INTERRUPT);
    }
}
