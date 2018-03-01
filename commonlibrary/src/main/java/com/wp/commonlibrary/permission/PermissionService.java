package com.wp.commonlibrary.permission;

import android.app.Activity;

/**
 * 权限服务
 * Created by WangPing on 2018/2/5.
 */

public interface PermissionService {
    /**
     * 请求权限
     *
     * @param activity    Activity
     * @param callBack    请求结果回调
     * @param permissions 请求的权限
     */
    void requestPermissions(Activity activity, PermissionCallBack callBack, String... permissions);

    /**
     * 是否拥有某权限
     *
     * @param activity   Activity
     * @param permission 权限
     * @return 是否拥有
     */
    boolean isGranted(Activity activity, String permission);
}
