package com.wp.commonlibrary.baseMVP;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.wp.commonlibrary.ActivityManager;
import com.wp.commonlibrary.R;
import com.wp.commonlibrary.network.retrofit.ObservableManager;
import com.wp.commonlibrary.utils.ReflectUtils;
import com.wp.commonlibrary.utils.ToastUtils;

import javax.inject.Inject;

/**
 * Activity的基类
 * Created by WangPing on 2018/1/23.
 */

public abstract class BaseActivity<T extends IPresenter> extends Activity implements IView {
    @Inject
    protected T mPresenter;
    private KProgressHUD hud;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId());
        findView();
        inject();
        mPresenter.attachView(this);
        ActivityManager.getAppManager().addActivity(this);
    }

    protected abstract void findView();

    protected abstract int layoutId();

    protected abstract void inject();

    protected View findView(@IdRes int id) {
        return findViewById(id);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.detachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getAppManager().removeActivity(this);
    }

    @Override
    public void showLoading() {
        showLoading(false);
    }

    @Override
    public void showLoading(boolean cancelable) {
        if (hud == null) {
            hud = KProgressHUD
                    .create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel(getResources().getString(R.string.loading_text))
                    .setCancellable(cancelable);

        }
        Dialog dialog = (Dialog) ReflectUtils.getDeclaredObject(hud, "mProgressDialog");
        loadingCancelable(dialog, cancelable);
        hud.show();
    }

    private void loadingCancelable(Dialog dialog, boolean cancelable) {
        if (cancelable) {
            dialog.setOnKeyListener((dialog1, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    ObservableManager.getInstance().stopObserver();
                    dialog1.dismiss();
                }
                return true;
            });
        } else {
            dialog.setOnKeyListener(null);
        }
        dialog.setCancelable(cancelable);
    }


    @Override
    public void dismissLoading() {
        if (isLoadingShow()) {
            hud.dismiss();
        }
    }

    @Override
    public void showCommonToast(String msg) {
        ToastUtils.showCommonToast(msg);
    }

    @Override
    public void showToastWithIcon(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public boolean isLoadingShow() {
        return hud != null && hud.isShowing();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
