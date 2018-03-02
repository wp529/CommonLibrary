package com.wp.commonlibrary.network.progress;

import android.os.Bundle;
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
    public static final int INTERRUPT = 0X0010000;
    public static final String TOTAL_LENGTH = "totalLength";
    public static final String URL = "url";
    private IMainThreadProgressEvent event;

    public MainThreadProgressHandler(IMainThreadProgressEvent listener) {
        this.event = listener;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case START:
                if (event != null) {
                    Bundle bundle = msg.getData();
                    event.start(bundle.getString(URL), bundle.getLong(TOTAL_LENGTH));
                }
                break;
            case UPDATE:
                if (event != null) {
                    event.updateProgress((Integer) msg.obj);
                }
                break;
            case END:
                if (event != null) {
                    event.end();
                }
                break;
            case CANCEL:
                if (event != null) {
                    event.cancel((Long) msg.obj);
                }
                break;
            case INTERRUPT:
                if (event != null) {
                    event.interrupt((Long) msg.obj);
                }
            default:
                break;
        }
    }
}
