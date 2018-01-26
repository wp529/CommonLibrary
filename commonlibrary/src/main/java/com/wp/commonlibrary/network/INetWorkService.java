package com.wp.commonlibrary.network;

/**
 * 网络请求服务
 * Created by WangPing on 2018/1/17.
 */

public interface INetWorkService {
    void getRequest();

    void postRequest();

    void uploadFile();

    void downloadFile();
}
