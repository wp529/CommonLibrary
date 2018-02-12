package com.wp.sharelogin.share;

import android.app.Activity;
import android.text.TextUtils;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.wp.sharelogin.bean.ShareInfo;
import com.wp.sharelogin.callback.IThirtyPartyShareListener;
import com.wp.sharelogin.utils.UMengUtils;

/**
 * 三方分享的入口
 * Created by WangPing on 2018/2/11.
 */

class ThirdPartyShareHelper {
    private static ThirdPartyShareHelper helper;

    private ThirdPartyShareHelper() {
    }

    public static ThirdPartyShareHelper getDefault() {
        if (helper == null) {
            synchronized (ThirdPartyShareHelper.class) {
                if (helper == null) {
                    helper = new ThirdPartyShareHelper();
                }
            }
        }
        return helper;
    }

    //分享文字
    public void share2QQ(Activity activity, ShareInfo info, IThirtyPartyShareListener listener) {
        share(activity, SHARE_MEDIA.QQ, info, listener);
    }

    public void share2WX(Activity activity, ShareInfo info, IThirtyPartyShareListener listener) {
        share(activity, SHARE_MEDIA.WEIXIN, info, listener);
    }

    public void share2WXCircle(Activity activity, ShareInfo info, IThirtyPartyShareListener listener) {
        share(activity, SHARE_MEDIA.WEIXIN_CIRCLE, info, listener);
    }

    public void share2WB(Activity activity, ShareInfo info, IThirtyPartyShareListener listener) {
        share(activity, SHARE_MEDIA.SINA, info, listener);
    }

    public void share2Ali(Activity activity, ShareInfo info, IThirtyPartyShareListener listener) {
        share(activity, SHARE_MEDIA.ALIPAY, info, listener);
    }

    private void share(Activity activity, SHARE_MEDIA platform, ShareInfo info, final IThirtyPartyShareListener listener) {
        ShareAction action = new ShareAction(activity);
        //分享平台
        action.setPlatform(platform);
        if (!TextUtils.isEmpty(info.getContent())) //分享文字
            action.withText(info.getContent());
        if (!TextUtils.isEmpty(info.getImageUrl())) //分享图片
            action.withMedia(buildShareImage(activity, info));
        if (!TextUtils.isEmpty(info.getUrl()))  //分享链接
            action.withMedia(buildShareWeb(activity, info));

        action.setCallback(new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                if (listener != null)
                    listener.onShareStart(UMengUtils.buildPlatform(share_media));
            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {
                if (listener != null)
                    listener.onShareEnd(UMengUtils.buildPlatform(share_media));
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                if (listener != null)
                    listener.onShareError(UMengUtils.buildPlatform(share_media), throwable);
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
                if (listener != null)
                    listener.onShareCancel(UMengUtils.buildPlatform(share_media));
            }
        }).share();
    }

    private UMImage buildShareImage(Activity activity, ShareInfo info) {
        UMImage image = new UMImage(activity, info.getImageUrl());
        UMImage thumb = new UMImage(activity, info.getImageThumb());
        image.setThumb(thumb);
        return image;
    }

    private UMWeb buildShareWeb(Activity activity, ShareInfo info) {
        UMImage thumbImage = new UMImage(activity, info.getThumb());
        UMWeb web = new UMWeb(info.getUrl());
        web.setTitle(info.getTitle());//标题
        web.setThumb(thumbImage);  //缩略图
        web.setDescription(info.getDes());
        return web;
    }
}
