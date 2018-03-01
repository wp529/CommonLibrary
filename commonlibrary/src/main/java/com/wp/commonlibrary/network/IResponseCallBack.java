package com.wp.commonlibrary.network;

import com.wp.commonlibrary.basemvp.IView;

/**
 * 发起请求时的回调接口
 * Created by WangPing on 2018/1/17.
 */

public interface IResponseCallBack<K> {
    /**
     * 请求成功后的响应
     *
     * @param t 响应数据
     */
    void success(K t);

    /**
     * 请求发起前可做的操作,加载框是否可以取消之类的
     *
     * @param view IView
     */
    void onStart(IView view);
}
