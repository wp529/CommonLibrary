package com.wp.commonlibrary.network;

import android.os.Handler;
import android.os.Message;
import com.wp.commonlibrary.views.IViewProgressEvent;

/**
 * 网络请求进度
 * Created by WangPing on 2018/1/24.
 */

public class DefaultProgressListener implements ProgressListener {
    private static IViewProgressEvent event;
    private static final int START = 0X0000001;
    private static final int UPDATE = 0X0000010;
    private static final int END = 0X0000100;
    //切换为主线程
    private Handler handler = new DefaultProgressHandler();

    public DefaultProgressListener(IViewProgressEvent event) {
        DefaultProgressListener.event = event;
    }

    public DefaultProgressListener() {

    }

    @Override
    public void onStart(long totalLength) {
        if (event != null)
            handler.sendEmptyMessage(START);
    }

    @Override
    public void onProgress(int progress) {
        if (event != null) {
            Message msg = Message.obtain();
            msg.what = UPDATE;
            msg.obj = progress;
            handler.sendMessage(msg);
        }
    }

    @Override
    public void onEnd(String url) {
        if (event != null)
            handler.sendEmptyMessage(END);
    }

    public static class DefaultProgressHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case START:
                    event.start();
                    break;
                case UPDATE:
                    event.updateProgress((Integer) msg.obj);
                    break;
                case END:
                    event.end();
                    break;
            }
        }
    }
}
