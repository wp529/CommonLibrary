package com.wp.commonlibrary.network;

/**
 * 网络请求结果转换实体类
 * Created by WangPing on 2018/2/28.
 */

public interface INetworkResultConvert {
    Object convert(String result, Class<? extends Object> convertTo);
}
