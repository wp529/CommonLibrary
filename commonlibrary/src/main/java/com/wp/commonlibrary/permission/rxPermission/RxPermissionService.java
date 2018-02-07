package com.wp.commonlibrary.permission.rxPermission;

import android.app.Activity;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wp.commonlibrary.permission.PermissionCallBack;
import com.wp.commonlibrary.permission.PermissionService;
import com.wp.commonlibrary.utils.LogUtils;

/**
 * Created by WangPing on 2018/2/5.
 */

public class RxPermissionService implements PermissionService {

    @Override
    public void requestPermissions(Activity ac, PermissionCallBack callBack, String... permissions) {
        RxPermissions permission = new RxPermissions(ac);
        permission.requestEach(permissions)
                .subscribe(per -> {
                    if (per.granted) {
                        callBack.grant(per.name);
                    } else if (per.shouldShowRequestPermissionRationale) {
                        callBack.denied(per.name);
                    } else {
                        callBack.deniedNotAskAgain(per.name);
                    }
                });
    }

    @Override
    public boolean isGranted(Activity ac, String permission) {
        RxPermissions permissions = new RxPermissions(ac);
        return permissions.isGranted(permission);
    }
}
