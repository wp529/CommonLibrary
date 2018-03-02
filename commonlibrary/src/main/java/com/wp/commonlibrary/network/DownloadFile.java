package com.wp.commonlibrary.network;

import com.wp.commonlibrary.network.progress.ProgressListener;

import java.io.File;

import retrofit2.http.PUT;

/**
 * 下载文件需要构造的信息
 * Created by WangPing on 2018/1/26.
 */

public class DownloadFile {
    private String url;
    private File file;
    private long start;
    private ProgressListener listener;

    public DownloadFile(String url, File file) {
        this(url, 0, file, null);
    }

    public DownloadFile(String url, File file, ProgressListener listener) {
        this(url, 0, file, listener);
    }

    public DownloadFile(String url, long start, File file, ProgressListener listener) {
        this.url = url;
        this.start = start;
        this.file = file;
        this.listener = listener;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public ProgressListener getListener() {
        return listener;
    }

    public void setListener(ProgressListener listener) {
        this.listener = listener;
    }
}
