package com.wp.commonlibrary.network.progress;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.wp.commonlibrary.utils.LogUtils;
import com.wp.commonlibrary.utils.NetworkUtils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * 进度Body
 * Created by WangPing on 2018/1/24.
 */

public class ProgressResponseBody extends ResponseBody {
    private static final String TAG = ProgressResponseBody.class.getName();
    private BufferedSource bufferedSource;
    private ResponseBody responseBody;
    private ProgressListener listener;
    private String url;

    public ProgressResponseBody(String url, ResponseBody responseBody) {
        this.responseBody = responseBody;
        listener = ProgressManager.getListener(url);
        this.url = url;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(new ProgressSource(responseBody.source()));
        }
        return bufferedSource;
    }

    private class ProgressSource extends ForwardingSource {
        long totalBytesRead = 0;
        int currentProgress;

        ProgressSource(Source source) {
            super(source);
        }

        @Override
        public long read(@NonNull Buffer sink, long byteCount) throws IOException {
            if (NetworkUtils.isConnected()) {
                return doRead(sink, byteCount);
            } else {
                LogUtils.e(TAG, "没有网络连接");
                listener.networkInterrupt(url, totalBytesRead);
                listener = null;
                ProgressManager.removeListener(url);
                return -1;
            }
        }

        private long doRead(Buffer sink, long byteCount) {
            long bytesRead = -1;
            try {
                bytesRead = super.read(sink, byteCount);
            } catch (Exception e) {
                e.printStackTrace();
                LogUtils.e(e.getMessage());
                if (listener != null && TextUtils.equals("Socket closed", e.getMessage())) {
                    cancelRequest();
                } else if (listener != null && TextUtils.equals("thread interrupted", e.getMessage())) {
                    cancelRequest();
                } else if (listener != null && TextUtils.equals("Software caused connection abort", e.getMessage())) {
                    interruptConnect();
                }
                return bytesRead;
            }
            long fullLength = responseBody.contentLength();
            if (listener != null && totalBytesRead == 0) {
                listener.onStart(url, fullLength);
            }
            if (bytesRead == -1) {
                totalBytesRead = fullLength;
            } else {
                totalBytesRead += bytesRead;
            }
            int progress = (int) (100f * totalBytesRead / fullLength);

            if (listener != null && progress != currentProgress) {
                listener.onProgress(progress);
            }
            if (listener != null && totalBytesRead == fullLength) {
                listener.onEnd(url);
                listener = null;
                ProgressManager.removeListener(url);
            }
            currentProgress = progress;
            return bytesRead;
        }

        private void interruptConnect() {
            listener.networkInterrupt(url, totalBytesRead);
            listener = null;
            ProgressManager.removeListener(url);
        }

        private void cancelRequest() {
            listener.cancel(url, totalBytesRead);
            listener = null;
            ProgressManager.removeListener(url);
        }
    }

}
