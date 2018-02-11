package com.wp.commonlibrary.network;

import java.util.HashMap;
import java.util.Map;

/**
 * 网络请求的参数
 * Created by WangPing on 2018/2/11.
 */

public class Params {
    private Map<String, Object> params;

    public Params(Map<String, Object> params) {
        this.params = params;
    }

    public Params() {
        params = new HashMap<>(4);
    }

    public void param(String key, Object value) {
        params.put(key, value);
    }

    public Map<String, Object> getParams() {
        return params;
    }
}
