package com.wp.commonlibrary.image.preview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.chrisbanes.photoview.PhotoView;
import com.wp.commonlibrary.R;
import com.wp.commonlibrary.image.DownloadImage;
import com.wp.commonlibrary.image.ImageHelper;
import com.wp.commonlibrary.views.ProgressPhotoView;

/**
 * Created by WangPing on 2018/2/7.
 */

public class ImagesPreviewPagerAdapter extends PagerAdapter {
    private String[] images;
    private Context context;
    private View currentView;

    public ImagesPreviewPagerAdapter(Context context, String[] images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.common_library_layout_images_preview_item, null);
        ProgressPhotoView image = (ProgressPhotoView) view.findViewById(R.id.common_library_images_preview_image);
        ImageHelper.getDefault().loadImage(context, new DownloadImage.Builder()
                .path(images[position])
                .targetView(image)
                .memoryCache(false)
                .diskCache(false)
                .build());
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        currentView = (View) object;
    }

    public View getCurrentView() {
        return currentView;
    }
}