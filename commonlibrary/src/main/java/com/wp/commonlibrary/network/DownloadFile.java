package com.wp.commonlibrary.network;

import java.io.File;

/**
 * Created by WangPing on 2018/1/26.
 */

public class DownloadFile {
    private String url;
    private File file;
    private ProgressListener listener;

    public DownloadFile(String url, File file, ProgressListener listener) {
        this.url = url;
        this.file = file;
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

    public ProgressListener getListener() {
        return listener;
    }

    public void setListener(ProgressListener listener) {
        this.listener = listener;
    }
}
