package com.wp.sharelogin;

import android.app.Activity;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.wp.commonlibrary.utils.LogUtils;

/**
 * 分享的入口
 * Created by WangPing on 2018/2/11.
 */

public class ShareHelper {
    private static ShareHelper helper;

    private ShareHelper() {
    }

    public static ShareHelper getDefault() {
        if (helper == null) {
            synchronized (ShareHelper.class) {
                if (helper == null) {
                    helper = new ShareHelper();
                }
            }
        }
        return helper;
    }

    public void share2QQ(Activity activity, String content) {
        share(activity, SHARE_MEDIA.QQ, content);
    }

    public void share2WX(Activity activity, String content) {
        share(activity, SHARE_MEDIA.WEIXIN, content);
    }

    public void share2WB(Activity activity, String content) {
        share(activity, SHARE_MEDIA.SINA, content);
    }

    public void share2Ali(Activity activity, String content) {
        share(activity, SHARE_MEDIA.ALIPAY, content);
    }

    private void share(Activity activity, SHARE_MEDIA platform, String content) {
        new ShareAction(activity)
                .setPlatform(platform)//传入平台
                .withText(content)//分享内容
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        LogUtils.e("onStart");
                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        LogUtils.e("onResult");
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        LogUtils.e("onError");
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        LogUtils.e("onCancel");
                    }
                })//回调监听器
                .share();
    }
}
