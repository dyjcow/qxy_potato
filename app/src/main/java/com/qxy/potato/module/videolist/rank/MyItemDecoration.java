package com.qxy.potato.module.videolist.rank;

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Color;

import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;

import android.graphics.Shader;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



/**
 * @author yinxiaolong
 * @describe :recyclerView 分割线 以及  top标签
 * @data: 2022/8/11
 */
public class MyItemDecoration extends RecyclerView.ItemDecoration {

    private final Paint textPaint =new Paint();
    private final Paint paint =new Paint();
    private final Paint linePaint=new Paint();

    /**
     * 对画笔进行初始化操作
     * @param context
     */
    public MyItemDecoration(Context context){
        textPaint.setColor(Color.parseColor("#a64936"));

        //paint.setColor(Color.parseColor("#ffff00"));
        Shader shader= new LinearGradient(0,0,100,1000, Color.parseColor("#fff9e6"),Color.parseColor("#fec002"), Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);
        linePaint.setColor(Color.BLACK);
        linePaint.setAntiAlias(true);
        paint.setAntiAlias(true);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(50);
    }

    /**
     * 用于测量view的矩形
     */
    private final Rect mBounds = new Rect();

    /**
     * 绘制 分割线
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);

        for (int i = 0; i < parent.getChildCount(); i++) {
            final View child=parent.getChildAt(i);
            parent.getDecoratedBoundsWithMargins(child,mBounds);
            c.drawLine(400,mBounds.bottom,mBounds.right-50,mBounds.bottom,linePaint);
        }
    }

    /**
     * 绘制标签
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

        for (int i = 0; i < parent.getChildCount(); i++) {
            final View child=parent.getChildAt(i);
            int position=parent.getChildAdapterPosition(child);
            parent.getDecoratedBoundsWithMargins(child,mBounds);
            c.drawRoundRect(mBounds.left+48,mBounds.top+10,mBounds.left+170,mBounds.top+70,10,10, paint);
            if (position<3){
                c.drawText("TOP"+(position+1)+"",mBounds.left+48,mBounds.top+60, textPaint);
            }else {
                c.drawText(position+"",mBounds.left+80,mBounds.top+60, textPaint);
            }
        }
    }

    /**
     * 间距
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.set(0,10,0,40);
    }
}
