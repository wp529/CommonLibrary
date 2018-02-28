package com.wp.commonlibrary.network;

import com.wp.commonlibrary.utils.GsonUtils;

/**
 * Json转换实体类
 * Created by WangPing on 2018/2/28.
 */

public class JsonConvert implements INetworkResultConvert {
    @Override
    public Object convert(String result, Class<?> convertTo) {
        return GsonUtils.fromJson(result, convertTo);
    }
}
