package com.wp.commonlibrary.rx;


import android.support.annotation.NonNull;

import com.wp.commonlibrary.CommonApplication;
import com.wp.commonlibrary.R;
import com.wp.commonlibrary.baseMVP.IView;
import com.wp.commonlibrary.network.INetworkError;
import com.wp.commonlibrary.network.IResponseCallBack;


/**
 * NetworkDefaultObserver
 * Created by WangPing on 2018/1/17.
 */

public class NetworkDefaultObserver<T,K> extends NetworkBaseObserver<T,K> {

    public NetworkDefaultObserver(@NonNull IView view, IResponseCallBack<K> callBack) {
        super(view, new DefaultNetworkError(), callBack);
    }

    public NetworkDefaultObserver(@NonNull IView view, INetworkError error, IResponseCallBack<K> callBack) {
        super(view, error, callBack);
    }

    public static class DefaultNetworkError implements INetworkError {

        @Override
        public void networkClosed(IView view) {
            view.showToastWithIcon(CommonApplication.context.getResources().getString(R.string.network_closed));
        }

        @Override
        public void connectTimeOut(IView view) {
            view.showToastWithIcon(CommonApplication.context.getResources().getString(R.string.connect_timeout));
        }

        @Override
        public void connectFail(IView view) {
            view.showToastWithIcon(CommonApplication.context.getResources().getString(R.string.connect_fail));
        }
    }
}
