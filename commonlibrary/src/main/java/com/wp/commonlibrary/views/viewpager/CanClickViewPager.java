package com.wp.commonlibrary.views.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 可点击的ViewPager
 * Created by WangPing on 2018/3/14.
 */

public class CanClickViewPager extends ViewPager {
    private int downX;
    private OnViewPagerClickListener listener;
    private int count;

    public CanClickViewPager(Context context) {
        this(context, null);
    }

    public CanClickViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //按下的时候开始的x的位置
                downX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:

            case MotionEvent.ACTION_UP:
                //up的时候x的位置
                int upX = (int) event.getX();
                int distance = upX - downX;
                if (distance == 0) {
                    if (listener != null) {
                        listener.onViewPagerClick(getCurrentItem() % count + 1);
                    }
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setOnPageClickListener(OnViewPagerClickListener listener) {
        this.listener = listener;
    }

    public void setPageCount(int count) {
        this.count = count;
    }

    public interface OnViewPagerClickListener {
        /**
         * ViewPager点击事件
         *
         * @param index 点击的页数
         */
        void onViewPagerClick(int index);
    }
}
