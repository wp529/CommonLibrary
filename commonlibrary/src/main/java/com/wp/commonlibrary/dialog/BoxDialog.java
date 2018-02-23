package com.wp.commonlibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.wp.commonlibrary.R;
import com.wp.commonlibrary.text.TextWithStyle;
import com.wp.commonlibrary.utils.SpannableStringUtils;

/**
 * 对话框
 * Created by WangPing on 2018/2/6.
 */

public class BoxDialog {
    private String title;
    private String content;
    private TextWithStyle[] contents;
    private String negativeText;
    private String positiveText;
    private int titleColor;
    private int contentColor;
    private int negativeColor;
    private int positiveColor;
    private boolean isCancelable;
    private boolean isCancelableTouchOutside;
    private boolean isSingleButton;
    private IDialogOperateListener listener;
    private Dialog dialog;

    private BoxDialog(Builder builder) {
        this.title = builder.title;
        this.content = builder.content;
        this.contents = builder.contents;
        this.negativeText = builder.negativeText;
        this.positiveText = builder.positiveText;
        this.isCancelable = builder.isCancelable;
        this.isCancelableTouchOutside = builder.isCancelableTouchOutside;
        this.isSingleButton = builder.isSingleButton;
        this.titleColor = builder.titleColor;
        this.contentColor = builder.contentColor;
        this.negativeColor = builder.negativeColor;
        this.positiveColor = builder.positiveColor;
        this.listener = builder.listener;
    }

    public void show(Context context) {
        if (dialog != null && dialog.isShowing())
            return;
        if (dialog != null) {
            dialog.show();
            return;
        }

        View dialogView;
        if (isSingleButton) {
            dialogView = LayoutInflater.from(context).inflate(R.layout.common_library_dialog_box_single_button, null);
        } else {
            dialogView = LayoutInflater.from(context).inflate(R.layout.common_library_dialog_box_double_button, null);
        }
        dialog = new Dialog(context);
        dialog.setContentView(dialogView);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = dialogView.getResources().getDisplayMetrics().widthPixels * 3 / 4;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.onWindowAttributesChanged(layoutParams);

        TextView title = (TextView) dialogView.findViewById(R.id.common_library_dialog_box_title);
        TextView content = (TextView) dialogView.findViewById(R.id.common_library_dialog_box_content);
        TextView positive = (TextView) dialogView.findViewById(R.id.common_library_dialog_box_positive);
        TextView negative = (TextView) dialogView.findViewById(R.id.common_library_dialog_box_negative);
        //设置文字
        if (TextUtils.isEmpty(this.title)) {
            title.setVisibility(View.GONE);
        } else {
            title.setVisibility(View.VISIBLE);
            title.setText(this.title);
        }
        if (TextUtils.isEmpty(this.content) && (this.contents == null || this.contents.length == 0)) {
            //没有设置字符串或者TextWithColor
            content.setVisibility(View.GONE);
        } else {
            content.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(this.content)) { //设置了字符串
                content.setText(this.content);
            } else { //设置了TextWithColor
                content.setText(SpannableStringUtils.makeSpannableString(this.contents));
            }
        }

        positive.setText(this.positiveText);
        if (!isSingleButton)
            negative.setText(this.negativeText);
        //设置颜色
        if (this.titleColor != 0)
            title.setTextColor(this.titleColor);
        if (this.contentColor != 0)
            content.setTextColor(this.contentColor);
        if (this.positiveColor != 0)
            positive.setTextColor(this.positiveColor);
        if (this.negativeColor != 0 && !isSingleButton)
            negative.setTextColor(this.negativeColor);
        //设置返回
        dialog.setCancelable(this.isCancelable);
        dialog.setCanceledOnTouchOutside(this.isCancelableTouchOutside);
        //设置监听
        positive.setOnClickListener(v -> {
            dialog.dismiss();
            if (this.listener != null)
                listener.positive(context, dialog);
        });
        if (!isSingleButton) {
            negative.setOnClickListener(v -> {
                dialog.dismiss();
                if (this.listener != null) {
                    listener.negative(context, dialog);
                }
            });
        }
        dialog.setOnCancelListener(dialogInterface -> {
            if (this.listener != null) {
                listener.cancel(context, dialog);
            }
        });
        dialog.show();
    }

    public static class Builder {
        private String title;
        private String content;
        private TextWithStyle[] contents;
        private String negativeText = "取消";
        private String positiveText = "确定";
        private int titleColor;
        private int contentColor;
        private int negativeColor;
        private int positiveColor;
        private boolean isCancelable = true;
        private boolean isCancelableTouchOutside;
        private boolean isSingleButton = false;
        private IDialogOperateListener listener;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder content(TextWithStyle... contents) {
            this.contents = contents;
            return this;
        }

        public Builder negativeText(String negativeText) {
            this.negativeText = negativeText;
            return this;
        }

        public Builder positiveText(String positiveText) {
            this.positiveText = positiveText;
            return this;
        }

        public Builder cancelable(boolean isCancelable) {
            this.isCancelable = isCancelable;
            return this;
        }

        public Builder cancelOutside(boolean can) {
            this.isCancelableTouchOutside = can;
            return this;
        }

        public Builder singleButton(boolean isSingleButton) {
            this.isSingleButton = isSingleButton;
            return this;
        }

        public Builder positiveColor(int positiveColor) {
            this.positiveColor = positiveColor;
            return this;
        }

        public Builder titleColor(int titleColor) {
            this.titleColor = titleColor;
            return this;
        }

        public Builder contentColor(int contentColor) {
            this.contentColor = contentColor;
            return this;
        }

        public Builder negativeColor(int negativeColor) {
            this.negativeColor = negativeColor;
            return this;
        }

        public Builder listener(IDialogOperateListener listener) {
            this.listener = listener;
            return this;
        }

        public BoxDialog build() {
            return new BoxDialog(this);
        }
    }
}
