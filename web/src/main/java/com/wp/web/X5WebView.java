package com.wp.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class X5WebView extends WebView {
    private OnWebLoadProgressUpdateListener listener;

    public X5WebView(Context arg0) {
        super(arg0);
        setBackgroundColor(85621);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public X5WebView(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
        WebViewClient client = new WebViewClient() {
            // 防止加载网页时调起系统浏览器
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        };

        WebChromeClient chromeClient = new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (listener != null) {
                    listener.onUpdate(newProgress);
                }
            }

            @Override
            public void onReceivedTitle(WebView webView, String s) {
            }
        };
        this.setWebChromeClient(chromeClient);
        this.setWebViewClient(client);
        initWebViewSettings();
        this.getView().setClickable(true);
    }

    private void initWebViewSettings() {
        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
    }

    public void setOnWebLoadProgressUpdateListener(OnWebLoadProgressUpdateListener listener) {
        this.listener = listener;
    }

    public interface OnWebLoadProgressUpdateListener {
        void onUpdate(int progress);
    }
}
