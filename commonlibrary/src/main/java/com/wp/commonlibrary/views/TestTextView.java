package com.wp.commonlibrary.views;

import android.content.Context;
import android.util.AttributeSet;
import com.wp.commonlibrary.utils.LogUtils;

/**
 * Created by WangPing on 2018/2/9.
 */

public class TestTextView extends android.support.v7.widget.AppCompatTextView implements IViewProgressEvent {
    public TestTextView(Context context) {
        super(context);
    }

    public TestTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void start(long totalLength) {

    }

    @Override
    public void updateProgress(int progress) {
        LogUtils.e("updateProgress " + progress);
        setText("" + progress);
    }

    @Override
    public void end() {

    }

    @Override
    public void cancel() {
        setText("");
        LogUtils.e("cancel");
    }
}
