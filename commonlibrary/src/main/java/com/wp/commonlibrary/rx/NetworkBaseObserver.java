package com.wp.commonlibrary.rx;


import com.wp.commonlibrary.basemvp.IView;
import com.wp.commonlibrary.network.error.INetworkError;
import com.wp.commonlibrary.network.convert.INetworkResultConvert;
import com.wp.commonlibrary.network.callback.IResponseCallBack;
import com.wp.commonlibrary.network.NetworkHelper;
import com.wp.commonlibrary.utils.LogUtils;
import com.wp.commonlibrary.utils.NetworkUtils;

import java.lang.reflect.ParameterizedType;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Rx的NetworkBaseObserver
 * Created by WangPing on 2018/1/17.
 */

public class NetworkBaseObserver<T, K> extends BaseObserver<T> {
    private static final String TAG = NetworkBaseObserver.class.getName();
    private INetworkError networkError;
    private IResponseCallBack<K> callBack;
    private IView view;

    public NetworkBaseObserver(INetworkError error, IResponseCallBack<K> callBack) {
        this.networkError = error;
        this.callBack = callBack;
    }

    public NetworkBaseObserver(IView view, INetworkError error, IResponseCallBack<K> callBack) {
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
        try {
            //observer的泛型
            Class<?> observerClass = t.getClass();
            //callBack的泛型
            Class callBackClass = (Class) ((ParameterizedType) callBack.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            if (observerClass == callBackClass) {
                K k = (K) t;
                callBack.success(k);
            } else if ("java.lang.String".equals(observerClass.getName())) {
                INetworkResultConvert convert = NetworkHelper.getDefault().getConvert();
                String result = (String) t;
                Object o = convert.convert(result, callBackClass);
                K k = (K) o;
                callBack.success(k);
            } else {
                throw new IllegalArgumentException("不知道你要怎么转换,请自定义");
            }
        } catch (Throwable e) {
            e.printStackTrace();
            LogUtils.e("NetworkBaseObserver", "某些非常规操作导致异常");
        }
    }


    @Override
    public void onError(@NonNull Throwable e) {
        view.dismissLoading();
        if (NetworkUtils.isConnected()) {
            if (e.toString().contains("SocketTimeoutException")) {
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
