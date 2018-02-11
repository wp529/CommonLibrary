package com.wp.commonlibrary.network.retrofit;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Retrofit下载文件
 * Created by WangPing on 2018/1/26.
 */
 interface RetrofitDownloadService {
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);
}
