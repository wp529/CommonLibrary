package com.wp.common;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wp.commonlibrary.utils.LogUtils;
import com.wp.commonlibrary.views.viewpager.BannerViewPager;
import com.wp.commonlibrary.views.viewpager.CanClickViewPager;

/**
 * Created by WangPing on 2018/3/14.
 */

public class BannerDemoActivity extends Activity {
    private BannerViewPager vp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_demo);
        vp = (BannerViewPager) findViewById(R.id.banner);
        String[] images = new String[3];
        images[0] = "http://p9.pstatp.com/large/615c0002e579e79689d0";
        images[1] = "http://p3.pstatp.com/large/615e00012d933c0d545f";
        images[2] = "http://p1.pstatp.com/large/615c0002e578aee415e6";
        vp.setImageData(images, true);
        vp.setOnPageClickListener(index -> {
            LogUtils.e("WP", "++" + index);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vp.release();
    }
}
