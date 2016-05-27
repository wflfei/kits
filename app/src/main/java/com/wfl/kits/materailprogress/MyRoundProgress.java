package com.wfl.kits.materailprogress;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.wfl.kits.commons.utils.Logger;

/**
 * Created by wfl on 16/3/16.
 */
public class MyRoundProgress extends ImageView {
    private static final String TAG = Logger.makeLogTag(MyRoundProgress.class);
    private static final int ANIM_DURATION = 1000;

    private static final int MAX_SWEEP = 270;
    private static final float START_STEP = 10;
    private static final float SWEEP_STEP = 7.5f;
    private Paint mPaint;
    private int strokeWidth;
    private RectF drawArc;
    private float startAngle, sweepAngle, step, endAngle;
    private boolean adding = true;
    private boolean started = false;


    MaterialProgressDrawable materialProgressDrawable;
    private int[] colors = {
            0xFFFF0000/*,0xFFFF7F00,0xFFFFFF00,0xFF00FF00
            ,0xFF00FFFF,0xFF0000FF,0xFF8B00FF*/};

    private final int CIRCLE_BG_LIGHT = 0xFFFAFAFA;


    public MyRoundProgress(Context context) {
        super(context);
        init(null, 0);
    }

    public MyRoundProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MyRoundProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyRoundProgress(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {
//        strokeWidth = DisplayUtil.dp2px(getContext(), 3);
//        mPaint = new Paint();
//        mPaint.setStrokeWidth(strokeWidth);
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setColor(Color.RED);
//        step = 0;
//        startAngle = 0;
//        sweepAngle = 0;
//        endAngle = 0;
        materialProgressDrawable = new MaterialProgressDrawable(getContext(), this);
        materialProgressDrawable.updateSizes(MaterialProgressDrawable.LARGE);
        materialProgressDrawable.setBackgroundColor(CIRCLE_BG_LIGHT);
        materialProgressDrawable.setColorSchemeColors(colors);
        materialProgressDrawable.showArrow(false);
        materialProgressDrawable.setAlpha(255);
        setImageDrawable(materialProgressDrawable);
    }

    /*@Override
    protected void onDraw(Canvas canvas) {
        initDrawRectIfNeed();
        Logger.v(TAG, "onDraw: startAngle=" + startAngle + " sweep=" + sweepAngle + " rect=" + drawArc.right + " " + drawArc.bottom);
        canvas.drawArc(drawArc, startAngle, sweepAngle, false, mPaint);
    }
*/

    private void initDrawRectIfNeed() {
        if (null == drawArc) {

            float left, right, top, bottom;
            float s = strokeWidth / 2f;
            left = 0 + s;
            top = 0 + s;
            right = getWidth() - s;
            bottom = getHeight() - s;
            drawArc = new RectF(left, top, right, bottom);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getVisibility() == View.VISIBLE) {
            Logger.v(TAG, "onAttachedToWindow");
            start();
        }
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == View.VISIBLE) {
            Logger.v(TAG, "onVisibility: Visible ");
            start();
        } else {
            Logger.v(TAG, "onVisibility: InVisible");
            stop();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    /*public void start() {
        if (!started) {
            started = true;
            postDelayed(mRunnable, 5);
        }
    }

    public void stop() {
        removeCallbacks(mRunnable);
    }*/

    public void start() {
        if (!materialProgressDrawable.isRunning()) {
            started = true;
            materialProgressDrawable.start();
        }
    }

    public void stop() {
        materialProgressDrawable.stop();
    }

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            step += START_STEP;
            step %= 360;
            startAngle = step;
            if (adding) {
                if (sweepAngle < MAX_SWEEP) {
                    sweepAngle = sweepAngle + SWEEP_STEP;
                } else if (sweepAngle >= MAX_SWEEP) {
                    sweepAngle -= SWEEP_STEP;
                    adding = false;
                }
            } else {
                if (sweepAngle <= 0) {
                    sweepAngle += SWEEP_STEP;
                    adding = true;
                } else {
                    sweepAngle -= SWEEP_STEP;
                }
            }
//            float startStep = (step + 270) % 360;
//            startAngle = (float) startOfStep(step);
//            endAngle = (float) startOfStep(startStep);
//            sweepAngle = (float) sweepOfStart(startStep);
//            sweepAngle = (float) calSweep(startAngle, endAngle);
            postInvalidate();
            postDelayed(this, 8);
        }
    };

    private double startOfStep(float step) {
        if (step < 0 || step > 360) {
            return 0;
        }
        double t = Math.PI / 360d;
        return Math.sin(step * t -  Math.PI / 2) * 180 + 180;

    }

    private double sweepOfStart(float start) {
        if (start < 0 || start > 360) {
            return 0;
        }
        double t = 270d / (180d * 180d);
        double y = (270f - t * (start - 180f) * (start - 180f));
        return Math.abs(y);
    }

    private double calSweep(float start, float end) {
        if (end < start) {
            return end + 360 - start;
        } else {
            return  end - start;
        }
    }

}
