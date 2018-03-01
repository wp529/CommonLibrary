package com.wp.common;


import com.wp.commonlibrary.basemvp.IPresenter;
import com.wp.commonlibrary.basemvp.IView;
import com.wp.commonlibrary.network.DownloadFile;

import java.io.File;

/**
 * Created by WangPing on 2018/1/23.
 */

public interface MainContract {
    interface View extends IView {
        void requestMovieSuccess(MovieBean result);

        void downloadFileSuccess(File file);

        void downloadFileFail(Throwable exception);
    }

    interface Presenter extends IPresenter<View> {
        void requestMovieData(int start, int count);

        void downloadFile(DownloadFile downloadFile);
    }
}
