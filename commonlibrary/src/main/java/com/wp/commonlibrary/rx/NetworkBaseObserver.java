package com.wp.commonlibrary.rx;


import com.wp.commonlibrary.baseMVP.IView;
import com.wp.commonlibrary.network.INetworkError;
import com.wp.commonlibrary.network.IResponseCallBack;
import com.wp.commonlibrary.utils.LogUtils;
import com.wp.commonlibrary.utils.NetworkUtils;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Rx的NetworkBaseObserver
 * Created by WangPing on 2018/1/17.
 */

public class NetworkBaseObserver<T> extends BaseObserver<T> {
    private static final String TAG = NetworkBaseObserver.class.getName();
    private INetworkError networkError;
    private IResponseCallBack<T> callBack;
    private IView view;

    public NetworkBaseObserver(INetworkError error, IResponseCallBack<T> callBack) {
        this.networkError = error;
        this.callBack = callBack;
    }

    public NetworkBaseObserver(IView view, INetworkError error, IResponseCallBack<T> callBack) {
        this.networkError = error;
        this.callBack = callBack;
        this.view = view;
    }

    @Override
    public void subscribe(Disposable d) {
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
                LogUtils.e(TAG, e.toString());
            }
        } else { //网络连接没打开
            networkError.networkClosed(view);
        }
    }

    @Override
    public void complete() {
        view.dismissLoading();
    }
}
