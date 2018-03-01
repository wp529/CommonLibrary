package com.wp.commonlibrary.network;

import android.text.TextUtils;

import com.wp.commonlibrary.basemvp.IView;
import com.wp.commonlibrary.network.callback.FileCallBack;
import com.wp.commonlibrary.network.callback.IResponseCallBack;
import com.wp.commonlibrary.network.convert.INetworkResultConvert;
import com.wp.commonlibrary.network.convert.JsonConvert;
import com.wp.commonlibrary.network.retrofit.RetrofitNetworkService;

/**
 * 网络请求入口
 * Created by WangPing on 2018/2/11.
 */

public class NetworkHelper {
    private static String BASE_URL;
    private static NetworkHelper helper;
    private INetWorkService service;
    private INetworkResultConvert convert;

    private NetworkHelper() {
        if (TextUtils.isEmpty(BASE_URL)) {
            throw new NullPointerException("在使用前,你需要先设置RetrofitHelper的BASE_URL");
        }
        service = new RetrofitNetworkService(BASE_URL);
    }

    public static NetworkHelper getDefault() {
        if (helper == null) {
            synchronized (NetworkHelper.class) {
                if (helper == null) {
                    helper = new NetworkHelper();
                }
            }
        }
        return helper;
    }

    /**
     * 初始化网络请求baseUrl
     *
     * @param baseUrl baseUrl
     */
    public static void init(String baseUrl) {
        BASE_URL = baseUrl;
    }

    /**
     * 设置网络请求结果转换器
     *
     * @param convert 转换器
     */
    public void setConvert(INetworkResultConvert convert) {
        this.convert = convert;
    }

    public INetworkResultConvert getConvert() {
        if (this.convert == null){
            this.convert = new JsonConvert();
        }
        return this.convert;
    }

    public <K> void get(IView view, String subUrl, Params params, IResponseCallBack<K> callBack) {
        service.get(view, subUrl, params, callBack);
    }

    public <K> void post(IView view, String subUrl, Params params, IResponseCallBack<K> callBack) {
        service.post(view, subUrl, params, callBack);
    }

    public <K> void get(IView view, String subUrl, IResponseCallBack<K> callBack) {
        service.get(view, subUrl, null, callBack);
    }

    public <K> void post(IView view, String subUrl, IResponseCallBack<K> callBack) {
        service.post(view, subUrl, null, callBack);
    }

    public void download(IView view, DownloadFile downloadFile, FileCallBack callBack) {
        service.download(view, downloadFile, callBack);
    }


    public NetworkHelper changeBaseUrl(String baseUrl) {
        if (BASE_URL.equals(baseUrl)) {
            throw new IllegalStateException("两次的baseUrl不应该一致");
        }
        service.changeBaseUrl(baseUrl);
        return getDefault();
    }
}
