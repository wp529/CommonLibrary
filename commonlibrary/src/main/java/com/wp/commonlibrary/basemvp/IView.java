package com.wp.commonlibrary.basemvp;

/**
 * MVP中的View层 由Activity或Fragment实现
 * Created by WangPing on 2018/1/23.
 */

public interface IView {
    /**
     * 显示加载框
     *
     * @param cancelable 加载框是否可取消
     */
    void showLoading(boolean cancelable);

    /**
     * 显示加载框
     */
    void showLoading();

    /**
     * 隐藏加载框
     */
    void dismissLoading();

    /**
     * 显示系统Toast
     */
    void showCommonToast(String msg);

    /**
     * 显示自定义Toast
     * @param msg 内容
     */
    void showToastWithIcon(String msg);

    /**
     * 加载框是否正在显示
     * @return 是否正在显示
     */
    boolean isLoadingShow();
}
