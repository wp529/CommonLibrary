package com.wp.commonlibrary.network.error;

import com.wp.commonlibrary.CommonApplication;
import com.wp.commonlibrary.R;
import com.wp.commonlibrary.basemvp.IView;

/**
 * 网络错误时默认处理,弹个Toast
 * Created by WangPing on 2018/3/1.
 */

public class DefaultNetworkError implements INetworkError {

    @Override
    public void networkClosed(IView view) {
        view.showToastWithIcon(CommonApplication.context.getResources().getString(R.string.network_closed));
    }

    @Override
    public void connectTimeOut(IView view) {
        view.showToastWithIcon(CommonApplication.context.getResources().getString(R.string.connect_timeout));
    }

    @Override
    public void connectFail(IView view) {
        view.showToastWithIcon(CommonApplication.context.getResources().getString(R.string.connect_fail));
    }
}
