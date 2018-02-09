package com.wp.commonlibrary.network;

import java.io.File;

/**
 * 下载文件需要构造的信息
 * Created by WangPing on 2018/1/26.
 */

public class DownloadFile {
    private String url;
    private File file;
    private boolean cancelable;
    private ProgressListener listener;

    public DownloadFile(String url, File file) {
        this(url, file, false, null);
    }

    public DownloadFile(String url, File file, boolean cancelable, ProgressListener listener) {
        this.url = url;
        this.file = file;
        this.cancelable = cancelable;
        this.listener = listener;
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

    public boolean isCancelable() {
        return cancelable;
    }

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    public ProgressListener getListener() {
        return listener;
    }

    public void setListener(ProgressListener listener) {
        this.listener = listener;
    }
}
