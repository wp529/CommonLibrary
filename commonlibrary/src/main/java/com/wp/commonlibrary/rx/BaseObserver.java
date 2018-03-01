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

    /**
     * 操作成功
     *
     * @param t 成功的结果
     */
    @Override
    public abstract void onNext(T t);

    /**
     * 操作失败
     *
     * @param e 失败的原因
     */
    @Override
    public abstract void onError(Throwable e);

    @Override
    public void onComplete() {
        ObservableManager.getInstance().stopObserver(disposable);
        complete();
    }

    /**
     * 订阅关系建立
     *
     * @param d Disposable
     */
    public abstract void subscribe(Disposable d);

    /**
     * 处理完成
     */
    public abstract void complete();
}
