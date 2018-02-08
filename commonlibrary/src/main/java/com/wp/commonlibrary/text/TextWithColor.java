package com.wp.commonlibrary.text;

import android.support.annotation.ColorInt;

/**
 * SpannableString需要使用
 * Created by WangPing on 2018/2/6.
 */

public class TextWithColor {
    private String content;
    private int color;

    public TextWithColor(String content, @ColorInt int color) {
        this.content = content;
        this.color = color;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return this.content;
    }
}
