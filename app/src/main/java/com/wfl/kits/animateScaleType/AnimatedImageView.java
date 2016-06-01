package com.wfl.kits.animateScaleType;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import java.util.Arrays;

    /**
     * Custom ImageView that can animate to the target ScaleType
     *
     * Created by wfl on 16/5/31.
     *
     */
    public class AnimatedImageView extends ImageView {
        static final int ANIM_DURATION = 500;
        private Matrix srcMatrix;
        private Matrix destMatrix;
        private ValueAnimator mAnimator;
        /**
         * animated to
         */
        private ScaleType animatedScaleType;
        /**
         * the original ScaleType
         */
        private ScaleType originScaleType;
        private int mAnimDuration = ANIM_DURATION;
        private int mStartDelay = 0;
        boolean changed = false;
        boolean layouted = false;
        boolean isDelaying = false;

        boolean isAnimationOpen = true;


        public AnimatedImageView(Context context) {
            this(context, null);
        }

        public AnimatedImageView(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public AnimatedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);

            setAnimatedScaleType(ScaleType.CENTER_CROP);
            if (originScaleType == null) {
                originScaleType = getScaleType();
            }

            mAnimator = ValueAnimator.ofFloat(0f, 1f);
            mAnimator.setDuration(mAnimDuration);
            changeResource();
        }

        /**
         * Set the target ScaleType which the drawable animate to
         * @param animatedScaleType
         */
        public void setAnimatedScaleType(ScaleType animatedScaleType) {
            this.animatedScaleType = animatedScaleType;
        }

        /**
         * Duration of the animation
         * @param animDuration
         */
        public void setAnimDuration(int animDuration) {
            mAnimDuration = animDuration;
            mAnimator.setDuration(mAnimDuration);
        }

        /**
         * set the time delayed for the animation
         * @param startDelay The delay (in milliseconds) before the animation
         */
        public void setStartDelay(int startDelay) {
            mStartDelay = startDelay;
        }

        /**
         * manual
         */
        public void manualAnimate() {
            if (mAnimator != null && mAnimator.isRunning()) {
                return;
            }
            changeResource();
        }

        public void enableAnimation() {
            isAnimationOpen = true;
        }

        public void disableAnimation() {
            isAnimationOpen = false;
        }

        @Override
        public void setScaleType(ScaleType scaleType) {
            super.setScaleType(scaleType);
            originScaleType = scaleType;
        }

        @Override
        public void setImageDrawable(Drawable drawable) {
            super.setImageDrawable(drawable);
            changeResource();
        }

        @Override
        public void setImageBitmap(Bitmap bm) {
            super.setImageBitmap(bm);
            changeResource();
        }

        @Override
        public void setImageResource(int resId) {
            super.setImageResource(resId);
            changeResource();
        }

        @Override
        public void setImageURI(Uri uri) {
            super.setImageURI(uri);
            changeResource();
        }

        /**
         * drawable changed
         */
        private void changeResource() {
            changed = true;
            if (mAnimator != null) {
                mAnimator.cancel();
                mAnimator.removeAllUpdateListeners();
            }
            if (isAnimationOpen && layouted) {
                scheduleAnimation();
            }
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);
            layouted = true;
            if (isAnimationOpen && changed) {
                changed = false;
                scheduleAnimation();
            }
        }

        private void scheduleAnimation() {
            if (isDelaying) {
                removeCallbacks(delayRunnable);
                isDelaying = false;
            }
            if (mStartDelay == 0) {
                animateToScaleType(animatedScaleType);
            } else {
                super.setScaleType(originScaleType);
                postDelayed(delayRunnable, mStartDelay);
                isDelaying = true;
            }
        }

        Runnable delayRunnable = new Runnable() {
            @Override
            public void run() {
                animateToScaleType(animatedScaleType);
                isDelaying = false;
            }
        };

        /**
         * animate to target scaleType
         *
         * @param scaleType
         */
        private void animateToScaleType(ScaleType scaleType) {
            // Use super to avoid set originScaleType;
            super.setScaleType(originScaleType);
            setFrame(getLeft(), getTop(), getRight(), getBottom());    // configureBounds
            srcMatrix = getImageMatrix();
            final float[] srcValues = new float[9], destValues = new float[9];
            srcMatrix.getValues(srcValues);

            super.setScaleType(scaleType);
            setFrame(getLeft(), getTop(), getRight(), getBottom());
            destMatrix = getImageMatrix();
            if (scaleType == ScaleType.FIT_XY) {
                float sX = ((float)getWidth()) / getDrawable().getIntrinsicWidth();
                float sY = ((float)getHeight()) / getDrawable().getIntrinsicHeight();
                destMatrix.postScale(sX, sY);
            }
            destMatrix.getValues(destValues);

    //        Log.v("Animated", "old: " + Arrays.toString(srcValues));
    //        Log.v("Animated", "new: " + Arrays.toString(destValues));

            final float transX = destValues[2] - srcValues[2];
            final float transY = destValues[5] - srcValues[5];
            final float scaleX = destValues[0] - srcValues[0];
            final float scaleY = destValues[4] - srcValues[4];
    //        final float scale = destValues[8] - srcValues[8];

            super.setScaleType(ScaleType.MATRIX);


            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float value = animation.getAnimatedFraction();
                            float[] currValues = Arrays.copyOf(srcValues, srcValues.length);
                            currValues[2] = srcValues[2] + transX * value;
                            currValues[5] = srcValues[5] + transY * value;
                            currValues[0] = srcValues[0] + scaleX * value;
                            currValues[4] = srcValues[4] + scaleY * value;
    //                        currValues[8] = srcValues[8] + scale * value;
                            Matrix matrix = new Matrix();
                            matrix.setValues(currValues);
    //                        Log.v("Animated", "values: " + Arrays.toString(currValues));
                            setImageMatrix(matrix);
                        }
                    });
            mAnimator.start();
        }
    }
