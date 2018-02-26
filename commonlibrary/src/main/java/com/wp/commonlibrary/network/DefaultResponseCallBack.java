package com.wp.commonlibrary.network;


import com.wp.commonlibrary.baseMVP.IView;

/**
 * 网络请求回调
 * Created by WangPing on 2018/1/24.
 */

public abstract class DefaultResponseCallBack<K> implements IResponseCallBack<K> {

    @Override
    public abstract void success(K result);

    @Override
    public void onStart(IView view) {
        //默认使用不可取消的加载框
        view.showLoading();
    }
}
