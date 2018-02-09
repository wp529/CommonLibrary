package com.wp.commonlibrary.rx;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * rx的BaseObserver
 * Created by WangPing on 2018/2/9.
 */

public abstract class BaseObserver<T> implements Observer<T> {
    private Disposable disposable;

    @Override
    public void onSubscribe(Disposable d) {
        ObservableManager.getInstance().addObservable(d);
        this.disposable = d;
        subscribe(d);
    }

    @Override
    public abstract void onNext(T t);

    @Override
    public abstract void onError(Throwable e);

    @Override
    public void onComplete() {
        ObservableManager.getInstance().stopObserver(disposable);
        complete();
    }

    public abstract void subscribe(Disposable d);

    public abstract void complete();
}
