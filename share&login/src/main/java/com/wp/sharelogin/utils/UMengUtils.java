package com.wp.sharelogin.utils;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.wp.sharelogin.UMengPlatform;
import com.wp.sharelogin.bean.ThirdPartyUserInfo;

import java.util.Map;

/**
 * 友盟三方平台转换的工具
 * Created by WangPing on 2018/2/12.
 */

public final class UMengUtils {
    public static String buildPlatform(SHARE_MEDIA share_media) {
        String result = UMengPlatform.OTHER;
        if (share_media == SHARE_MEDIA.QQ) {
            result = UMengPlatform.QQ;
        } else if (share_media == SHARE_MEDIA.WEIXIN) {
            result = UMengPlatform.WX;
        } else if (share_media == SHARE_MEDIA.WEIXIN_CIRCLE) {
            result = UMengPlatform.WX_CIRCLE;
        } else if (share_media == SHARE_MEDIA.SINA) {
            result = UMengPlatform.WB;
        } else if (share_media == SHARE_MEDIA.ALIPAY) {
            result = UMengPlatform.ALI;
        }
        return result;
    }

    public static ThirdPartyUserInfo buildUserInfo(Map<String, String> result) {
        String uid = result.get("uid");
        String name = result.get("name");
        String gender = result.get("gender");
        String iconUrl = result.get("iconurl");
        return new ThirdPartyUserInfo(uid, name, gender, iconUrl);
    }
}
