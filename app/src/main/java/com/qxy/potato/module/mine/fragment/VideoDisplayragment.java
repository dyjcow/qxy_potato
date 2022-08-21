//package com.qxy.potato.module.mine.fragment;
//
//import android.app.AlertDialog;
//import android.app.Fragment;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.SystemClock;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//
//
//import android.widget.LinearLayout;
//
//import com.qxy.potato.R;
//import com.qxy.potato.app.App;
//import com.qxy.potato.module.mine.webview.MyWebView;
//import com.qxy.potato.util.LogUtil;
//import com.qxy.potato.util.ToastUtil;
//import com.tencent.smtt.export.external.TbsCoreSettings;
//
//import com.tencent.smtt.export.external.interfaces.JsPromptResult;
//import com.tencent.smtt.sdk.DownloadListener;
//import com.tencent.smtt.sdk.QbSdk;
//import com.tencent.smtt.sdk.WebChromeClient;
//import com.tencent.smtt.sdk.WebSettings;
//import com.tencent.smtt.sdk.WebView;
//import com.tencent.smtt.sdk.WebViewClient;
//
//import java.util.HashMap;
//
///**
// * @author Soul Mate
// * @brief
// * @date 2022-08-17 14:10
// */
//public class VideoDisplayragment extends Fragment {
//
//	// TODO: Rename parameter arguments, choose names that match
//	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//	private static final String ARG_PARAM1 = "url";
//
//	// TODO: Rename and change types of parameters
//	private String url;
//
//	private LinearLayout mLinearLayout;
//	private WebView mWebView;
//
//	public VideoDisplayragment() {
//		// Required empty public constructor
//	}
//
//	/**
//	 * Use this factory method to create a new instance of
//	 * this fragment using the provided parameters.
//	 *
//	 * @param url 需要传入视频具体url
//	 * @return A new instance of fragment VideoDisplayragment.
//	 */
//	// TODO: Rename and change types and number of parameters
//	public static VideoDisplayragment newInstance(String url) {
//		VideoDisplayragment fragment = new VideoDisplayragment();
//		Bundle args = new Bundle();
//		args.putString(ARG_PARAM1, url);
//
//		fragment.setArguments(args);
//		return fragment;
//	}
//
//	@Override public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		if (getArguments() != null) {
//			url = getArguments().getString(ARG_PARAM1);
//
//		}
//	}
//
//	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		// Inflate the layout for this fragment
//		View view = inflater.inflate(R.layout.fragment_mine_videodisplay, container, false);
//
//		mLinearLayout = view.findViewById(R.id.ll_video_display);
//
//		//开启硬件加速
//		getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
//				WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
//
//		//创建以及设置WebView
//		setWebView();
//
//
//		if (url != null) {
//
//			mWebView.loadUrl(url);
//		}
//
//
//
//
//
//		return view;
//	}
//
//
//	@Override public void onResume() {
//		super.onResume();
//		mWebView.resumeTimers();
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//			mWebView.onResume();
//		}
//
//	}
//
//	@Override public void onPause() {
//		if (null != mWebView) {
//
//			mWebView.onPause();
//
//		}
//		super.onPause();
//
//	}
//
//	@Override public void onStop() {
//		if (null != mWebView) {
//
//			mWebView.pauseTimers();
//
//		}
//		super.onStop();
//	}
//
//	/**
//	 * 销毁时先销毁移除webview,避免内存泄漏
//	 */
//	@Override public void onDestroy() {
//		if (mWebView != null) {
//			((ViewGroup) mWebView.getParent()).removeView(mWebView);
//			mWebView.destroy();
//			mWebView = null;
//		}
//
//		super.onDestroy();
//
//	}
//
//
//
//
//	/**
//	 * 动态创建WebView同时设置他
//	 */
//	private void setWebView() {
//
//
//		//避免WebView引起的内存泄漏
//		//动态创建
//		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//				ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//		mWebView = new MyWebView(App.getContext());
//		mWebView.setLayoutParams(params);
//		mLinearLayout.addView(mWebView);
//
//
//
//
//	}
//
//	//	/**
//	//	 * 模拟物理点击控件中心处
//	//	 *
//	//	 * @param view
//	//	 */
//	//	private void centralClick(View view) {
//	//		//左上角坐标    (left,top)
//	//		int top = view.getTop();
//	//		int left = view.getLeft();
//	//		//右下角坐标
//	//		int right = view.getRight();
//	//		int bottom = view.getBottom();
//	//		//中心
//	//		int y = (bottom - top) / 2;
//	//		int x = (right - left) / 2;
//	//
//	//		LogUtil.i("中心：" + x + " " + y);
//	//
//	//		long downTime = SystemClock.uptimeMillis();
//	//		final MotionEvent downEvent = MotionEvent.obtain(downTime, downTime,
//	//				MotionEvent.ACTION_DOWN, x, y, 0);
//	//		downTime += 1000;
//	//		final MotionEvent upEvent = MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_UP, x,
//	//				y, 0);
//	//		view.onTouchEvent(downEvent);
//	//		view.onTouchEvent(upEvent);
//	//		downEvent.recycle();
//	//		upEvent.recycle();
//
////	}
//
//	public WebView getmWebViewWebView() {
//		return mWebView;
//	}
//}