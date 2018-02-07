package com.wp.commonlibrary.baseMVP;

import com.wp.commonlibrary.network.retrofit.ObservableManager;

/**
 * Created by WangPing on 2018/1/23.
 */

public class BasePresenter<T extends IView> implements IPresenter<T> {
    protected T mView;

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView(T view) {
        //当此页面不可见时的操作
        ObservableManager.getInstance().stopObserver();
        view.dismissLoading();
    }
}
