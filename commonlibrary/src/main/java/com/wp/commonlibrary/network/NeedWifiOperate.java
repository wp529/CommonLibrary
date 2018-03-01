package com.wp.commonlibrary.network;

import com.wp.commonlibrary.network.networktype.INetworkTypeCallBack;
import com.wp.commonlibrary.utils.NetworkUtils;

/**
 * 提升用户体验之最好是WiFi状态才能进行的操作
 * Created by WangPing on 2018/2/11.
 */

public class NeedWifiOperate {
    private static NeedWifiOperate operate;

    private NeedWifiOperate() {
    }

    public static NeedWifiOperate getDefault() {
        if (operate == null) {
            synchronized (NeedWifiOperate.class) {
                if (operate == null) {
                    operate = new NeedWifiOperate();
                }
            }
        }
        return operate;
    }

    public void networkTypeShouldBeWifi(INetworkTypeCallBack type) {
        if (NetworkUtils.isConnected()) {
            int netType = NetworkUtils.getNetworkType();
            if (netType == INetworkTypeCallBack.NETWORK_WIFI) {
                type.wifi(netType);
            } else {
                type.other(netType);
            }
        } else {
            type.disConnect();
        }
    }
}
