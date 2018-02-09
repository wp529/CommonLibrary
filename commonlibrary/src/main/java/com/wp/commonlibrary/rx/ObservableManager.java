package com.wp.commonlibrary.rx;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 请求的控制
 * Created by WangPing on 2018/1/23.
 */

public class ObservableManager {
    private static ObservableManager manager;
    private CompositeDisposable disposable;

    private ObservableManager() {
        disposable = new CompositeDisposable();
    }

    public static ObservableManager getInstance() {
        if (manager == null) {
            synchronized (ObservableManager.class) {
                if (manager == null) {
                    manager = new ObservableManager();
                }
            }
        }
        return manager;
    }

    public void addObservable(Disposable d) {
        disposable.add(d);
    }

    public void stopObserver() {
        disposable.clear();
    }

    public void stopObserver(Disposable d) {
        disposable.remove(d);
    }
}
