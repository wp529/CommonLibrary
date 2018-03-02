package com.wp.commonlibrary.network.retrofit;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Retrofit下载文件
 * Created by WangPing on 2018/1/26.
 */
interface RetrofitDownloadService {
    /**
     * 文件下载
     *
     * @param range 下载文件的范围
     * @param url   下载文件的网络路径
     * @return Observable
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Header("RANGE") String range, @Url String url);
}
