package com.wp.commonlibrary.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.wp.commonlibrary.CommonApplication;

import java.io.File;

/**
 * 安装包相关的工具
 * Created by WangPing on 2018/2/27.
 */

public final class ApkUtils {
    /**
     * APK文件是否完整
     *
     * @param apkFile APK文件
     * @return 是否完整
     */
    public static boolean apkCanInstall(File apkFile) {
        boolean result = false;
        try {
            PackageManager pm = CommonApplication.context.getPackageManager();
            PackageInfo info = pm.getPackageArchiveInfo(apkFile.getAbsolutePath(), PackageManager.GET_ACTIVITIES);
            if (info != null) {
                //Apk完整
                result = true;
            }
        } catch (Exception e) {
            //Apk不完整
            result = false;
        }
        return result;
    }
}
