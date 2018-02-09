package com.wp.commonlibrary.network.retrofit;


import android.support.annotation.NonNull;

import com.wp.commonlibrary.CommonApplication;
import com.wp.commonlibrary.R;
import com.wp.commonlibrary.baseMVP.IView;
import com.wp.commonlibrary.network.INetworkError;
import com.wp.commonlibrary.network.IResponseCallBack;


/**
 * DefaultObserver
 * Created by WangPing on 2018/1/17.
 */

public class DefaultObserver<T> extends BaseObserver<T> {

    public DefaultObserver(@NonNull IView view, IResponseCallBack<T> callBack) {
        super(view, new DefaultNetworkError(), callBack);
    }

    public DefaultObserver(@NonNull IView view, INetworkError error, IResponseCallBack<T> callBack) {
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
