package com.wp.commonlibrary.permission;

import android.app.Activity;

import com.wp.commonlibrary.permission.rxPermission.RxPermissionService;

/**
 * Created by WangPing on 2018/2/6.
 */

public class PermissionHelper {
    private static PermissionHelper helper;
    private static PermissionService service;

    private PermissionHelper() {
        service = new RxPermissionService();
    }

    public static PermissionHelper getDefault() {
        if (helper == null) {
            synchronized (PermissionHelper.class) {
                if (helper == null) {
                    helper = new PermissionHelper();
                }
            }
        }
        return helper;
    }

    public void requestPermissions(Activity activity, PermissionCallBack callBack, String... permission) {
        service.requestPermissions(activity, callBack, permission);
    }

    public boolean isGranted(Activity activity, String permission) {
        return service.isGranted(activity, permission);
    }
}
