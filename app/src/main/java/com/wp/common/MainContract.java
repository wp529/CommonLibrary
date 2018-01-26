package com.wp.common;


import com.wp.commonlibrary.baseMVP.IPresenter;
import com.wp.commonlibrary.baseMVP.IView;

/**
 * Created by WangPing on 2018/1/23.
 */

public interface MainContract {
    interface View extends IView {

    }

    interface Presenter extends IPresenter {
        void requestData();
    }
}
