package com.wp.common;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.wp.commonlibrary.CommonApplication;
import com.wp.commonlibrary.baseMVP.BasePresenter;
import com.wp.commonlibrary.baseMVP.IView;
import com.wp.commonlibrary.network.DefaultProgressListener;
import com.wp.commonlibrary.network.DefaultResponseCallBack;
import com.wp.commonlibrary.network.DownloadFile;
import com.wp.commonlibrary.network.FileCallBack;
import com.wp.commonlibrary.network.IResponseCallBack;
import com.wp.commonlibrary.network.ProgressInterceptor;
import com.wp.commonlibrary.network.ProgressListener;
import com.wp.commonlibrary.network.ProgressManager;
import com.wp.commonlibrary.network.retrofit.DefaultObserver;
import com.wp.commonlibrary.network.retrofit.DownloadService;
import com.wp.commonlibrary.network.retrofit.RetrofitDownloadService;
import com.wp.commonlibrary.network.retrofit.RetrofitHelper;
import com.wp.commonlibrary.network.retrofit.ThreadTransformer;
import com.wp.commonlibrary.utils.FileIOUtils;
import com.wp.commonlibrary.utils.LogUtils;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * Created by WangPing on 2018/1/23.
 */
public class MainPresenter extends BasePresenter implements MainContract.Presenter {
    @Inject
    public MainPresenter() {
    }

    @Override
    public void requestData() {
        /*RetrofitHelper.getDefault()
                .getService(LoginService.class)
                .getCustomerOrg()
                .compose(ThreadTransformer.<String>io2main())
                .subscribe(new DefaultObserver<>(mView, new DefaultResponseCallBack<String>() {
                    @Override
                    public void onStart(IView view) {
                        view.showLoading(true);
                    }

                    @Override
                    public void success(String s) {

                    }
                }));*/
        /*ProgressManager.addListener("http://61.188.178.213/ZHFQWebService/version/zhfqu.apk", new DefaultProgressListener() {
            @Override
            public void onStart(long totalLength) {
                LogUtils.e("onStart " + totalLength);
            }

            @Override
            public void onProgress(int progress) {
                LogUtils.e("progress " + progress);
            }

            @Override
            public void onEnd(String url) {
                LogUtils.e("onEnd");
            }
        });
        RetrofitHelper.getDefault()
                .getService(DownloadService.class)
                .download("http://61.188.178.213/ZHFQWebService/version/zhfqu.apk")
                .map(new Function<ResponseBody, File>() {
                    @Override
                    public File apply(ResponseBody responseBody) throws Exception {
                        File file = new File(CommonApplication.context.getCacheDir(), System.currentTimeMillis() + ".apk");
                        boolean b = FileIOUtils.writeFileFromIS(file, responseBody.byteStream());
                        LogUtils.e("" + b);
                        return file;
                    }
                })
                .compose(ThreadTransformer.<File>io2main())
                .subscribe(new DefaultObserver<>(mView, new DefaultResponseCallBack<File>() {
                    @Override
                    public void onStart(IView view) {
                        view.showLoading(true);
                    }

                    @Override
                    public void success(File file) {

                    }
                }));*/
        String url = "http://61.188.178.200:8088/CreditLoanWeb/download/zhdy.apk";
        File file = new File(CommonApplication.context.getCacheDir(), System.currentTimeMillis() + ".apk");
        RetrofitDownloadService service = new RetrofitDownloadService();
        service.download(new DownloadFile(url, file, new ProgressListener() {
            @Override
            public void onStart(long totalLength) {
                LogUtils.e("onStart " + totalLength);
            }

            @Override
            public void onProgress(int progress) {
                LogUtils.e("onProgress " + progress);
            }

            @Override
            public void onEnd(String url) {
                LogUtils.e("onEnd " + url);
            }
        }), new FileCallBack() {
            @Override
            public void downloadSuccess(File file) {
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
                LogUtils.e("downloadSuccess " + file.getAbsolutePath());
            }

            @Override
            public void downloadFail(Throwable e) {
                LogUtils.e("downloadFail " + e.toString());
            }
        });
    }
}
