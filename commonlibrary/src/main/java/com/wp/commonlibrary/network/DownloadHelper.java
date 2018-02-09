package com.wp.commonlibrary.network;

import com.wp.commonlibrary.baseMVP.IView;
import com.wp.commonlibrary.network.retrofit.RetrofitDownloadService;

/**
 * Created by WangPing on 2018/2/9.
 */

public class DownloadHelper {
    private static DownloadHelper helper;
    private static IDownloadService service;

    private DownloadHelper() {
        service = new RetrofitDownloadService();
    }

    public static DownloadHelper getDefault() {
        if (helper == null) {
            synchronized (DownloadHelper.class) {
                if (helper == null) {
                    helper = new DownloadHelper();
                }
            }
        }
        return helper;
    }

    public void download(IView view, DownloadFile downloadFile, FileCallBack callBack) {
        service.download(view, downloadFile, callBack);
    }
}
