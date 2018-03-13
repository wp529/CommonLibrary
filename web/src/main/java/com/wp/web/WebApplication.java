package com.wp.web;

import com.tencent.smtt.sdk.QbSdk;
import com.wp.commonlibrary.CommonApplication;
import com.wp.commonlibrary.IApplication;
import com.wp.commonlibrary.utils.LogUtils;

/**
 * Created by WangPing on 2018/3/13.
 */

public class WebApplication implements IApplication {
    @Override
    public void onCreate(CommonApplication application) {
        initTBS(application);
    }

    /**
     * 初始化TBS浏览服务X5内核
     *
     * @param application
     */
    private void initTBS(CommonApplication application) {
        //搜集本地tbs内核信息并上报服务器,服务器返回结果决定使用哪个内核
        //非wifi条件下允许下载X5内核
        QbSdk.setDownloadWithoutWifi(true);
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtils.d("WebApplication", "x5内核加载" + (arg0 ? "成功" : "失败"));
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(application.getApplicationContext(), cb);
    }
}
