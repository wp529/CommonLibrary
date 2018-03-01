package com.wp.commonlibrary.dialog;

import android.app.Dialog;
import android.content.Context;

/**
 * Dialog事件
 * Created by WangPing on 2018/2/6.
 */

public interface IDialogOperateListener {
    /**
     * 取消、退出等negative操作
     *
     * @param context 上下文
     * @param dialog  Dialog对象
     */
    void negative(Context context, Dialog dialog);

    /**
     * 确定等positive操作
     *
     * @param context 上下文
     * @param dialog  Dialog对象
     */
    void positive(Context context, Dialog dialog);

    /**
     * 取消操作，一般为触发返回
     *
     * @param context 上下文
     * @param dialog  Dialog对象
     */
    void cancel(Context context, Dialog dialog);
}
