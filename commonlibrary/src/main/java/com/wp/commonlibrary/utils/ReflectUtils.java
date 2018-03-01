package com.wp.commonlibrary.utils;

import java.lang.reflect.Field;

/**
 * 反射相关工具类
 * Created by WangPing on 2018/1/24.
 */

public final class ReflectUtils {
    /**
     * 获取私有属性对象
     *
     * @param targetObject 被获取的对象
     * @param name         被获取的属性名
     * @return 获取的对象
     */
    public static Object getDeclaredObject(Object targetObject, String name) {
        try {
            Field field = targetObject.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return field.get(targetObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
