package com.wp.commonlibrary.utils;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wp.commonlibrary.CommonApplication;
import com.wp.commonlibrary.R;


public final class ToastUtils {
    private static Toast customToast;
    private static Toast commonToast;

    public static void showCommonToast(String text) {
        if (commonToast == null) {
            commonToast = Toast.makeText(CommonApplication.context, text, Toast.LENGTH_LONG);
        } else {
            commonToast.setText(text);
        }
        commonToast.show();
    }

    public static void showToast(String text) {
        showToast(text, R.drawable.common_library_img_point);
    }

    public static void showToast(String text, int imgId) {
        if (customToast == null) {
            LayoutInflater inflater = LayoutInflater.from(CommonApplication.context);
            View view = inflater.inflate(R.layout.layout_toast_with_icon, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_toast_icon);
            imageView.setImageResource(imgId);
            TextView t = (TextView) view.findViewById(R.id.tv_toast_message);
            t.setText(text);
            customToast = new Toast(CommonApplication.context);
            customToast.setGravity(Gravity.CENTER, 0, 0);
            customToast.setDuration(Toast.LENGTH_SHORT);
            customToast.setView(view);
        } else {
            View view = customToast.getView();
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_toast_icon);
            imageView.setImageResource(imgId);
            TextView t = (TextView) view.findViewById(R.id.tv_toast_message);
            t.setText(text);
        }
        customToast.show();
    }
}
