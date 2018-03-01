package com.wp.commonlibrary.permission;

/**
 * 权限回调
 * Created by WangPing on 2018/2/5.
 */

public interface PermissionCallBack {
    /**
     * 权限允许
     *
     * @param permissions 允许的权限
     */
    void grant(String... permissions);

    /**
     * 权限拒绝
     *
     * @param permissions 拒绝的权限
     */
    void denied(String... permissions);

    /**
     * 权限拒绝不再询问
     *
     * @param permissions 不再询问的权限
     */
    void deniedNotAskAgain(String... permissions);
}
