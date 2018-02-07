package com.wp.commonlibrary.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.wp.commonlibrary.text.TextWithColor;

/**
 * Created by WangPing on 2018/2/6.
 */

public final class SpannableStringUtils {
    public static SpannableString makeSpannableString(TextWithColor... texts) {
        int currentStart = 0;
        int currentEnd = 0;
        SpannableString spannableString;
        StringBuilder builder = new StringBuilder();
        for (TextWithColor text : texts) {
            builder.append(text.getContent());
        }
        spannableString = new SpannableString(builder.toString());
        for (TextWithColor text : texts) {
            currentEnd += text.getContent().length();
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(text.getColor());
            spannableString.setSpan(colorSpan, currentStart, currentEnd, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            currentStart += text.getContent().length();
        }
        return spannableString;
    }
}
