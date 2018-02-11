package com.wp.common;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.view.View;

import com.wp.common.dagger.DaggerActivityComponent;
import com.wp.commonlibrary.CommonApplication;
import com.wp.commonlibrary.baseMVP.BaseActivity;
import com.wp.commonlibrary.dialog.DialogOperateAdapter;
import com.wp.commonlibrary.image.DownloadImage;
import com.wp.commonlibrary.image.ImageHelper;
import com.wp.commonlibrary.image.preview.ImagesPreviewActivity;
import com.wp.commonlibrary.network.DefaultNetworkTypeCallBack;
import com.wp.commonlibrary.network.DownloadFile;
import com.wp.commonlibrary.network.ChangeViewWithProgressListener;
import com.wp.commonlibrary.network.NeedWifiOperate;
import com.wp.commonlibrary.permission.MustGrantPermissionCallBack;
import com.wp.commonlibrary.permission.NeedPermissionOperate;
import com.wp.commonlibrary.permission.Permission;
import com.wp.commonlibrary.permission.PermissionCallBack;
import com.wp.commonlibrary.permission.PermissionHelper;
import com.wp.commonlibrary.utils.LogUtils;
import com.wp.commonlibrary.views.ProgressImageView;
import com.wp.commonlibrary.views.TestTextView;
import com.wp.sharelogin.ShareHelper;

import java.io.File;

public class MainActivity extends BaseActivity<MainPresenter> implements PermissionCallBack, MainContract.View {
    private ProgressImageView ivExample;
    private TestTextView tvExample;

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void findView() {
        ivExample = (ProgressImageView) findView(R.id.iv_example);
        tvExample = (TestTextView) findView(R.id.tv_example);
    }

    @Override
    protected void inject() {
        DaggerActivityComponent.create().inject(this);
    }

    //请求接口
    public void requestApi(View view) {
        //mPresenter.requestMovieData(10, 2);
        ShareHelper.getDefault().share2WX(this, "hello");
    }

    @Override
    public void requestMovieSuccess(String result) {
        tvExample.setVisibility(View.VISIBLE);
        tvExample.setText(result);
        ivExample.setVisibility(View.GONE);
    }


    //下载文件
    public void downloadFile(View view) {
        NeedWifiOperate.getDefault().networkTypeShouldBeWifi(new DefaultNetworkTypeCallBack(this) {
            @Override
            public void wifi(int netType) {
                downloadFile();
            }
        }.setDialogOperateListener(new DialogOperateAdapter() {
            @Override
            public void positive(Context context, Dialog dialog) {
                downloadFile();
            }
        }));
    }

    private void downloadFile() {
        File file = new File(CommonApplication.context.getCacheDir(), System.currentTimeMillis() + ".apk");
        DownloadFile downloadFile = new DownloadFile("http://gdown.baidu.com/data/wisegame/13095bef5973a891/QQ_786.apk", file, true, new ChangeViewWithProgressListener(tvExample));
        mPresenter.downloadFile(downloadFile);
    }


    @Override
    public void downloadFileSuccess(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            data = FileProvider.getUriForFile(CommonApplication.context, "com.wp.common.fileprovider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            data = Uri.fromFile(file);
        }
        intent.setDataAndType(data, "application/vnd.android.package-archive");
        CommonApplication.context.startActivity(intent);
    }

    @Override
    public void downloadFileFail(Throwable exception) {
        LogUtils.e(exception.getMessage());
    }

    //加载网络图片
    public void loadNetImage(View view) {
        tvExample.setVisibility(View.GONE);
        ivExample.setVisibility(View.VISIBLE);
        String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999" +
                "_10000&sec=1516793144571&di=01beb0d58d63c328051647c96c7d3742" +
                "&imgtype=0&src=http%3A%2F%2Fi1.hdslb.com%2Fbfs%2Farchive%2F5" +
                "8619c927133fd015f1656ea505cef48c20089ba.jpg";
        ImageHelper.getDefault().loadImage(this, new DownloadImage.Builder()
                .path(url)
                .targetView(ivExample)
                .memoryCache(false)
                .diskCache(false)
                .build());
    }

    //加载本地图片
    public void loadLocalImage(View view) {
        //本地文件路径
        NeedPermissionOperate.getDefault().buildSafeExternalStoragePath(this, "scene_photo.jpg", new MustGrantPermissionCallBack(this) {
            @Override
            public void granted(Context context, String result) {
                ImageHelper.getDefault().loadImage(context, new DownloadImage.Builder().path(result).targetView(ivExample).build());
            }
        });
    }

    //请求权限
    public void requestPermission(View view) {
        PermissionHelper.getDefault().requestPermissions(this, new MustGrantPermissionCallBack(this) {
            @Override
            public void granted(Context context, String result) {

            }
        }, Permission.cameraPermission());
    }

    //图片预览
    public void imagesPreview(View view) {
        String[] images = new String[3];
        images[0] = "http://p9.pstatp.com/large/615c0002e579e79689d0";
        images[1] = "http://p3.pstatp.com/large/615e00012d933c0d545f";
        images[2] = "http://p1.pstatp.com/large/615c0002e578aee415e6";
        ImagesPreviewActivity.startImagesPreview(this, images, 0);
    }


    @Override
    public void grant(String... permissions) {
    }

    @Override
    public void denied(String... permissions) {
    }

    @Override
    public void deniedNotAskAgain(String... permissions) {

    }
}
