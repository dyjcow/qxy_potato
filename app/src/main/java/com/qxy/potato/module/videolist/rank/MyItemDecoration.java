package com.qxy.potato.module.videolist.rank;

import android.content.Context;
import android.graphics.Bitmap;

import android.graphics.Canvas;
import android.graphics.Color;

import android.graphics.Paint;
import android.graphics.Rect;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



/**
 * @author yinxiaolong
 * @describe :recyclerView 分割线 以及  top标签
 * @data: 2022/8/11
 */
public class MyItemDecoration extends RecyclerView.ItemDecoration {

    private final Paint TextPaint =new Paint();
    private final Paint paint =new Paint();

    Bitmap bitmap;
    public MyItemDecoration(Context context){
        TextPaint.setColor(Color.parseColor("#a33656"));
       // paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#ffff00"));
    }
    private final Rect mBounds = new Rect();
    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        TextPaint.setTextSize(50);
        for (int i = 0; i < parent.getChildCount(); i++) {
            final View child=parent.getChildAt(i);
            parent.getDecoratedBoundsWithMargins(child,mBounds);
            paint.setColor(Color.BLACK);
            c.drawLine(400,mBounds.bottom,mBounds.right-50,mBounds.bottom,paint);
            paint.setColor(Color.parseColor("#ffff00"));
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

        for (int i = 0; i < parent.getChildCount(); i++) {
            final View child=parent.getChildAt(i);
            int position=parent.getChildAdapterPosition(child);
            parent.getDecoratedBoundsWithMargins(child,mBounds);
            c.drawRect(mBounds.left+48,mBounds.top+10,mBounds.left+170,mBounds.top+70, paint);
            if (position<3){
                c.drawText("TOP"+(position+1)+"",mBounds.left+48,mBounds.top+60, TextPaint);
            }else {
                c.drawText(position+"",mBounds.left+90,mBounds.top+60, TextPaint);
            }
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.set(0,10,0,40);
    }
}
