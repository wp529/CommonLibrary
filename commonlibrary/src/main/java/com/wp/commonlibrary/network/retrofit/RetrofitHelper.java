package com.wp.commonlibrary.network.retrofit;

import android.text.TextUtils;
import android.util.LruCache;

import com.wp.commonlibrary.network.ProgressInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Retrofit使用入口类
 * Created by WangPing on 2018/1/17.
 */

public class RetrofitHelper {
    private static RetrofitHelper manager;
    private static Retrofit retrofitClient;
    private static String BASE_URL;
    private LruCache<String, Object> serviceCache;

    private RetrofitHelper() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(new ProgressInterceptor())
                .retryOnConnectionFailure(true);

        if (TextUtils.isEmpty(BASE_URL)) {
            throw new NullPointerException("在使用前,你需要先设置RetrofitHelper的BASE_URL");
        }
        retrofitClient = new Retrofit.Builder()
                .addConverterFactory(StringConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build())
                .baseUrl(BASE_URL)
                .build();

        serviceCache = new LruCache<>(8);
    }

    public static void init(String baseUrl) {
        BASE_URL = baseUrl;
    }

    public static RetrofitHelper getDefault() {
        if (manager == null) {
            synchronized (RetrofitHelper.class) {
                if (manager == null) {
                    manager = new RetrofitHelper();
                }
            }
        }
        return manager;
    }

    public void changeBaseUrl(String baseUrl) {
        if (BASE_URL.equals(baseUrl)) {
            throw new IllegalStateException("两次的baseUrl不应该一致");
        }
        retrofitClient = retrofitClient.newBuilder()
                .baseUrl(baseUrl).build();
    }

    public <T> T getService(Class<T> clazz) {
        String serviceName = clazz.getName();
        T t = (T) serviceCache.get(serviceName);
        if (t == null) {
            t = retrofitClient.create(clazz);
            serviceCache.put(serviceName, t);
        }
        return t;
    }
}
