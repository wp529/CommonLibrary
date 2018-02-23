package com.wp.commonlibrary.permission;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;

import com.wp.commonlibrary.dialog.BoxDialog;
import com.wp.commonlibrary.dialog.DialogHelper;
import com.wp.commonlibrary.dialog.DialogOperateAdapter;
import com.wp.commonlibrary.text.TextWithStyle;
import com.wp.commonlibrary.utils.SettingUtils;

/**
 * 可以拒绝权限
 * Created by WangPing on 2018/2/8.
 */

public abstract class AllowDeniedPermissionCallBack extends CommonPermissionCallBack implements PermissionCallBack {

    public AllowDeniedPermissionCallBack(Context context) {
        super(context);
    }

    public abstract void granted(Context context, String result);

    @Override
    public void denied(Context context, String permission) {
        DialogHelper.getDefault().showDialogBox(context, new BoxDialog.Builder()
                .cancelable(true)
                .cancelOutside(true)
                .singleButton(false)
                .title("提示")
                .content(new TextWithStyle("如果您不允许 ", Color.BLACK)
                        , new TextWithStyle(Permission.getPermissionDescription(permission), Color.RED)
                        , new TextWithStyle(" 您将无法正常使用", Color.BLACK))
                .positiveText("去设置")
                .negativeText("取消")
                .listener(new DialogOperateAdapter() {
                    @Override
                    public void positive(Context context, Dialog dialog) {
                        SettingUtils.permissionSetting((Activity) context);
                    }

                    @Override
                    public void negative(Context context, Dialog dialog) {
                        dialog.dismiss();
                    }
                }));
    }
}
