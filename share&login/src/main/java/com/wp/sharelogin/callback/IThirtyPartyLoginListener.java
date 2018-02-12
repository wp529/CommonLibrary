package com.wp.sharelogin.callback;

import com.wp.sharelogin.UMengPlatform;
import com.wp.sharelogin.bean.ThirdPartyUserInfo;

/**
 * {@link UMengPlatform}
 * Created by WangPing on 2018/2/12.
 */

public interface IThirtyPartyLoginListener {
    void loginStart(String platform);

    void loginSuccess(String platform, ThirdPartyUserInfo userInfo);

    void loginError(String platform, Throwable throwable);

    void loginCancel(String platform);
}
