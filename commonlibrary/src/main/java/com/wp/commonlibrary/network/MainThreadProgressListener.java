package com.wp.commonlibrary.network;

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
    public void onStart(long totalLength) {
        Message msg = Message.obtain();
        msg.what = MainThreadProgressHandler.START;
        msg.obj = totalLength;
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

}
