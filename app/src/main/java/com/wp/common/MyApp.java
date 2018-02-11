package com.wp.common;

import com.wp.commonlibrary.CommonApplication;
import com.wp.commonlibrary.network.NetworkHelper;

/**
 * Created by WangPing on 2018/1/17.
 */

public class MyApp extends CommonApplication {
    private static final String BASE_URL = "http://api.douban.com/";

    @Override
    public void onCreate() {
        super.onCreate();
        NetworkHelper.init(BASE_URL);
    }
}
