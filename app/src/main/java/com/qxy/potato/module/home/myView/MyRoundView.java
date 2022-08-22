package com.qxy.potato.module.home.myView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author :yinxiaolong
 * @describe : com.qxy.potato.module.home.myView
 * @date :2022/8/18 17:21
 */
public class MyRoundView extends View {
    public MyRoundView(Context context) {
        super(context);
    }

    public MyRoundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRoundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Paint paint;
    private int width;
    {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#ffffff"));
        width=displayMetrics.widthPixels;
    }
    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawRoundRect(0,0,width,100,200,200,paint);
    }
}
