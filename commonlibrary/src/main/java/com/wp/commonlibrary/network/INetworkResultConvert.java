package com.wp.commonlibrary.network;

/**
 * 网络请求结果转换实体类
 * Created by WangPing on 2018/2/28.
 */

public interface INetworkResultConvert {
    /**
     * 请求结果转换
     *
     * @param result    请求结果
     * @param convertTo 需转换成的实体类
     * @return 转换结果
     */
    Object convert(String result, Class<?> convertTo);
}
