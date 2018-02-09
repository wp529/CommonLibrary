package com.wp.commonlibrary.network;


import com.wp.commonlibrary.baseMVP.IView;

/**
 * 文件下载服务
 * Created by WangPing on 2018/1/26.
 */

public interface IDownloadService {
    void download(IView view, DownloadFile file, FileCallBack callBack);
}
