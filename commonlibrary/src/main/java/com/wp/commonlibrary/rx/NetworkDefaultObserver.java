package com.wp.commonlibrary.rx;


import android.support.annotation.NonNull;

import com.wp.commonlibrary.CommonApplication;
import com.wp.commonlibrary.R;
import com.wp.commonlibrary.basemvp.IView;
import com.wp.commonlibrary.network.error.DefaultNetworkError;
import com.wp.commonlibrary.network.error.INetworkError;
import com.wp.commonlibrary.network.callback.IResponseCallBack;


/**
 * NetworkDefaultObserver
 * Created by WangPing on 2018/1/17.
 */

public class NetworkDefaultObserver<T, K> extends NetworkBaseObserver<T, K> {

    public NetworkDefaultObserver(@NonNull IView view, IResponseCallBack<K> callBack) {
        super(view, new DefaultNetworkError(), callBack);
    }

    public NetworkDefaultObserver(@NonNull IView view, INetworkError error, IResponseCallBack<K> callBack) {
        super(view, error, callBack);
    }
}
