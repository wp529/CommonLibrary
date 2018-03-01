package com.wp.commonlibrary.network.retrofit;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * 使用Retrofit构建请求
 * Created by WangPing on 2018/2/11.
 */

interface RetrofitRequestService {
    /**
     * Retrofit get请求
     *
     * @param subUrl 子路径
     * @param params 请求参数
     * @return Observable
     */
    @GET("{subUrl}")
    Observable<String> get(@Path("subUrl") String subUrl, @QueryMap Map<String, Object> params);

    /**
     * Retrofit post请求
     *
     * @param subUrl 子路径
     * @param params 请求参数
     * @return Observable
     */
    @FormUrlEncoded
    @POST("{subUrl}")
    Observable<String> post(@Path("subUrl") String subUrl, @FieldMap Map<String, Object> params);
}
