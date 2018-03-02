package com.wp.commonlibrary.network.netspeed;

import android.content.Context;
import android.net.TrafficStats;
import android.os.Handler;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 网速检测
 * 注意: 需要在显示调用stopShowNetSpeed()停止观测,谨防内存泄漏
 * 一般在不需要的时候或者在onDestroy
 * Created by WangPing on 2018/3/2.
 */

public class NetworkSpeed {
    public static final int NETWORK_SPEED = 0x00000001;
    private Context context;
    private Handler mHandler;
    private long lastTotalRxBytes = 0;
    private long lastTimeStamp = 0;

    public NetworkSpeed(Context context, OnNetworkSpeedUpdateListener listener) {
        this.context = context;
        mHandler = new NetworkSpeedHandler(listener);
    }

    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            showNetSpeed();
        }
    };

    public void startShowNetSpeed() {
        lastTotalRxBytes = getTotalRxBytes();
        lastTimeStamp = System.currentTimeMillis();
        // 1s后启动任务，每2s执行一次
        new Timer().schedule(task, 1000, 1000);
    }

    public void stopShowNetSpeed() {
        task.cancel();
    }

    private long getTotalRxBytes() {
        //转为KB
        return TrafficStats.getUidRxBytes(context.getApplicationInfo().uid) == TrafficStats.UNSUPPORTED ? 0 : (TrafficStats.getTotalRxBytes() / 1024);
    }

    private void showNetSpeed() {
        long nowTotalRxBytes = getTotalRxBytes();
        long nowTimeStamp = System.currentTimeMillis();
        //毫秒转换
        long speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));
        //毫秒转换
        long speed2 = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 % (nowTimeStamp - lastTimeStamp));
        lastTimeStamp = nowTimeStamp;
        lastTotalRxBytes = nowTotalRxBytes;
        Message msg = mHandler.obtainMessage();
        msg.what = NETWORK_SPEED;
        msg.obj = String.valueOf(speed) + "." + String.valueOf(speed2) + " kb/s";
        //更新界面
        mHandler.sendMessage(msg);
    }

    public interface OnNetworkSpeedUpdateListener {
        /**
         * 网速更新回调接口
         *
         * @param speed 网速
         */
        void onNetworkSpeedUpdate(String speed);
    }

    private static class NetworkSpeedHandler extends Handler {
        OnNetworkSpeedUpdateListener listener;

        NetworkSpeedHandler(OnNetworkSpeedUpdateListener listener) {
            this.listener = listener;
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == NETWORK_SPEED && listener != null) {
                listener.onNetworkSpeedUpdate((String) msg.obj);
            }
        }
    }
}
