package com.wp.commonlibrary.baseMVP;

/**
 * MVP中的P层 由具体的Presenter实现
 * Created by WangPing on 2018/1/23.
 */

public interface IPresenter {
    void attachView(IView view);

    void detachView(IView view);
}
