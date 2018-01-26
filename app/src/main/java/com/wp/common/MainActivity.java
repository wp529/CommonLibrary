package com.wp.common;

import android.view.View;
import android.widget.ImageView;

import com.wp.common.dagger.DaggerActivityComponent;
import com.wp.commonlibrary.baseMVP.BaseActivity;

public class MainActivity extends BaseActivity<MainPresenter> {
    private ImageView iv;

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void findView() {
        iv = (ImageView) findView(R.id.iv);
    }

    @Override
    protected void inject() {
        DaggerActivityComponent.create().inject(this);
    }

    public void login(View view) {
        //请求接口
        mPresenter.requestData();

        //加载图片 可带进度
        /*String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1516793144571&di=01beb0d58d63c328051647c96c7d3742&imgtype=0&src=http%3A%2F%2Fi1.hdslb.com%2Fbfs%2Farchive%2F58619c927133fd015f1656ea505cef48c20089ba.jpg";
        GlideHelper.getDefault().loadNetImage(this, new DownloadImage(url, iv));*/
    }
}
