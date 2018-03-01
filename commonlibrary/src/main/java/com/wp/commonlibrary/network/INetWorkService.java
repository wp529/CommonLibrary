package com.wp.commonlibrary.network;

import com.wp.commonlibrary.basemvp.IView;
import com.wp.commonlibrary.network.callback.FileCallBack;
import com.wp.commonlibrary.network.callback.IResponseCallBack;

/**
 * 网络请求服务
 * Created by WangPing on 2018/1/17.
 */

public interface INetWorkService {
    /**
     * get请求带参数
     *
     * @param view     IView
     * @param subUrl   子路径
     * @param params   请求参数
     * @param callBack 请求回调
     * @param <K>      callBack泛型
     */
    <K> void get(IView view, String subUrl, Params params, IResponseCallBack<K> callBack);

    /**
     * get请求不带参数
     *
     * @param view     IView
     * @param subUrl   子路径
     * @param callBack 请求回调
     * @param <K>      callBack泛型
     */
    <K> void get(IView view, String subUrl, IResponseCallBack<K> callBack);

    /**
     * post请求带参数
     *
     * @param view     IView
     * @param subUrl   子路径
     * @param params   请求参数
     * @param callBack 请求回调
     * @param <K>      callBack泛型
     */
    <K> void post(IView view, String subUrl, Params params, IResponseCallBack<K> callBack);

    /**
     * post请求不带参数
     *
     * @param view     IView
     * @param subUrl   子路径
     * @param callBack 请求回调
     * @param <K>      callBack泛型
     */
    <K> void post(IView view, String subUrl, IResponseCallBack<K> callBack);

    /**
     * 文件下载
     *
     * @param view     IView
     * @param file     下载信息
     * @param callBack 下载回调
     */
    void download(IView view, DownloadFile file, FileCallBack callBack);

    /**
     * 更换网络请求baseUrl
     *
     * @param baseUrl baseUrl
     */
    void changeBaseUrl(String baseUrl);
}
