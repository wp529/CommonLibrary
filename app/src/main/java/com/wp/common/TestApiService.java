package com.wp.common;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by WangPing on 2018/2/6.
 */

public interface TestApiService {
    //v2/movie/top250?start=25&count=25
    @GET("v2/movie/top250")
    Observable<String> testApi(@Query("start") int start, @Query("count") int count);
}
