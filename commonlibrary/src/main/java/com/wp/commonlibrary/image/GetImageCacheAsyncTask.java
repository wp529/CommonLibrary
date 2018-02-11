package com.wp.commonlibrary.image;

import android.content.Context;
import android.os.AsyncTask;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.wp.commonlibrary.CommonApplication;
import com.wp.commonlibrary.network.FileCallBack;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * 获取Glide缓存的异步任务
 * Created by WangPing on 2018/2/8.
 */

public class GetImageCacheAsyncTask extends AsyncTask<String, Void, File> {
    private FileCallBack callBack;

    public GetImageCacheAsyncTask(FileCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    protected File doInBackground(String... params) {
        String imgUrl = params[0];
        try {
            return Glide.with(CommonApplication.context.getApplicationContext())
                    .load(imgUrl)
                    .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(File result) {
        if (result == null) {
            callBack.downloadFail(new FileNotFoundException("缓存文件没找到"));
            return;
        }
        callBack.downloadSuccess(result);
    }
}
