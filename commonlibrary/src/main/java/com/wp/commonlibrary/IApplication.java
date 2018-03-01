package com.wp.commonlibrary;

/**
 * 用于各组件需要在Application的初始化
 * Created by WangPing on 2018/2/11.
 */

public interface IApplication {
    /**
     * onCreate
     *
     * @param application application
     */
    void onCreate(CommonApplication application);
}
