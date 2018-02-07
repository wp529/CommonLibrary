package com.wp.common;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.wp.commonlibrary.CommonApplication;
import com.wp.commonlibrary.baseMVP.BasePresenter;
import com.wp.commonlibrary.baseMVP.IView;
import com.wp.commonlibrary.network.DefaultResponseCallBack;
import com.wp.commonlibrary.network.DownloadFile;
import com.wp.commonlibrary.network.FileCallBack;
import com.wp.commonlibrary.network.ProgressListener;
import com.wp.commonlibrary.network.retrofit.DefaultObserver;
import com.wp.commonlibrary.network.retrofit.RetrofitDownloadHelper;
import com.wp.commonlibrary.network.retrofit.RetrofitHelper;
import com.wp.commonlibrary.network.retrofit.ThreadTransformer;
import com.wp.commonlibrary.utils.LogUtils;

import java.io.File;

import javax.inject.Inject;


/**
 * Created by WangPing on 2018/1/23.
 */
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {
    @Inject
    public MainPresenter() {
    }

    @Override
    public void requestMovieData(int start, int count) {
        RetrofitHelper.getDefault()
                .getService(TestApiService.class)
                .testApi(start, count)
                .compose(ThreadTransformer.io2main())
                .subscribe(new DefaultObserver<>(mView, new DefaultResponseCallBack<String>() {
                    @Override
                    public void success(String result) {
                        mView.requestMovieSuccess(result);
                    }

                    @Override
                    public void onStart(IView view) {
                        //设置加载框可否取消
                        view.showLoading(true);
                    }


                }));
    }

    @Override
    public void downloadFile(DownloadFile downloadFile) {
        RetrofitDownloadHelper helper = new RetrofitDownloadHelper();
        helper.download(downloadFile, new FileCallBack() {
            @Override
            public void downloadSuccess(File file) {
                mView.downloadFileSuccess(file);
            }

            @Override
            public void downloadFail(Throwable e) {
                mView.downloadFileFail(e);
            }
        });
    }
}
