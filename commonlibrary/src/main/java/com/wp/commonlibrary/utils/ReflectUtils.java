package com.wp.commonlibrary.utils;

import java.lang.reflect.Field;

/**
 * Created by WangPing on 2018/1/24.
 */

public final class ReflectUtils {
    /**
     * 获取私有属性对象
     *
     * @param targetObject
     * @param name
     * @return
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
