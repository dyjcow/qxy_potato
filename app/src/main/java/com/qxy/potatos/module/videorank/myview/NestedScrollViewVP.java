package com.qxy.potatos.module.videorank.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

/**
 * @author ：Dyj
 * @date ：Created in 2022/9/5 11:06
 * @description：自定义的NestedScrollView，解决滑动冲突
 * @modified By：
 * @version: 1.0
 */
public class NestedScrollViewVP extends NestedScrollView {

	public NestedScrollViewVP(@NonNull Context context) {
		super(context);
	}

	public NestedScrollViewVP(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public NestedScrollViewVP(@NonNull Context context, @Nullable AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	private int startX, startY;
	boolean isDisallowIntercept = false;


	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startX = (int) ev.getX();
			startY = (int) ev.getY();
			getParent().requestDisallowInterceptTouchEvent(true);//告诉viewgroup不要去拦截我
			break;
		case MotionEvent.ACTION_MOVE:
			int endX = (int) ev.getX();
			int endY = (int) ev.getY();

			int disX = endX - startX;
			int disY = endY - startY;
			//角度正确，则让上层view别拦截我的事件
			float r = (float)Math.abs(disY)/Math.abs(disX);
			if (r > 0.6f) isDisallowIntercept = true;
			getParent().requestDisallowInterceptTouchEvent(isDisallowIntercept);
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			isDisallowIntercept = false;
			getParent().requestDisallowInterceptTouchEvent(false);
			break;
		default:
			break;
		}
		return super.dispatchTouchEvent(ev);
	}

}
