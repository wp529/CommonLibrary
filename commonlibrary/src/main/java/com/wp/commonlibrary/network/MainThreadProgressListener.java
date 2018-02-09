package com.wp.commonlibrary.network;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

/**
 * 在主线程中回调的Listener
 * Created by WangPing on 2018/2/9.
 */

public class MainThreadProgressListener implements ProgressListener {
    private static final int START = 0X0000001;
    private static final int UPDATE = 0X0000010;
    private static final int END = 0X0000100;

    private Handler handler = new MainThreadProgressHandler();
    private static IMainThreadProgressEvent event;

    public MainThreadProgressListener(@NonNull IMainThreadProgressEvent event) {
        MainThreadProgressListener.event = event;
    }

    @Override
    public void onStart(long totalLength) {
        Message msg = Message.obtain();
        msg.what = START;
        msg.obj = totalLength;
        handler.sendMessage(msg);
    }

    @Override
    public void onProgress(int progress) {
        Message msg = Message.obtain();
        msg.what = UPDATE;
        msg.obj = progress;
        handler.sendMessage(msg);
    }

    @Override
    public void onEnd(String url) {
        handler.sendEmptyMessage(END);
    }

    private static class MainThreadProgressHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case START:
                    if (event != null)
                        event.start((Long) msg.obj);
                    break;
                case UPDATE:
                    if (event != null)
                        event.updateProgress((Integer) msg.obj);
                    break;
                case END:
                    if (event != null)
                        event.end();
                    break;
            }
        }
    }
}
