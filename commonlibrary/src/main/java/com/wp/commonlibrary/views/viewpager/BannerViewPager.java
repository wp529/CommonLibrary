package com.wp.commonlibrary.views.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.wp.commonlibrary.R;

/**
 * 可无限轮询,触摸取消的ViewPager
 * Created by WangPing on 2018/3/14.
 */

public class BannerViewPager extends FrameLayout {
    private static final int DEFAULT_LOOP_TIME = 5 * 1000;
    private CanClickViewPager vp;
    private BannerAdapter adapter;


    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.common_library_layout_banner_view_pager, this);
        vp = (CanClickViewPager) findViewById(R.id.common_library_banner_container);
    }

    /**
     * 设置viewpager图片资源
     *
     * @param resource 网络图片资源url
     * @param loop     是否需要轮询
     */
    public void setImageData(String[] resource, boolean loop) {
        setImageData(resource, loop, 0);
    }

    public void setImageData(String[] resource, boolean loop, long loopTime) {
        vp.setPageCount(resource.length);
        if (loopTime == 0) {
            loopTime = DEFAULT_LOOP_TIME;
        }
        adapter = new BannerAdapter(vp, resource, loop, loopTime);
        vp.setAdapter(adapter);
    }

    public void release() {
        adapter.release();
    }

    public void setOnPageClickListener(CanClickViewPager.OnViewPagerClickListener listener) {
        vp.setOnPageClickListener(listener);
    }
}
