package com.wp.commonlibrary.permission;

import android.app.Activity;

/**
 * 权限服务
 * Created by WangPing on 2018/2/5.
 */

public interface PermissionService {
    void requestPermissions(Activity activity, PermissionCallBack callBack, String... permissions);

    boolean isGranted(Activity activity, String permission);
}
