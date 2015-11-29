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
        headerBg = BitmapFactory.decodeResource(getResources(), R.drawable.googlelogo);
        bgMatrix = new Matrix();
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        deltaY = ev.getY() - y;
        x = ev.getX();
        y = ev.getY();
        int action = ev.getActionMasked();
        if (action == MotionEvent.ACTION_MOVE){
            if (isTop()) {
                movedLength += deltaY;
                if (movedLength > 0) {
                    xScale = yScale = movedLength * 2 / getWidth() + 1;
                    bgMatrix.setScale(xScale, yScale, headerBg.getWidth() / 2, headerBg.getHeight() / 2);
                    scrollBy(0 , -(int) deltaY);
                    invalidate();
                    return true;
                } else {
                    bgMatrix.setScale(0, 0);
                    scrollTo(0 , 0);
                    invalidate();
                    return true;
                }

            } else {
                return super.onTouchEvent(ev);
            }
        }
        return super.onTouchEvent(ev);
    }

    private boolean isTop() {
        return getFirstVisiblePosition() == 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(headerBg, bgMatrix, new Paint());
        super.onDraw(canvas);
    }
}
