package com.qxy.potato.module.mine.webview;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.qxy.potato.R;
import com.qxy.potato.app.App;
import com.qxy.potato.util.ActivityUtil;
import com.qxy.potato.util.LogUtil;
import com.qxy.potato.util.ToastUtil;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * @author Soul Mate
 * @brief 定制的Webview
 * @date 2022-08-20 20:21
 */

public class MyWebView extends WebView {

    public MyWebView(Context context) {
        super(context);
        init();
    }


    //处理webview与ViewPager的滑动冲突，这里是响应webview的滑动，webview到边缘后响应父视图（ViewPager）的滑动

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        if (clampedX) {
            ViewParent viewParent = findViewParentIfNeeds(this);
            viewParent.requestDisallowInterceptTouchEvent(false);
        }
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ViewParent viewParent = findViewParentIfNeeds(this);
            if (viewParent != null)
                viewParent.requestDisallowInterceptTouchEvent(true);
        }
        return super.onTouchEvent(event);
    }

    private ViewParent findViewParentIfNeeds(View tag) {
        ViewParent parent = tag.getParent();
        if (parent == null) {
            return null;
        }
        if (parent instanceof ViewPager2 || parent instanceof AbsListView || parent instanceof ScrollView || parent instanceof HorizontalScrollView) {
            return parent;
        } else {
            if (parent instanceof View) {
                findViewParentIfNeeds((View) parent);
            } else {
                return parent;
            }
        }
        return parent;
    }




    private void init() {
        //不使用系统浏览器
        this.setWebChromeClient(new WebChromeClient() {


            //
            //			/**
            //			 * js交互
            //			 * @param webView
            //			 * @param s
            //			 * @param s1
            //			 * @param jsResult
            //			 * @return
            //			 */
            //			@Override public boolean onJsAlert(WebView webView, String s, String s1,
            //					JsResult jsResult) {
            //				AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
            //				b.setTitle("Alert");
            //				b.setMessage(s1);
            //				b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            //					@Override
            //					public void onClick(DialogInterface dialog, int which) {
            //						jsResult.confirm();
            //					}
            //				});
            //				b.setCancelable(false);
            //				b.create().show();
            //				return true;
            //
            //			}
            //
            //			/**
            //			 * 显示自定义视图，无此方法视频不能播放
            //			 */
            //			@Override public void onShowCustomView(View view,
            //					IX5WebChromeClient.CustomViewCallback callback) {
            //				super.onShowCustomView(view, callback);
            //			}
            //
            //			//			@Override public void onProgressChanged(WebView webView, int i) {
            //			//
            //			//				super.onProgressChanged(webView, i);
            //			//				if (i == 100) {
            //			////					centralClick(mWebView);
            //			//				}
            //			//
            //			//			}
        });

        this.setWebViewClient(new WebViewClient() {

            /**
             * 所有资源加载都会走的方法
             * @param view
             * @param url
             */
            @Override
            public void onLoadResource(final WebView view, String url) {
                super.onLoadResource(view, url);
                LogUtil.i(url);
                //				//视频自动播放js方法
                //				String videoJs = "javascript: var v = document.getElementsByTagName('video'); v[0].play();";
                //				view.loadUrl(videoJs);
                //				//js代码：资源加载完成回调
                //				String videoJs = "javascript: window.onload = function(){ var v = document.getElementsByTagName('video'); v[0].SetAttribute('autoplay', 'true');v[0].load();}";
                //				view.loadUrl(videoJs);
                //				String videoJs = "javascript: var v = document.getElementsByTagName('video'); v[0].SetAttribute('autoplay', 'true'); v[0].load()";
                //
                //				view.loadUrl(videoJs);

                //借助js，onload()加载完成就回调，实现自动播放
                String videoJs = "javascript: var v = document.getElementsByTagName('video'); v[0].autoplay=true;  v[0].onload=function() {this.play()}";

                view.loadUrl(videoJs);




            }

            /**
             * 重定向
             * @param view
             * @param url
             * @return
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url == null)
                    return false;

                try {//跳转到APP
                    if (!url.startsWith("http://") && !url.startsWith("https://")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));


                        if (isInstall(intent)) {//已安装则打开
                            ActivityUtil.getCurrentActivity().startActivity(intent);
                            return true;
                        } else {//未安装抖音则跳转去系统浏览器下载页面
                            LogUtil.i("跳转到系统浏览器");
                            ToastUtil.showToast("请先下载抖音APP");
                            Intent intent1 = new Intent(Intent.ACTION_VIEW);
                            Uri uri = Uri.parse(getResources().getString(R.string.download_douyin));
                            intent1.addCategory(Intent.CATEGORY_BROWSABLE);
                            intent1.setData(uri);
                            ActivityUtil.getCurrentActivity().startActivity(intent1);

                            return true;
                        }

                    }
                } catch (Exception e) {//防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return true;//没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出上面的错误页面
                }

                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            /**
             * 手动代码开启播放,js办法都不行，尝试模拟用户点击
             * 还在转圈圈就调用了
             *
             * @param view
             * @param url
             */
            @Override
            public void onPageFinished(WebView view, String url) {
//				super.onPageFinished(view, url);
                //		view.loadUrl("javascript:try{autoplay();}catch(e){}");

                //		view.loadUrl("javascript:myFunction()");

                //				//音频自动播放js方法
                //				String audioJs = "javascript: var v = document.getElementsByTagName('audio'); v[0].play();";
                //				view.loadUrl(audioJs);
                //视频自动播放js方法
                //								String videoJs = "javascript: var v = document.getElementsByTagName('video'); v[0].play();";
                //								view.loadUrl(videoJs);

                //要确定是否是首次
                //				centralClick(view);
                //js代码：资源加载完成回调
                //				String videoJs = "javascript: window.onload = function(){ var v = document.getElementsByTagName('video'); v[0].SetAttribute('autoplay', 'true');v[0].play();}";

                //				String videoJs = "javascript: var v = document.getElementsByTagName('video'); alert(v[0])";
                //
                //				view.loadUrl(videoJs);
//				LogUtil.i("fuck");
                //				String videoJs = "javascript: var v = document.getElementsByTagName('video'); v[0].autoplay=true; alert(v[0]);  v[0].load();";
                //
                //				view.loadUrl(videoJs);
                //				String videoJs = "javascript: var v = document.getElementsByTagName('video'); v[0].autoplay=true;  v[0].play();";
                //
                //				view.loadUrl(videoJs);
                //				String videoJs = "javascript: window.onload = function(){ var v = document.getElementsByTagName('video'); alert(v[0]);v[0].play();alert();}";
                //				view.loadUrl(videoJs);
                //				String videoJs = "javascript: var v = document.getElementsByTagName('video'); v[0].autoplay=true;  v[0].onload=function() {this.play()}";
                //
                //				view.loadUrl(videoJs);

//慢
//				getSettings().setBlockNetworkImage(false);
//				//判断webview是否加载了，图片资源
//				if (!getSettings().getLoadsImagesAutomatically()) {
//					//设置wenView加载图片资源
//					getSettings().setLoadsImagesAutomatically(true);
//				}


                //消除最上方的打开看看
                String js = "javascript: let header=document.getElementsByClassName('login-header'); header[0].style.display='none';";
                view.loadUrl(js);
                super.onPageFinished(view, url);


            }

        });


        com.tencent.smtt.sdk.WebSettings webSetting = this.getSettings();

//		//先阻塞加载图片(先加载页面再加载大资源)
//		webSetting.setBlockNetworkImage(true);

        // 设置允许JS弹窗
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);

        webSetting.setAllowContentAccess(true); //是否允许在WebView中访问内容URL（Content Url）  默认允许
        webSetting.setAllowFileAccess(true); //  是否允许访问文件，默认允许。注意，这里只是允许或禁止对文件系统的访问
        webSetting.setAllowFileAccessFromFileURLs(
                true);// 是否允许运行在一个URL环境（the context of a file scheme URL）中的JavaScript访问来自其他URL环境的内容，为了保证 安全，应该不允许

        webSetting.setAppCacheEnabled(true); // 应用缓存API是否可用，默认值false 结合setAppCachePath(String)使用。
        String appCacheDir = App.getContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        webSetting.setAppCachePath(
                appCacheDir); //设置应用缓存文件的路径。为了让应用缓存API可用，此方法必须传入一个应用可写的路径。 该方法只会执行一次，重复调用会被忽略。
//		webSetting.setAppCacheMaxSize(5*1024*1024);

        webSetting.setLoadsImagesAutomatically(
                true); // WebView是否下载图片资源，默认为true。如果该设置项的值由false变为true，WebView展示的内容所引用的所有的图片资源将自动下载。
        webSetting.setBlockNetworkImage(
                false);//是否禁止从网络（通过http和https URI schemes访问的资源）下载图片资源，默认值为false 除非setLoadsImagesAutomatically()返回 true,否则该方法无效

        webSetting.setBuiltInZoomControls(
                true);  //是否使用内置的缩放机制 默认值为false。 内置的缩放机制包括屏幕上的缩放控件（浮于WebView内容之上）和缩放手势的运用。通过 setDisplayZoomControls(boolean)可以控制是否显示这些控件
        webSetting.setDisplayZoomControls(
                false); // 使用内置的缩放机制时是否展示缩放控件，默认值true。参见setBuiltInZoomControls(boolean).

        webSetting.setCacheMode(
                com.tencent.smtt.sdk.WebSettings.LOAD_DEFAULT); // 重写使用缓存的方式，默认值LOAD_DEFAULT。缓存的使用方式基于导航类型，正常的页面加载，检测缓存，需要时缓存内容复现。导航返回时，内容 不会复现，只有内容会从缓存盘中恢复。该方法允许客户端通过指定LOAD_DEFAULT, LOAD_CACHE_ELSE_NETWORK, LOAD_NO_CACHE or LOAD_CACHE_ONLY的其中一项来重写其行为。
        webSetting.setDatabaseEnabled(
                true); // 数据库存储API是否可用，默认值false如何正确设置数据存储API参见setDatabasePath(String)。该设置对同一进程中的所有WebView实例均有 效。注意，只能在当前进程的任意WebView加载页面之前修改此项，因为此节点之后WebView的实现类可能会忽略该项设置的改变。
        //        webSetting.setDatabasePath ("") // 已废弃，数据库路径由实现（implementation）管理，调用此方法无效。
        webSetting.setDomStorageEnabled(true);//DOM存储API是否可用，默认false。

        webSetting.setJavaScriptCanOpenWindowsAutomatically(
                true); // 让JavaScript自动打开窗口，默认false。适用于JavaScript方法window.open()。
        webSetting.setJavaScriptEnabled(true);//设置WebView是否允许执行JavaScript脚本，默认false，不允许
        webSetting.setLayoutAlgorithm(
                com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm.NORMAL); //设置布局，会引起WebView的重新布局（relayout）,默认值NARROW_COLUMNS 视频适应屏幕

        webSetting.setLoadWithOverviewMode(
                false); // 是否允许WebView度超出以概览的方式载入页面，默认false。即缩小内容以适应屏幕宽度。该项设置在内容宽度超出WebView控件的宽度时生效，例 如当getUseWideViewPort() 返回true时。

        // tmd无效
        webSetting.setMediaPlaybackRequiresUserGesture(
                false);// WebView是否需要用户的手势进行媒体播放，默认值为true。false则自动播放无效

        //		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        //			webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW); // 当一个安全的来源（origin）试图从一个不安全的来源加载资源时配置WebView的行为 LOLLIPOP版本默认值MIXED_CONTENT_NEVER_ALLOW，WebView首选的最安全的操作模式为MIXED_CONTENT_NEVER_ALLOW ，不鼓励使用 MIXED_CONTENT_ALWAYS_ALLOW。
        //		}

        webSetting.setNeedInitialFocus(
                true); // 调用requestFocus(int, Android.graphics.Rect)时是否需要设置节点获取焦点，默认值为true。
        webSetting.setSaveFormData(true); // WebView是否保存表单数据，默认值true。
        webSetting.setSupportMultipleWindows(
                false); // 设置WebView是否支持多窗口。如果设置为true，主程序要实现onCreateWindow(WebView, boolean, boolean, Message)，默认false。如果设置了true并且没有实现onCreateWindiw 会在onShouldOverrideUrlLoading监听不到跳转的url
        webSetting.setSupportZoom(
                true);//WebView是否支持使用屏幕上的缩放控件和手势进行缩放，默认值true。设置setBuiltInZoomControls(boolean)可以使用特殊的缩放机制。
        webSetting.setUseWideViewPort(
                true);// WebView是否支持HTML的“viewport”标签或者使用wide viewport。设置值为true时，布局的宽度总是与WebView控件上的设备无关像素（device- dependent pixels）宽度一致。当值为true且页面包含viewport标记，将使用标签指定的宽度。
        webSetting.setUserAgentString(
                "android"); // 设置WebView的用户代理字符串。如果字符串为null或者empty，将使用系统默认值。注意从KITKAT版本开始，加载网页时改变用户代理会让WebView 再次初始化加载。


        //		mWebView.setOnTouchListener(new View.OnTouchListener() {
        //			@Override public boolean onTouch(View view, MotionEvent motionEvent) {
        //				LogUtil.i("点击了"+motionEvent.getX()+"  "+motionEvent.getY());
        //				return false;
        //			}
        //		});


    }


    /**
     * 判断app是否安装
     *
     * @param intent
     * @return
     */
    private boolean isInstall(Intent intent) {
        return App.getContext().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0;
    }

    /**
     * onResume播放视频
     */
    public void play(){
        String videoJs = "javascript: var v = document.getElementsByTagName('video'); v[0].play()";
        loadUrl(videoJs);
    }


}
