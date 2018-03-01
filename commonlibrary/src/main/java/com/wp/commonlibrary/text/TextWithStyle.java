package com.wp.commonlibrary.text;

import android.graphics.Typeface;
import android.support.annotation.ColorInt;

/**
 * SpannableString需要使用
 * Created by WangPing on 2018/2/6.
 */

public class TextWithStyle {
    private String content;
    private int color;
    private int typeface = -1;
    private boolean hasUnderLine;
    private boolean hasDeleteLine;

    public TextWithStyle(String content, @ColorInt int color) {
        this.content = content;
        this.color = color;
    }

    public boolean hasDeleteLine() {
        return hasDeleteLine;
    }

    public void setHasDeleteLine(boolean hasDeleteLine) {
        this.hasDeleteLine = hasDeleteLine;
    }

    public boolean hasUnderLine() {
        return hasUnderLine;
    }

    public void setHasUnderLine(boolean hasUnderLine) {
        this.hasUnderLine = hasUnderLine;
    }

    public int getTypeface() {
        return typeface;
    }

    public void setTypeface(int typeface) {
        if (typeface != Typeface.NORMAL && typeface != Typeface.BOLD && typeface != Typeface.ITALIC && typeface != Typeface.BOLD_ITALIC) {
            this.typeface = Typeface.NORMAL;
        } else {
            this.typeface = typeface;
        }
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
