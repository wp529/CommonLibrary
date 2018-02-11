package com.wp.commonlibrary.network;

/**
 * Created by WangPing on 2018/2/11.
 */

public interface INetworkTypeCallBack {
    int NETWORK_WIFI = 0x00000001;
    int NETWORK_4G = 0x00000010;
    int NETWORK_3G = 0x00000100;
    int NETWORK_2G = 0x00001000;
    int NETWORK_UNKNOWN = 0x00010000;
    int NETWORK_NO = 0x00100000;

    void wifi(int netType);

    void other(int netType);
}
