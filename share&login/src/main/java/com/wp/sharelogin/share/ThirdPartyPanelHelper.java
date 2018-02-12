package com.wp.sharelogin.share;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.wp.commonlibrary.dialog.DialogHelper;
import com.wp.sharelogin.R;
import com.wp.sharelogin.bean.ShareInfo;
import com.wp.sharelogin.callback.IThirtyPartyShareListener;

/**
 * 三方的面板
 * Created by WangPing on 2018/2/12.
 */

class ThirdPartyPanelHelper {
    private static ThirdPartyPanelHelper helper;

    private ThirdPartyPanelHelper() {
    }

    public static ThirdPartyPanelHelper getDefault() {
        if (helper == null) {
            synchronized (ThirdPartyPanelHelper.class) {
                if (helper == null) {
                    helper = new ThirdPartyPanelHelper();
                }
            }
        }
        return helper;
    }

    public void showDefaultSharePanel(Activity context, ShareInfo info, IThirtyPartyShareListener listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.share_panel_dialog, null);
        LinearLayout shareToWechat = (LinearLayout) view.findViewById(R.id.share_panel_share_to_wechat);
        LinearLayout shareToWechatCircle = (LinearLayout) view.findViewById(R.id.share_panel_share_to_wechat_circle);
        LinearLayout shareToQQ = (LinearLayout) view.findViewById(R.id.share_panel_share_to_qq);
        LinearLayout shareToWeibo = (LinearLayout) view.findViewById(R.id.share_panel_share_to_weibo);
        LinearLayout shareToAlipay = (LinearLayout) view.findViewById(R.id.share_panel_share_to_alipay);
        Dialog dialog = showSharePanel(context, view);
        shareToWechat.setOnClickListener(v -> {
            ThirdPartyShareHelper.getDefault().share2WX(context, info, listener);
            dialog.dismiss();
        });
        shareToWechatCircle.setOnClickListener(v -> {
            ThirdPartyShareHelper.getDefault().share2WXCircle(context, info, listener);
            dialog.dismiss();
        });
        shareToQQ.setOnClickListener(v -> {
            ThirdPartyShareHelper.getDefault().share2QQ(context, info, listener);
            dialog.dismiss();
        });
        shareToWeibo.setOnClickListener(v -> {
            ThirdPartyShareHelper.getDefault().share2WB(context, info, listener);
            dialog.dismiss();
        });
        shareToAlipay.setOnClickListener(v -> {
            ThirdPartyShareHelper.getDefault().share2Ali(context, info, listener);
            dialog.dismiss();
        });
    }

    private Dialog showSharePanel(Activity context, View contentView) {
        Dialog dialog = DialogHelper.getDefault().showBottomSheetDialog(context, contentView);
        dialog.setOnDismissListener(dialog1 -> {
            if (context instanceof SharePanelActivity) {
                context.finish();
            }
        });
        return dialog;
    }
}
