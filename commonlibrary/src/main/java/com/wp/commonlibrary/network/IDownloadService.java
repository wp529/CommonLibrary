package com.wp.commonlibrary.network;


/**
 * Created by WangPing on 2018/1/26.
 */

public interface IDownloadService {
    void download(DownloadFile file, FileCallBack callBack);
}
