package com.wp.commonlibrary.permission;

import android.content.Context;

/**
 * 通用权限申请
 * Created by WangPing on 2018/2/8.
 */

public abstract class CommonPermissionCallBack implements PermissionCallBack {
    protected String result = "success";
    protected Context context;

    /**
     * 权限允许
     * @param context 上下文
     * @param result 请求结果 一般用于存储权限允许后构造的路径
     */
    public abstract void granted(Context context, String result);

    /**
     * 权限拒绝
     * @param context 上下文
     * @param permission 被拒绝的权限
     */
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
