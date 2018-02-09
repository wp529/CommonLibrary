package com.wp.commonlibrary.network;

import com.wp.commonlibrary.baseMVP.IView;

/**
 * 网络故障回调
 * Created by WangPing on 2018/1/17.
 */

public interface INetworkError {
    void networkClosed(IView view);

    void connectTimeOut(IView view);

    void connectFail(IView view);
}
