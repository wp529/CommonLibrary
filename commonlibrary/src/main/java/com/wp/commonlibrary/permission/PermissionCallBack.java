package com.wp.commonlibrary.permission;

/**
 * Created by WangPing on 2018/2/5.
 */

public interface PermissionCallBack {
    void grant(String... permissions);

    void denied(String... permissions);

    void deniedNotAskAgain(String... permissions);
}
