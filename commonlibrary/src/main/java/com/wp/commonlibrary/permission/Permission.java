package com.wp.commonlibrary.permission;

import android.Manifest;

import java.util.HashMap;

/**
 * Dangerous Permission的统一管理
 * Created by WangPing on 2018/2/6.
 */

public class Permission {
    public static HashMap<String, String> permissions = new HashMap<>();

    static {
        permissions.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, "外部存储权限");
        permissions.put(Manifest.permission.READ_CONTACTS, "联系人权限");
        permissions.put(Manifest.permission.CAMERA, "相机权限");
        permissions.put(Manifest.permission.READ_CALENDAR, "日历权限");
        permissions.put(Manifest.permission.ACCESS_FINE_LOCATION, "位置权限");
        permissions.put(Manifest.permission.RECORD_AUDIO, "麦克风权限");
        permissions.put(Manifest.permission.READ_PHONE_STATE, "手机信息权限");
        permissions.put(Manifest.permission.READ_SMS, "短信权限");
    }

    public static String getPermissionDescription(String permissin) {
        return permissions.get(permissin);
    }

    /**
     * 存储卡
     *
     * @return 存储卡权限
     */
    public static String externalStoragePermission() {
        return Manifest.permission.WRITE_EXTERNAL_STORAGE;
    }

    /**
     * 联系人
     *
     * @return 联系人权限
     */
    public static String contactPermission() {
        return Manifest.permission.READ_CONTACTS;
    }

    /**
     * 相机
     *
     * @return 相机
     */
    public static String cameraPermission() {
        return Manifest.permission.CAMERA;
    }

    /**
     * 日历
     *
     * @return 日历权限
     */
    public static String calendarPermission() {
        return Manifest.permission.READ_CALENDAR;
    }

    /**
     * 位置
     *
     * @return 位置权限
     */
    public static String locationPermission() {
        return Manifest.permission.ACCESS_FINE_LOCATION;
    }

    /**
     * 麦克风
     *
     * @return 麦克风权限
     */
    public static String micphonePermission() {
        return Manifest.permission.RECORD_AUDIO;
    }

    /**
     * 手机信息
     *
     * @return 手机信息权限
     */
    public static String phoneStatePermission() {
        return Manifest.permission.READ_PHONE_STATE;
    }

    /**
     * 短信
     *
     * @return 短信权限
     */
    public static String smsPermission() {
        return Manifest.permission.READ_SMS;
    }
}
