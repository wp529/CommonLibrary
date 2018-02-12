package com.wp.commonlibrary.image.preview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wp.commonlibrary.ActivityManager;
import com.wp.commonlibrary.CommonApplication;
import com.wp.commonlibrary.R;
import com.wp.commonlibrary.image.ImageHelper;
import com.wp.commonlibrary.network.FileCallBack;
import com.wp.commonlibrary.permission.AllowDeniedPermissionCallBack;
import com.wp.commonlibrary.permission.NeedPermissionOperate;
import com.wp.commonlibrary.utils.FileIOUtils;
import com.wp.commonlibrary.utils.LogUtils;
import com.wp.commonlibrary.utils.ToastUtils;

import java.io.File;

/**
 * 图片预览界面
 * Created by WangPing on 2018/2/7.
 */

public class ImagesPreviewActivity extends Activity {
    private static final String TAG = ImagesPreviewActivity.class.getName();
    private static final String PREVIEW_IMAGES = "common_library_preview_images";
    private static final String CURRENT_INDEX = "common_library_preview_current_index";
    private ImagesPreviewViewPager container;
    private TextView indicate, save;
    private RelativeLayout bottomLayout;
    private View background;
    private String[] images;
    private int currentPage;
    private ImagesPreviewPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_library_activity_images_preview);
        ActivityManager.getAppManager().addActivity(this);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        //标识跟随变化 1/17
        container.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                String changeIndicate = (position + 1) + "/" + images.length;
                indicate.setText(changeIndicate);
                container.setTag(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        container.setPositionListener(new ImagesPreviewViewPager.OnPositionChangeListener() {

            @Override
            public void onPositionChange(int initTop, int nowTop, float ratio) {
                float alpha = 1 - Math.min(1, ratio * 5);
                bottomLayout.setAlpha(alpha);
                background.setAlpha(Math.max(0, 1 - ratio));
            }

            @Override
            public void onFlingOutFinish() {
                finish();
            }
        });
        adapter.setOnPageClickListener(new ImagesPreviewPagerAdapter.OnPageClickListener() {
            @Override
            public void onPageClick(int position) {
                finish();
            }

            @Override
            public void onPageLongClick(int position) {
                LogUtils.e("onPageLongClick " + position);
            }
        });
        save.setOnClickListener(v -> ImageHelper.getDefault().getCacheImage(images[currentPage], new FileCallBack() {
            @Override
            public void downloadSuccess(File file) {
                String correctImageName = buildCorrectImageName(file.getName());
                NeedPermissionOperate.getDefault().buildSafeExternalStoragePath(ImagesPreviewActivity.this, CommonApplication.context.getPackageName() + File.separator + correctImageName, new AllowDeniedPermissionCallBack(ImagesPreviewActivity.this) {
                    @Override
                    public void granted(Context context, String result) {
                        if (FileIOUtils.copyFile(file, result)) {
                            ToastUtils.showToast("图片已成功保存到" + CommonApplication.context.getPackageName() + "文件夹下");
                        } else {
                            ToastUtils.showToast("图片保存失败");
                        }
                    }
                });
            }

            private String buildCorrectImageName(String name) {
                LogUtils.v(TAG, "缓存的文件名: " + name);
                String result;
                if (name.endsWith(".jpg") || name.endsWith(".png")) {
                    result = name;
                } else {
                    if (name.contains(".")) {
                        int i = name.lastIndexOf(".");
                        result = name.substring(0, i) + ".jpg";
                    } else {
                        result = name + ".jpg";
                    }
                }
                LogUtils.v(TAG, "保存至外部存储的文件名: " + result);
                return result;
            }

            @Override
            public void downloadFail(Throwable e) {

            }
        }));
    }

    private void initView() {
        background = findViewById(R.id.common_library_images_preview_background_view);
        container = (ImagesPreviewViewPager) findViewById(R.id.common_library_images_preview_container);
        indicate = (TextView) findViewById(R.id.common_library_images_preview_indicate);
        bottomLayout = (RelativeLayout) findViewById(R.id.common_library_images_preview_bottom_layout);
        save = (TextView) findViewById(R.id.common_library_images_preview_save);
    }

    private void initData() {
        String[] extra = getIntent().getStringArrayExtra(PREVIEW_IMAGES);
        currentPage = getIntent().getIntExtra(CURRENT_INDEX, 0);
        if (extra != null && extra.length != 0) {
            images = extra;
        }
        adapter = new ImagesPreviewPagerAdapter(this, images);
        container.setAdapter(adapter);
        container.setCurrentItem(currentPage, false);
        String firstIndicate = (currentPage + 1) + "/" + images.length;
        indicate.setText(firstIndicate);
        container.setTag(0);
    }

    public static void startImagesPreview(Context context, String[] images, int currentIndex) {
        Intent intent = new Intent();
        intent.setClass(context, ImagesPreviewActivity.class);
        intent.putExtra(PREVIEW_IMAGES, images);
        intent.putExtra(CURRENT_INDEX, currentIndex);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getAppManager().removeActivity(this);
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.common_library_activity_alpha_exit, R.anim.common_library_activity_alpha_exit);
    }
}
