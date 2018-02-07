package com.wp.commonlibrary.permission;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;

import com.wp.commonlibrary.ActivityManager;
import com.wp.commonlibrary.dialog.BoxDialog;
import com.wp.commonlibrary.dialog.DialogHelper;
import com.wp.commonlibrary.dialog.DialogOperateAdapter;
import com.wp.commonlibrary.text.TextWithColor;
import com.wp.commonlibrary.utils.SettingUtils;

/**
 * 必须允许权限才能使用
 * Created by WangPing on 2018/2/7.
 */

public abstract class MustGrantPermissionCallBack implements PermissionCallBack {
    private String result = "success";
    private Context context;

    public abstract void granted(Context context, String result);

    public MustGrantPermissionCallBack(Context context) {
        this.context = context;
    }

    @Override
    public void grant(String... permissions) {
        granted(context, result);
    }

    @Override
    public void denied(String... permissions) {
        denied(context, permissions[0]);
    }

    @Override
    public void deniedNotAskAgain(String... permissions) {
        denied(context, permissions[0]);
    }

    public void setResult(String result) {
        this.result = result;
    }

    private void denied(Context context, String permission) {
        DialogHelper.getDefault().showDialogBox(context, new BoxDialog.Builder()
                .cancelable(true)
                .cancelOutside(true)
                .singleButton(false)
                .title("提示")
                .content(new TextWithColor("如果您不允许 ", Color.BLACK)
                        , new TextWithColor(Permission.getPermissionDescription(permission), Color.RED)
                        , new TextWithColor(" 您将无法使用", Color.BLACK))
                .positiveText("去设置")
                .negativeText("退出")
                .listener(new DialogOperateAdapter() {
                    @Override
                    public void positive(Context context) {
                        SettingUtils.permissionSetting((Activity) context);
                    }

                    @Override
                    public void negative(Context context) {
                        ActivityManager.getAppManager().finishAllActivity();
                    }
                }));
    }
}
