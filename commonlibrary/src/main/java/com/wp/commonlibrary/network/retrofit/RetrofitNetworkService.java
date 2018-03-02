package com.wp.commonlibrary.network.retrofit;

import com.wp.commonlibrary.basemvp.IView;
import com.wp.commonlibrary.network.callback.DefaultResponseCallBack;
import com.wp.commonlibrary.network.DownloadFile;
import com.wp.commonlibrary.network.callback.FileCallBack;
import com.wp.commonlibrary.network.INetWorkService;
import com.wp.commonlibrary.network.callback.IResponseCallBack;
import com.wp.commonlibrary.network.Params;
import com.wp.commonlibrary.network.progress.ProgressInterceptor;
import com.wp.commonlibrary.network.progress.ProgressManager;
import com.wp.commonlibrary.rx.BaseObserver;
import com.wp.commonlibrary.rx.DownloadObservableManager;
import com.wp.commonlibrary.rx.NetworkDefaultObserver;
import com.wp.commonlibrary.rx.ObservableManager;
import com.wp.commonlibrary.rx.ThreadTransformer;
import com.wp.commonlibrary.utils.FileIOUtils;
import com.wp.commonlibrary.utils.LogUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


/**
 * 使用Retrofit发请求
 * Created by WangPing on 2018/2/11.
 */

public class RetrofitNetworkService implements INetWorkService {
    private RetrofitRequestService requestService;
    private RetrofitDownloadService downloadService;
    private Retrofit retrofitClient;

    public RetrofitNetworkService(String baseUrl) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(new ProgressInterceptor())
                .retryOnConnectionFailure(true);

        retrofitClient = new Retrofit.Builder()
                .addConverterFactory(StringConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build())
                .baseUrl(baseUrl)
                .build();
        requestService = retrofitClient.create(RetrofitRequestService.class);
        downloadService = retrofitClient.create(RetrofitDownloadService.class);
    }

    @Override
    public <K> void get(IView view, String subUrl, Params params, IResponseCallBack<K> callBack) {
        Params realParams = handleParams(params);
        requestService.get(subUrl, realParams.getParams())
                .compose(ThreadTransformer.io2main())
                .subscribe(new NetworkDefaultObserver<>(view, callBack));
    }

    @Override
    public <K> void post(IView view, String subUrl, Params params, IResponseCallBack<K> callBack) {
        Params realParams = handleParams(params);
        requestService.post(subUrl, realParams.getParams())
                .compose(ThreadTransformer.io2main())
                .subscribe(new NetworkDefaultObserver<>(view, callBack));
    }

    /**
     * 处理入参
     *
     * @param params 入参
     * @return 处理后的参数
     */
    private Params handleParams(Params params) {
        if (params == null) {
            params = new Params();
        }
        params.param("", "");
        return params;
    }

    @Override
    public <K> void get(IView view, String subUrl, IResponseCallBack<K> callBack) {
        get(view, subUrl, null, callBack);
    }

    @Override
    public <K> void post(IView view, String subUrl, IResponseCallBack<K> callBack) {
        post(view, subUrl, null, callBack);
    }

    @Override
    public void changeBaseUrl(String baseUrl) {
        retrofitClient = retrofitClient.newBuilder()
                .baseUrl(baseUrl).build();
        requestService = retrofitClient.create(RetrofitRequestService.class);
    }

    @Override
    public void download(IView view, DownloadFile downloadFile, FileCallBack callBack) {
        //监听进度
        ProgressManager.addListener(downloadFile.getUrl(), downloadFile.getListener());
        Disposable disposable = downloadService.download("bytes=" + downloadFile.getStart() + "-", downloadFile.getUrl())
                .map(responseBody -> {
                    File file = downloadFile.getFile();
                    if (file == null) {
                        throw new IllegalArgumentException("下载文件不能为空");
                    }

                    if (FileIOUtils.writeFileFromIS(file, responseBody.byteStream(), true)) {
                        return file;
                    } else {
                        return null;
                    }
                })
                .compose(ThreadTransformer.io2main())
                .subscribe(file -> {
                    if (file == null) {
                        callBack.downloadFail(new Exception("文件IO有问题"));
                    } else {
                        callBack.downloadSuccess(file);
                    }
                }, Throwable::printStackTrace);
        DownloadObservableManager.getInstance().addObservable(downloadFile.getUrl(), disposable);
        ObservableManager.getInstance().addObservable(disposable);
    }
}
