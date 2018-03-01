package com.wp.commonlibrary.basemvp;

/**
 * MVP中的P层 由具体的Presenter实现
 * Created by WangPing on 2018/1/23.
 */

public interface IPresenter<T extends IView> {
    /**
     * Presenter与IView添加依赖关系
     * @param view IView
     */
    void attachView(T view);

    /**
     * Presenter与IView接触依赖关系
     * @param view IView
     */
    void detachView(T view);
}
