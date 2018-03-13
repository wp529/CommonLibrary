package com.wp.web;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;

import com.wp.commonlibrary.ActivityManager;
import com.wp.commonlibrary.utils.ToastUtils;

/**
 * 加载HTML的界面
 * Created by WangPing on 2018/3/13.
 */

public class WebActivity extends Activity {
    public static final int WEB_REQUEST_CODE = 0X00000001;
    public static final String WEB_URL = "webUrl";
    private X5WebView webView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_moudle_activity_web);
        ActivityManager.getActivityManager().addActivity(this);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        webView = (X5WebView) findViewById(R.id.x5_web_view);
        progressBar = (ProgressBar) findViewById(R.id.web_progress_bar);
    }

    private void initData() {
        String url = getIntent().getStringExtra(WEB_URL);
        if (TextUtils.isEmpty(url)) {
            ToastUtils.showToast("链接不能为空");
        } else {
            webView.loadUrl(url);
        }
    }

    private void initListener() {
        webView.setOnWebLoadProgressUpdateListener(progress -> {
            if (progress == 100 && progressBar.getVisibility() == View.VISIBLE) {
                progressBar.setProgress(progress);
                progressBar.setVisibility(View.GONE);
            } else {
                if (progressBar.getVisibility() == View.GONE) {
                    progressBar.setVisibility(View.VISIBLE);
                }
                //设置加载进度
                progressBar.setProgress(progress);
            }
        });
    }

    public static void startLoadHTML(Activity context, String url) {
        startLoadHTML(context, url, WEB_REQUEST_CODE);
    }

    public static void startLoadHTML(Activity context, String url, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(context, WebActivity.class);
        intent.putExtra(WEB_URL, url);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getActivityManager().removeActivity(this);
    }

    @Override
    public void onBackPressed() {
        webViewBack();
    }

    private void webViewBack() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }
}
