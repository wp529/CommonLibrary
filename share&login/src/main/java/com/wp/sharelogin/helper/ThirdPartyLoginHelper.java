package com.wp.sharelogin.helper;

import android.app.Activity;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.wp.sharelogin.callback.IThirtyPartyLoginListener;
import com.wp.sharelogin.utils.UMengUtils;

import java.util.Map;

/**
 * 三方登录
 * Created by WangPing on 2018/2/12.
 */

public class ThirdPartyLoginHelper {
    private static ThirdPartyLoginHelper helper;

    private ThirdPartyLoginHelper() {
    }

    public static ThirdPartyLoginHelper getDefault() {
        if (helper == null) {
            synchronized (ThirdPartyLoginHelper.class) {
                if (helper == null) {
                    helper = new ThirdPartyLoginHelper();
                }
            }
        }
        return helper;
    }

    public void login2QQ(Activity activity, IThirtyPartyLoginListener listener) {
        login(activity, SHARE_MEDIA.QQ, listener);
    }

    public void login2WX(Activity activity, IThirtyPartyLoginListener listener) {
        login(activity, SHARE_MEDIA.WEIXIN, listener);
    }

    public void login2WB(Activity activity, IThirtyPartyLoginListener listener) {
        login(activity, SHARE_MEDIA.SINA, listener);
    }

    public void login2Ali(Activity activity, IThirtyPartyLoginListener listener) {
        login(activity, SHARE_MEDIA.ALIPAY, listener);
    }

    private void login(Activity activity, SHARE_MEDIA platform, final IThirtyPartyLoginListener listener) {
        UMShareAPI.get(activity).getPlatformInfo(activity, platform, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                if (listener != null)
                    listener.loginStart(UMengUtils.buildPlatform(share_media));
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                if (listener != null)
                    listener.loginSuccess(UMengUtils.buildPlatform(share_media), UMengUtils.buildUserInfo(map));
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                if (listener != null)
                    listener.loginError(UMengUtils.buildPlatform(share_media), throwable);
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                if (listener != null)
                    listener.loginCancel(UMengUtils.buildPlatform(share_media));
            }
        });
    }
}
