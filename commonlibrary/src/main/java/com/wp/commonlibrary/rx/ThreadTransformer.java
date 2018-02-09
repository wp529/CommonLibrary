package com.wp.commonlibrary.rx;


import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Rx线程切换
 * Created by WangPing on 2018/1/17.
 */

public class ThreadTransformer {

    public static <T> ObservableTransformer<T, T> io2main() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> ObservableTransformer<T, T> io2io() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

}
