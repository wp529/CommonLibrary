package com.wp.sharelogin.callback;

import com.wp.sharelogin.UMengPlatform;

/**
 * 分享的结果监听
 * {@link UMengPlatform}
 * Created by WangPing on 2018/2/12.
 */

public interface IThirtyPartyShareListener {
    void onShareStart(String platform);

    void onShareEnd(String platform);

    void onShareError(String platform, Throwable throwable);

    void onShareCancel(String platform);
}
