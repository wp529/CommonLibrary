package com.wp.commonlibrary.permission;

import android.content.Context;

/**
 * 通用权限申请
 * Created by WangPing on 2018/2/8.
 */

public abstract class CommonPermissionCallBack implements PermissionCallBack {
    protected String result = "success";
    protected Context context;

    public abstract void granted(Context context, String result);

    public abstract void denied(Context context, String permission);


    public CommonPermissionCallBack(Context context) {
        this.context = context;
    }

    public void setResult(String result) {
        this.result = result;
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

}
