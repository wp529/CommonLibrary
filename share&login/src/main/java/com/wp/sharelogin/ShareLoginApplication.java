package com.wp.sharelogin;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.wp.commonlibrary.CommonApplication;
import com.wp.commonlibrary.IApplication;

/**
 * Created by WangPing on 2018/2/11.
 */

public class ShareLoginApplication implements IApplication {
    @Override
    public void onCreate(CommonApplication application) {
        UMShareAPI.get(application);
        Config.DEBUG = true;
        //微信
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        //新浪
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com/sina2/callback");
        //QQ
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }

}
