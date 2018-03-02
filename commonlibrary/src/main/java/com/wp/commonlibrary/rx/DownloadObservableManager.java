package com.wp.commonlibrary.rx;

import java.util.HashMap;
import java.util.Iterator;

import io.reactivex.disposables.Disposable;

/**
 * 下载请求的控制
 * Created by WangPing on 2018/3/2.
 */

public class DownloadObservableManager {
    private static DownloadObservableManager manager;
    private HashMap<String, Disposable> map;

    private DownloadObservableManager() {
        map = new HashMap<>(4);
    }

    public static DownloadObservableManager getInstance() {
        if (manager == null) {
            synchronized (DownloadObservableManager.class) {
                if (manager == null) {
                    manager = new DownloadObservableManager();
                }
            }
        }
        return manager;
    }

    public void addObservable(String url, Disposable d) {
        map.put(url, d);
    }

    public void stopAllObserver() {
        for (String key : map.keySet()) {
            Disposable disposable = map.get(key);
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
        }
        map.clear();
    }

    public void stopObserver(String url) {
        if (!map.containsKey(url)) {
            return;
        }
        Disposable disposable = map.get(url);
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            map.remove(url);
        }
    }
}
