package com.wp.commonlibrary.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;

import com.wp.commonlibrary.text.TextWithStyle;

/**
 * SpannableString相关工具类
 * Created by WangPing on 2018/2/6.
 */

public final class SpannableStringUtils {
    public static SpannableString makeSpannableString(TextWithStyle... texts) {
        int currentStart = 0;
        int currentEnd = 0;
        SpannableString spannableString;
        StringBuilder builder = new StringBuilder();
        for (TextWithStyle text : texts) {
            builder.append(text.getContent());
        }
        spannableString = new SpannableString(builder.toString());
        for (TextWithStyle text : texts) {
            currentEnd += text.getContent().length();
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(text.getColor());
            spannableString.setSpan(colorSpan, currentStart, currentEnd, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            if (text.getTypeface() != -1) {
                spannableString.setSpan(new StyleSpan(text.getTypeface()), currentStart, currentEnd, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }
            if (text.hasDeleteLine()) {
                spannableString.setSpan(new StrikethroughSpan(), currentStart, currentEnd, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }
            if (text.hasUnderLine()) {
                spannableString.setSpan(new UnderlineSpan(), currentStart, currentEnd, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }
            currentStart += text.getContent().length();
        }
        return spannableString;
    }
}
