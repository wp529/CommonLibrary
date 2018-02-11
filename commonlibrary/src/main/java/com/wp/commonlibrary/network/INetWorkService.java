package com.wp.commonlibrary.network;

import com.wp.commonlibrary.baseMVP.IView;

/**
 * 网络请求服务
 * Created by WangPing on 2018/1/17.
 */

public interface INetWorkService {
    /**
     * 有参数
     *
     * @param subUrl 子路径
     * @param params 参数
     */
    void get(IView view, String subUrl, Params params, IResponseCallBack<String> callBack);

    /**
     * 无参数
     *
     * @param subUrl 子路径
     */
    void get(IView view, String subUrl, IResponseCallBack<String> callBack);

    /**
     * 有参数
     *
     * @param subUrl 子路径
     * @param params 参数
     */
    void post(IView view, String subUrl, Params params, IResponseCallBack<String> callBack);

    /**
     * 无参数
     *
     * @param subUrl 子路径
     */
    void post(IView view, String subUrl, IResponseCallBack<String> callBack);

    void download(IView view, DownloadFile file, FileCallBack callBack);

    void changeBaseUrl(String baseUrl);
}
