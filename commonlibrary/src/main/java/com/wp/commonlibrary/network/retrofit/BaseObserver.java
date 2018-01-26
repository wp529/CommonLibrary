package com.wp.commonlibrary.network.retrofit;


import com.wp.commonlibrary.baseMVP.IView;
import com.wp.commonlibrary.network.INetworkError;
import com.wp.commonlibrary.network.IResponseCallBack;
import com.wp.commonlibrary.utils.LogUtils;
import com.wp.commonlibrary.utils.NetworkUtils;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by WangPing on 2018/1/17.
 */

class BaseObserver<T> implements Observer<T> {
    private INetworkError networkError;
    private IResponseCallBack<T> callBack;
    private IView view;

    public BaseObserver(INetworkError error, IResponseCallBack<T> callBack) {
        this.networkError = error;
        this.callBack = callBack;
    }

    public BaseObserver(IView view, INetworkError error, IResponseCallBack<T> callBack) {
        this.networkError = error;
        this.callBack = callBack;
        this.view = view;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        ObservableManager.getInstance().addObservable(d);
        callBack.onStart(view);
    }

    @Override
    public void onNext(@NonNull T t) {
        callBack.success(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        view.dismissLoading();

        if (NetworkUtils.isConnected()) {
            if (e.toString().contains("SocketTimeoutException")) { //请求超时
                networkError.connectTimeOut(view);
            } else { //请求失败
                networkError.connectFail(view);
                LogUtils.e(e.toString());
            }
        } else { //网络连接没打开
            networkError.networkClosed(view);
        }
    }

    @Override
    public void onComplete() {
        view.dismissLoading();
    }
}
