package com.wp.commonlibrary.permission;

import android.app.Activity;
import android.os.Build;
import android.os.Environment;

import java.io.File;

/**
 * 需要使用危险权限操作的获取
 * Created by WangPing on 2018/2/7.
 */

public final class NeedPermissionOperate {
    private static NeedPermissionOperate operate;
    private String externalStorageRoot = Environment.getExternalStorageDirectory() + File.separator;

    private NeedPermissionOperate() {
    }

    public static NeedPermissionOperate getDefault() {
        if (operate == null) {
            synchronized (NeedPermissionOperate.class) {
                if (operate == null) {
                    operate = new NeedPermissionOperate();
                }
            }
        }
        return operate;
    }

    /**
     * 获取外部存储卡路径
     *
     * @param subPath 子路径
     */
    public void buildSafeExternalStoragePath(Activity activity, String subPath, CommonPermissionCallBack callBack) {
        StringBuilder builder = new StringBuilder(externalStorageRoot);
        builder.append(subPath);
        callBack.setResult(builder.toString());
        if (!hadPermission(activity, Permission.externalStoragePermission()) && needRequestPermission()) {
            PermissionHelper.getDefault().requestPermissions(activity, callBack, Permission.externalStoragePermission());
        } else {
            callBack.granted(activity, builder.toString());
        }
    }

    public void needExternalStoragePermission(Activity activity, CommonPermissionCallBack callBack) {
        if (!hadPermission(activity, Permission.externalStoragePermission()) && needRequestPermission()) {
            PermissionHelper.getDefault().requestPermissions(activity, callBack, Permission.externalStoragePermission());
        } else {
            callBack.granted(activity, "");
        }
    }

    private boolean needRequestPermission() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    private boolean hadPermission(Activity activity, String permission) {
        return PermissionHelper.getDefault().isGranted(activity, permission);
    }
}
