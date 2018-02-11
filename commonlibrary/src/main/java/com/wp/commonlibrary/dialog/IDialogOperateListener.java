package com.wp.commonlibrary.dialog;

import android.app.Dialog;
import android.content.Context;

/**
 * Dialog事件
 * Created by WangPing on 2018/2/6.
 */

public interface IDialogOperateListener {
    void negative(Context context, Dialog dialog);

    void positive(Context context, Dialog dialog);

    void cancel(Context context, Dialog dialog);
}
