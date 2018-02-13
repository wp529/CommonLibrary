package com.wp.sharelogin;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.wp.commonlibrary.CommonApplication;
import com.wp.commonlibrary.IApplication;
import com.wp.commonlibrary.utils.LogUtils;

/**
 * Created by WangPing on 2018/2/11.
 */

public class ShareLoginApplication implements IApplication {
    @Override
    public void onCreate(CommonApplication application) {
        UMShareAPI.get(application);
        //正式发版需要屏蔽掉 还需在Manifest里设置UMENG需要的APPKEY
        Config.DEBUG = true;
        //微信开放平台
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        //新浪开放平台
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
        //QQ开放平台  还需在Manifest里设置QQ需要的AppId
        PlatformConfig.setQQZone("id", "key");
        //阿里开放平台
        PlatformConfig.setAlipay("id");
        LogUtils.e("onCreate");
    }

}
