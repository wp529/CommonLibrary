package com.wp.commonlibrary.basemvp;

import com.wp.commonlibrary.rx.ObservableManager;

/**
 * Presenter的基类
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
