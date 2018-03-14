package com.wp.commonlibrary.views.viewpager;

import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wp.commonlibrary.image.DownloadImage;
import com.wp.commonlibrary.image.ImageHelper;

/**
 * Created by WangPing on 2018/3/14.
 */

class BannerAdapter extends PagerAdapter {
    private String[] images;
    private int count;
    private Handler handler = new Handler();
    private Runnable run;
    private long time;

    public BannerAdapter(ViewPager vp, String[] images, boolean looper, long time) {
        this.images = images;
        this.time = time;
        this.count = images.length;
        //设置viewpager的各项属性
        if (count > 1 && looper) {
            setViewPager(vp);
        }
    }

    @Override
    public int getCount() {
        if (count == 1) {
            return 1;
        } else {
            return Integer.MAX_VALUE;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(container.getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageHelper.getDefault().loadImage(container.getContext(), new DownloadImage.Builder()
                .path(images[position % count])
                .targetView(imageView)
                .build());
        container.addView(imageView, 0); // 将图片增加到ViewPager
        return imageView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 为viewPager设置好轮询和touch时不轮询
     *
     * @param viewPager 需要设置轮询的viewpager
     */
    private void setViewPager(final ViewPager viewPager) {
        viewPager.setCurrentItem(50000 - 50000 % images.length);
        run = new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPager.getCurrentItem();
                currentItem++;
                viewPager.setCurrentItem(currentItem);
                handler.postDelayed(this, time);
            }
        };
        //设置轮询
        handler.postDelayed(run, time);
        //设置顶端图片的触摸监听 触摸时不轮询
        viewPager.setOnTouchListener(new TopViewPagerTouchListener());
    }

    public void release() {
        handler.removeCallbacksAndMessages(null);
    }

    /**
     * 顶部轮询viewpager的触摸监听
     */
    class TopViewPagerTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //删除Handler中的所有消息
                    handler.removeCallbacksAndMessages(null);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    handler.postDelayed(run, time);
                    break;
                case MotionEvent.ACTION_UP:
                    handler.postDelayed(run, time);
                    break;
                default:
                    break;
            }
            return false;
        }
    }
}