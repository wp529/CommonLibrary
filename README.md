使用 [Blankj](https://github.com/Blankj/AndroidUtilCode)大神的部分工具类
#####此库目前包含有

* 网络请求
* 图片加载
* 文件下载
* 下载进度
* 权限安全操作
* 图片预览
* 三方分享&登录

#####每个功能组件都有默认实现，helper为入口，直接调用使用，使用接口暴露出服务，所以在更换底层框架时重新实现服务接口即可。

* 网络请求部分默认使用Okhttp,Retrofit,RxJava实现
* 图片使用Glide
* 权限使用RxPermission
* 图片预览类今日头条效果，是个完整的组件

#####示例代码
* 请求接口

```
Params params = new Params();
params.param("start", start);
params.param("count", count);
NetworkHelper.getDefault().get(mView, "v2/movie/top250", params, new DefaultResponseCallBack<String>() {
            @Override
            public void success(String result) {
                mView.requestMovieSuccess(result);
            }

            @Override
            public void onStart(IView view) {
                //设置加载框可否取消
                view.showLoading(true);
            }
        });

```

* 下载文件
```
NeedWifiOperate.getDefault().networkTypeShouldBeWifi(new DefaultNetworkTypeCallBack(this) {
            @Override
            public void wifi(int netType) {
                downloadFile();
            }
        }.setDialogOperateListener(new DialogOperateAdapter() {
            @Override
            public void positive(Context context, Dialog dialog) {
                downloadFile();
            }
        }));

private void downloadFile() {
        File file = new File(CommonApplication.context.getCacheDir(), System.currentTimeMillis() + ".apk");
        DownloadFile downloadFile = new DownloadFile("http://gdown.baidu.com/data/wisegame/13095bef5973a891/QQ_786.apk", file, true, new ChangeViewWithProgressListener(tvExample));
        mPresenter.downloadFile(downloadFile);
    }

NetworkHelper.getDefault().download(mView, downloadFile, new FileCallBack() {
            @Override
            public void downloadSuccess(File file) {
                mView.downloadFileSuccess(file);
            }

            @Override
            public void downloadFail(Throwable e) {
                mView.downloadFileFail(e);
            }
        });
```

* 加载网络图片可带进度
```
String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999" +
                "_10000&sec=1516793144571&di=01beb0d58d63c328051647c96c7d3742" +
                "&imgtype=0&src=http%3A%2F%2Fi1.hdslb.com%2Fbfs%2Farchive%2F5" +
                "8619c927133fd015f1656ea505cef48c20089ba.jpg";
ImageHelper.getDefault().loadImage(this, new DownloadImage.Builder()
                .path(url)
                .targetView(ivExample)
                .memoryCache(false)
                .diskCache(false)
                .build());
```
* 加载本地图片
```
NeedPermissionOperate.getDefault().buildSafeExternalStoragePath(this, "scene_photo.jpg",
new MustGrantPermissionCallBack(this) {
            @Override
            public void granted(Context context, String result) {
                ImageHelper.getDefault().loadImage(context,
                new DownloadImage.Builder().path(result).targetView(ivExample).build());
            }
        });
```
* 请求权限
```
PermissionHelper.getDefault().requestPermissions(this, new MustGrantPermissionCallBack(this) {
            @Override
            public void granted(Context context, String result) {

            }
        }, Permission.cameraPermission());
```
* 图片预览
```
String[] images = new String[3];
images[0] = "http://p9.pstatp.com/large/615c0002e579e79689d0";
images[1] = "http://p3.pstatp.com/large/615e00012d933c0d545f";
images[2] = "http://p1.pstatp.com/large/615c0002e578aee415e6";
ImagesPreviewActivity.startImagesPreview(this, images, 0);
```
* 三方分享
你需要在share&login组件库的ShareLoginApplication和Manifest中设置三方平台的appkey，不然不能正常使用该组件
可在ThirdPartyPanelHelper中重新定义需要的分享版样式
```
SharePanelActivity.startShare(this, new ShareInfo("hello"), new IThirtyPartyShareListener() {
            @Override
            public void onShareStart(String platform) {
                showLoading();
                LogUtils.e("onShareStart: " + platform);
            }

            @Override
            public void onShareEnd(String platform) {
                dismissLoading();
                LogUtils.e("onShareEnd: " + platform);
            }

            @Override
            public void onShareError(String platform, Throwable throwable) {
                dismissLoading();
                ToastUtils.showToast(throwable.getMessage());

            }

            @Override
            public void onShareCancel(String platform) {
                dismissLoading();
                LogUtils.e("onShareCancel: " + platform);
            }
        });
```
* 三方登录
```
ThirdPartyLoginHelper.getDefault().login2QQ(this, new IThirtyPartyLoginListener() {
            @Override
            public void loginStart(String platform) {

            }

            @Override
            public void loginSuccess(String platform, ThirdPartyUserInfo userInfo) {

            }

            @Override
            public void loginError(String platform, Throwable throwable) {

            }

            @Override
            public void loginCancel(String platform) {

            }
        });
```