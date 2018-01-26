package com.wp.common;

import com.wp.commonlibrary.CommonApplication;
import com.wp.commonlibrary.network.retrofit.RetrofitHelper;

/**
 * Created by WangPing on 2018/1/17.
 */

public class MyApp extends CommonApplication {
    private static final String BASE_URL = "http://xxx.xxx.xx.xx:xxxx/xxx/";

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitHelper.init(BASE_URL);
    }
}
