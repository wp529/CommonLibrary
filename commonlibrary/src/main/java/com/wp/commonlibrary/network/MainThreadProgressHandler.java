package com.wp.commonlibrary.network;

import android.os.Handler;
import android.os.Message;

/**
 * 在主线程中回调进度
 * Created by WangPing on 2018/2/9.
 */

public class MainThreadProgressHandler extends Handler {
    public static final int START = 0X0000001;
    public static final int UPDATE = 0X0000010;
    public static final int END = 0X0000100;
    public static final int CANCEL = 0X0001000;
    private IMainThreadProgressEvent event;

    public MainThreadProgressHandler(IMainThreadProgressEvent listener) {
        this.event = listener;
    }

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
            case CANCEL:
                if (event != null)
                    event.cancel();
                break;
        }
    }
}
