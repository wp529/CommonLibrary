package com.wp.common;


import com.wp.commonlibrary.basemvp.BasePresenter;
import com.wp.commonlibrary.basemvp.IView;
import com.wp.commonlibrary.network.DefaultResponseCallBack;
import com.wp.commonlibrary.network.DownloadFile;
import com.wp.commonlibrary.network.FileCallBack;
import com.wp.commonlibrary.network.NetworkHelper;
import com.wp.commonlibrary.network.Params;
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
        Params params = new Params();
        params.param("start", start);
        params.param("count", count);
        NetworkHelper.getDefault().get(mView, "v2/movie/top250", params, new DefaultResponseCallBack<MovieBean>() {
            @Override
            public void success(MovieBean result) {
                mView.requestMovieSuccess(result);
            }

            @Override
            public void onStart(IView view) {
                //设置加载框可否取消
                view.showLoading(true);
            }
        });
    }

    @Override
    public void downloadFile(DownloadFile downloadFile) {
        NetworkHelper.getDefault().download(mView, downloadFile, new FileCallBack() {
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
