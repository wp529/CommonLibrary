package com.wp.commonlibrary.network;

import android.text.TextUtils;

import com.wp.commonlibrary.baseMVP.IView;
import com.wp.commonlibrary.network.retrofit.RetrofitNetworkService;

/**
 * 网络请求入口
 * Created by WangPing on 2018/2/11.
 */

public class NetworkHelper {
    private static String BASE_URL;
    private static NetworkHelper helper;
    private INetWorkService service;

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

    public static void init(String baseUrl) {
        BASE_URL = baseUrl;
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
