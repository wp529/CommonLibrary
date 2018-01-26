package com.wp.commonlibrary.baseMVP;

/**
 * MVP中的View层 由Activity或Fragment实现
 * Created by WangPing on 2018/1/23.
 */

public interface IView {
    void showLoading(boolean cancelable);

    void showLoading();

    void dismissLoading();

    void showCommonToast(String msg);

    void showToastWithIcon(String msg);

    boolean isLoadingShow();
}
