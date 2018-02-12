package com.wp.sharelogin.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.umeng.socialize.UMShareAPI;
import com.wp.commonlibrary.utils.LogUtils;
import com.wp.sharelogin.bean.ShareInfo;
import com.wp.sharelogin.callback.IThirtyPartyShareListener;

/**
 * SharePanelActivity
 * Created by WangPing on 2018/2/12.
 */

public class SharePanelActivity extends Activity {
    private static final String SHARE_INFO = "share_info";
    private static IThirtyPartyShareListener listener;
    private static boolean isShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.overridePendingTransition(com.wp.commonlibrary.R.anim.common_library_activity_alpha_in, com.wp.commonlibrary.R.anim.common_library_activity_alpha_in);
        ShareInfo info = getIntent().getParcelableExtra(SHARE_INFO);
        ThirdPartyPanelHelper.getDefault().showDefaultSharePanel(this, info, listener);
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(com.wp.commonlibrary.R.anim.common_library_activity_alpha_exit, com.wp.commonlibrary.R.anim.common_library_activity_alpha_exit);
    }

    public static void startShare(Context context, ShareInfo info, IThirtyPartyShareListener listener) {
        if (isShowing)
            return;
        isShowing = true;
        Intent intent = new Intent();
        intent.setClass(context, SharePanelActivity.class);
        intent.putExtra(SHARE_INFO, info);
        SharePanelActivity.listener = listener;
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isShowing = false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);//完成回调
    }
}
