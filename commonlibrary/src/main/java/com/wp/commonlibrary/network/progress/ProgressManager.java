package com.wp.commonlibrary.network.progress;

import java.util.HashMap;
import java.util.Map;

/**
 * 网络请求进度监听器管理
 * Created by Administrator on 2018/1/26.
 */

public class ProgressManager {
    private static final Map<String, ProgressListener> map = new HashMap<>(8);

    public static void addListener(String url, ProgressListener listener) {
        map.put(url, listener);
    }

    public static void removeListener(String url) {
        map.remove(url);
    }

    public static ProgressListener getListener(String url) {
        return map.get(url);
    }

    public static Map<String, ProgressListener> getMap() {
        return map;
    }

}
