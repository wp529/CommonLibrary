package com.wp.commonlibrary.dialog;

import android.content.Context;

/**
 * Dialog使用入口
 * Created by WangPing on 2018/2/6.
 */

public class DialogHelper {
    private static DialogHelper helper;

    private DialogHelper() {
    }

    public static DialogHelper getDefault() {
        if (helper == null) {
            synchronized (DialogHelper.class) {
                if (helper == null) {
                    helper = new DialogHelper();
                }
            }
        }
        return helper;
    }

    /**
     * 显示一般的对话框
     *
     * @param context context一般传Activity
     * @param builder dialog的构造器
     */
    public void showDialogBox(Context context, BoxDialog.Builder builder) {
        BoxDialog dialog = builder.build();
        dialog.show(context);
    }

}
