package com.wp.commonlibrary.network;

import com.wp.commonlibrary.basemvp.IView;

/**
 * 网络故障回调
 * Created by WangPing on 2018/1/17.
 */

public interface INetworkError {
    /**
     * 网络连接关闭
     *
     * @param view IView
     */
    void networkClosed(IView view);

    /**
     * 网络连接超时
     *
     * @param view IView
     */
    void connectTimeOut(IView view);

    /**
     * 网络连接失败
     *
     * @param view IView
     */
    void connectFail(IView view);
}
