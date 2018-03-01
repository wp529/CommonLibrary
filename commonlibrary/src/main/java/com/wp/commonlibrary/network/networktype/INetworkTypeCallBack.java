package com.wp.commonlibrary.network.networktype;

/**
 * 网络类型回调
 * Created by WangPing on 2018/2/11.
 */

public interface INetworkTypeCallBack {
    int NETWORK_WIFI = 0x00000001;
    int NETWORK_4G = 0x00000010;
    int NETWORK_3G = 0x00000100;
    int NETWORK_2G = 0x00001000;
    int NETWORK_UNKNOWN = 0x00010000;
    int NETWORK_NO = 0x00100000;

    /**
     * 网络状态为WiFi
     * @param netType 网络状态
     */
    void wifi(int netType);

    /**
     * 网络状态为其他类型
     * @param netType 网络状态
     */
    void other(int netType);
}
