package com.wp.commonlibrary.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

/**
 * 与设置相关的跳转
 * Created by WangPing on 2018/2/5.
 */

public final class SettingUtils {
    public static final int SETTING_PERMISSION = 0X00000001;

    public static void permissionSetting(Activity activity) {
        String brand = Build.BRAND;
        try {
            if (TextUtils.equals(brand.toLowerCase(), "redmi") || TextUtils.equals(brand.toLowerCase(), "xiaomi")) {
                miuiPermission(activity);
            } else if (TextUtils.equals(brand.toLowerCase(), "meizu")) {
                meizuPermission(activity);
            } else if (TextUtils.equals(brand.toLowerCase(), "huawei") || TextUtils.equals(brand.toLowerCase(), "honor")) {
                huaweiPermission(activity);
            } else {
                commonPermission(activity);
            }
        } catch (Exception e) {
            ToastUtils.showCommonToast("未找到" + brand + "设置页,请兼容");
            e.printStackTrace();
        }
    }

    private static void miuiPermission(Activity activity) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
        intent.putExtra("extra_pkgname", activity.getPackageName());
        activity.startActivity(intent);
    }

    private static void meizuPermission(Activity activity) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", activity.getPackageName());
        activity.startActivityForResult(intent, SETTING_PERMISSION);
    }

    private static void huaweiPermission(Activity activity) {
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
        intent.setComponent(comp);
        activity.startActivityForResult(intent, SETTING_PERMISSION);
    }

    private static void commonPermission(Activity activity) {
        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        activity.startActivityForResult(intent, SETTING_PERMISSION);
    }
}
