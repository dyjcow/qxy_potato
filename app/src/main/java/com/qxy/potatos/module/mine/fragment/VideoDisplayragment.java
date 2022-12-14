package com.qxy.potatos.module.mine.fragment;

import android.annotation.SuppressLint;




import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;



import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.qxy.potatos.R;
import com.qxy.potatos.app.App;
import com.qxy.potatos.module.mine.webview.MyWebView;

import com.qxy.potatos.util.LogUtil;
import com.tencent.smtt.sdk.WebView;


/**
 * @author Soul Mate
 * @brief
 * @date 2022-08-17 14:10
 */
public class VideoDisplayragment extends Fragment {


	private String url;

	private LinearLayout mLinearLayout;
	private WebView mWebView;

	public VideoDisplayragment() {
		// Required empty public constructor
	}


	@SuppressLint("ValidFragment")
	public VideoDisplayragment(String url) {
		super();
		this.url = url;
	}



	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtil.i("onCreate");

	}

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		LogUtil.i("onCreateView");

		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_videodisplay, container, false);

		mLinearLayout = view.findViewById(R.id.ll_video_display);

		//创建以及设置WebView
		setWebView();


		if (url != null) {

			mWebView.loadUrl(url);
		}


		return view;
	}


	@Override public void onResume() {
		super.onResume();


		LogUtil.i("onResume");

		mWebView.resumeTimers();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			mWebView.onResume();
		}


	}

	@Override public void onPause() {

		LogUtil.i("onPause");

		if (null != mWebView) {

			mWebView.onPause();

		}
		super.onPause();

	}

	@Override public void onStop() {

		LogUtil.i("onStop");

		if (null != mWebView) {

			mWebView.pauseTimers();

		}
		super.onStop();
	}

	/**
	 * 销毁时先销毁移除webview,避免内存泄漏
	 */
	@Override public void onDestroy() {

		LogUtil.i("onDestroy");

		if (mWebView != null) {
			((ViewGroup) mWebView.getParent()).removeView(mWebView);
			mWebView.destroy();
			mWebView = null;
		}

		super.onDestroy();

	}


	/**
	 * 动态创建WebView同时设置他
	 */
	private void setWebView() {


		//避免WebView引起的内存泄漏
		//动态创建
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		mWebView = new MyWebView(App.getContext());
		mWebView.setLayoutParams(params);
		mWebView.setBackgroundColor(getResources().getColor(R.color.black));
		mLinearLayout.addView(mWebView);




	}
}