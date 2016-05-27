package com.wfl.kits.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.wfl.kits.R;

/**
 * Created by wfl on 15/11/24.
 */
public class OverScrollListView extends ListView{
    private Bitmap headerBg;
    private Matrix bgMatrix;
    private float xScale = 1f;
    private float yScale = 1f;
    private float movedLength = 0;
    private float x, y, deltaY;
    private boolean isOverScrolling = false;
    /**
     * over 的height
     */
    private float overHeight = 0;

    public OverScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OverScrollListView(Context context) {
        super(context);
        init();
    }

    public OverScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        headerBg = BitmapFactory.decodeResource(getResources(), R.drawable.scenery0);
        bgMatrix = new Matrix();
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        deltaY = ev.getY() - y;
        deltaY *= 0.5f;
        x = ev.getX();
        y = ev.getY();
        int action = ev.getActionMasked();
        if (action == MotionEvent.ACTION_MOVE){
            boolean isTop = isTop();
            boolean needScroll = isTop && deltaY > 0;
            if (needScroll && !isOverScrolling) {
                isOverScrolling = true;
                overHeight = 0;
            }
            if (needScroll) {
                overHeight += deltaY;
                movedLength += deltaY;
                if (overHeight > 0) {
                    xScale = yScale = overHeight * 2 / getWidth() + 1;
                    bgMatrix.setScale(xScale, yScale, headerBg.getWidth() / 2, headerBg.getHeight() / 2);
                    scrollBy(0 , -(int) deltaY);
                    invalidate();
                    return true;
                } else {
                    if (isOverScrolling) {
                        isOverScrolling = false;
                        overHeight = 0;
                        bgMatrix.setScale(1, 1);
                        scrollTo(0 , 0);
                        invalidate();
                        return true;
                    }
                }
            } else {
                return super.onTouchEvent(ev);
            }
        } else if (action == MotionEvent.ACTION_UP) {
            releaseToStart();
        }
        return super.onTouchEvent(ev);
    }

    private boolean isTop() {
        if (getFirstVisiblePosition() == 0) {
            if (getChildCount() > 0) {
                View v = getChildAt(0);
                if (v.getTop() == 0) {
                    return  true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(headerBg, bgMatrix, new Paint());
        super.onDraw(canvas);
    }

    private void releaseToStart() {
        if (overHeight > 0) {
            isOverScrolling = false;
            bgMatrix.setScale(1, 1);
            scrollTo(0 , 0);
            invalidate();
        }
    }
}
