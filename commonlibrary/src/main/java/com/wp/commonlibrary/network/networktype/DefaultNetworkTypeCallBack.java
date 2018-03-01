package com.wp.commonlibrary.network.networktype;


import android.content.Context;
import com.wp.commonlibrary.dialog.BoxDialog;
import com.wp.commonlibrary.dialog.DialogHelper;
import com.wp.commonlibrary.dialog.IDialogOperateListener;

/**
 * 不是WiFi状态弹窗
 * Created by WangPing on 2018/2/11.
 */

public abstract class DefaultNetworkTypeCallBack implements INetworkTypeCallBack {
    private IDialogOperateListener listener;
    private Context context;

    public DefaultNetworkTypeCallBack(Context context) {
        this.context = context;
    }

    /**
     * 网络状态为WiFi
     * @param netType 网络状态
     */
    @Override
    public abstract void wifi(int netType);

    @Override
    public void other(int netType) {
        DialogHelper.getDefault()
                .showDialogBox(context
                        , new BoxDialog.Builder()
                                .title("温馨提示")
                                .content("您正在使用手机流量")
                                .negativeText("取消")
                                .positiveText("继续")
                                .listener(listener));
    }

    public DefaultNetworkTypeCallBack setDialogOperateListener(IDialogOperateListener listener) {
        this.listener = listener;
        return this;
    }
}
