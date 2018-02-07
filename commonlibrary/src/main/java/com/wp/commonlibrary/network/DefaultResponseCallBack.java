package com.wp.commonlibrary.network;


import com.wp.commonlibrary.baseMVP.IView;

/**
 * Created by WangPing on 2018/1/24.
 */

public abstract class DefaultResponseCallBack<T> implements IResponseCallBack<T> {

    @Override
    public abstract void success(T result);

    @Override
    public void onStart(IView view) {
        view.showLoading();
    }
}
