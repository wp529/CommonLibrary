package com.wp.commonlibrary.network.retrofit;

import com.wp.commonlibrary.baseMVP.IView;
import com.wp.commonlibrary.network.DefaultResponseCallBack;
import com.wp.commonlibrary.network.DownloadFile;
import com.wp.commonlibrary.network.FileCallBack;
import com.wp.commonlibrary.network.INetWorkService;
import com.wp.commonlibrary.network.IResponseCallBack;
import com.wp.commonlibrary.network.Params;
import com.wp.commonlibrary.network.ProgressInterceptor;
import com.wp.commonlibrary.network.ProgressManager;
import com.wp.commonlibrary.rx.NetworkDefaultObserver;
import com.wp.commonlibrary.rx.ThreadTransformer;
import com.wp.commonlibrary.utils.FileIOUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

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

    //处理请求入参
    private Params handleParams(Params params) {
        if (params == null)
            params = new Params();
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
        ProgressManager.addListener(downloadFile.getUrl(), downloadFile.getListener()); //监听进度
        downloadService.download(downloadFile.getUrl())
                .map(responseBody -> {
                    File file = downloadFile.getFile();
                    if (file == null)
                        throw new IllegalArgumentException("下载文件不能为空");

                    if (FileIOUtils.writeFileFromIS(file, responseBody.byteStream())) {
                        return file;
                    } else {
                        return null;
                    }
                })
                .compose(ThreadTransformer.io2main())
                .subscribe(new NetworkDefaultObserver<>(view, new DefaultResponseCallBack<File>() {
                    @Override
                    public void onStart(IView view) {
                        view.showLoading(downloadFile.isCancelable());
                    }

                    @Override
                    public void success(File result) {
                        if (result == null) {
                            callBack.downloadFail(new Exception("文件IO有问题"));
                        } else {
                            callBack.downloadSuccess(result);
                        }
                    }
                }));
    }
}
