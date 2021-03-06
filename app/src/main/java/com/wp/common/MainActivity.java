package com.wp.common;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.view.View;

import com.wp.common.dagger.DaggerActivityComponent;
import com.wp.commonlibrary.CommonApplication;
import com.wp.commonlibrary.basemvp.BaseActivity;
import com.wp.commonlibrary.dialog.DialogOperateAdapter;
import com.wp.commonlibrary.image.DownloadImage;
import com.wp.commonlibrary.image.ImageHelper;
import com.wp.commonlibrary.image.preview.ImagesPreviewActivity;
import com.wp.commonlibrary.network.networktype.DefaultNetworkTypeCallBack;
import com.wp.commonlibrary.network.DownloadFile;
import com.wp.commonlibrary.network.progress.ChangeViewWithProgressListener;
import com.wp.commonlibrary.network.NeedWifiOperate;
import com.wp.commonlibrary.permission.MustGrantPermissionCallBack;
import com.wp.commonlibrary.permission.NeedPermissionOperate;
import com.wp.commonlibrary.permission.Permission;
import com.wp.commonlibrary.permission.PermissionHelper;
import com.wp.commonlibrary.utils.ApkUtils;
import com.wp.commonlibrary.utils.LogUtils;
import com.wp.commonlibrary.utils.MD5Utils;
import com.wp.commonlibrary.utils.ToastUtils;
import com.wp.commonlibrary.views.ProgressImageView;
import com.wp.commonlibrary.views.TestTextView;
import com.wp.sharelogin.bean.ShareInfo;
import com.wp.sharelogin.callback.IThirtyPartyShareListener;
import com.wp.sharelogin.share.SharePanelActivity;
import com.wp.web.WebActivity;

import java.io.File;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
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
        tvExample.setOnClickListener(v -> {
            LogUtils.e("被点击了");
            tvExample.cancelDownload();
        });
    }

    @Override
    protected void inject() {
        DaggerActivityComponent.create().inject(this);
    }

    //请求接口
    public void requestApi(View view) {
        mPresenter.requestMovieData(10, 2);
    }

    @Override
    public void requestMovieSuccess(MovieBean result) {
        tvExample.setVisibility(View.VISIBLE);
        tvExample.setText(result.toString());
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

    /**
     * 暂停下载后重新下载已做了文件的追加,但文件路径必须一致,所以通常可以将url的md5做文件名
     * 在重新下载时进度是未下载部分的百分比进度,所以你需要自行计算,计算方式为
     * 重新开始时进度 = 已下载 / (已下载 + 未下载(重新下载时onStart会回传)) * 100%
     * 重新下载时进度 = (已下载 + progress * 未下载) / (已下载 + 未下载(重新下载时onStart会回传)) * 100%
     * <p>
     * tips:    怎么判断是重新下载?    文件存在且file.length()大于0
     * <p>
     * 怎么获取已下载字节数?  file.length()
     */
    private void downloadFile() {
        String downloadUrl = "http://gdown.baidu.com/data/wisegame/13095bef5973a891/QQ_786.apk";
        File file = new File(CommonApplication.context.getCacheDir(), MD5Utils.MD5(downloadUrl) + ".apk");
        LogUtils.e("DownloadFile: " + file.length());
        DownloadFile downloadFile = new DownloadFile(downloadUrl, file.length(), file, new ChangeViewWithProgressListener(tvExample));
        mPresenter.downloadFile(downloadFile);
    }


    @Override
    public void downloadFileSuccess(File file) {
        if (ApkUtils.apkCanInstall(file)) {
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
        } else {
            ToastUtils.showToast("安装包不完整,请重新下载");
        }
    }

    @Override
    public void downloadFileFail(Throwable exception) {
        LogUtils.e(exception.getMessage());
    }

    //加载网络图片
    public void loadNetImage(View view) {
        tvExample.setVisibility(View.GONE);
        ivExample.setVisibility(View.VISIBLE);
        String url = "https://p3.pstatp.com/large/666c00065c746ccf3333";
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

    public void share(View view) {
        SharePanelActivity.startShare(this, new ShareInfo("hello"), new IThirtyPartyShareListener() {
            @Override
            public void onShareStart(String platform) {
                showLoading();
                LogUtils.e("onShareStart: " + platform);
            }

            @Override
            public void onShareEnd(String platform) {
                dismissLoading();
                LogUtils.e("onShareEnd: " + platform);
            }

            @Override
            public void onShareError(String platform, Throwable throwable) {
                dismissLoading();
                ToastUtils.showToast(throwable.getMessage());

            }

            @Override
            public void onShareCancel(String platform) {
                dismissLoading();
                LogUtils.e("onShareCancel: " + platform);
            }
        });
    }

    //图片预览
    public void imagesPreview(View view) {
        String[] images = new String[3];
        images[0] = "http://p9.pstatp.com/large/615c0002e579e79689d0";
        images[1] = "http://p3.pstatp.com/large/615e00012d933c0d545f";
        images[2] = "http://p1.pstatp.com/large/615c0002e578aee415e6";
        ImagesPreviewActivity.startImagesPreview(this, images, 0);
    }

    //加载网页
    public void web(View view) {
        WebActivity.startLoadHTML(this, "https://www.baidu.com/");
    }

    public void banner(View view) {
        startActivity(new Intent(this, BannerDemoActivity.class));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        LogUtils.e("onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }


}
