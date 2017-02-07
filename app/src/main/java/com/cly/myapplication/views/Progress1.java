package com.cly.myapplication.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.cly.myapplication.R;

/**
 * Created by 丛龙宇 on 17-1-13.
 */

public class Progress1 extends View {
    private static final String TAG = "Progress1";

    //小圆半径
    private float mSmallRedius = 30;

    //大圆半径
    private float mBigRedius = 0;

    //中圆半径
    private float mMiddleRedius = mBigRedius / 12;

    //当前动画状态
    private SimpleState mState;

    //背景颜色
    private int bgColor = Color.parseColor("#ff4466");

    //动画总时长
    private long mDuration = 1600;

    //画小圆的画笔
    private Paint mPaint;

    //每个小圆的角度


    //当前旋转角度
    private float mAngle = ((float) (Math.PI * 2 / 6));
    //小圆颜色数组
    private int[] colorArray;

    //中心点横坐标
    private int centerX;
    //中心点纵坐标
    private int centerY;

    //动画
    private ValueAnimator animator;

    //第一个动画是否完毕
    private boolean isFirstFinish = false;

    //画笔粗细
    private float paintWidth;

    //责任链模式
    private abstract class SimpleState {
        abstract void drawCanvas(Canvas canvas);
    }

    //第一个动画
    private class FirstState extends SimpleState {


        public FirstState() {
            animator = ObjectAnimator.ofFloat(((float) (Math.PI * 2)), 0);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mAngle = ((float) animation.getAnimatedValue());
                    postInvalidate();
                    Log.d(TAG, "mAngle = " + mAngle);
                }
            });
            animator.setDuration(mDuration);
            animator.setRepeatCount(ObjectAnimator.INFINITE);
            animator.setInterpolator(new LinearInterpolator());
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationCancel(Animator animation) {
                    super.onAnimationCancel(animation);
                    mState = new SecondState();
                }
            });
            animator.start();
        }

        @Override
        void drawCanvas(Canvas canvas) {

            //画背景(擦黑板)
            drawBackground(canvas);

            //画圆
            drawCircles(canvas);


        }

        private void drawCircles(Canvas canvas) {
            for (int i = 0; i < colorArray.length; i++) {
                double curAngle = ((float) (Math.PI * 2 / 6)) * (i + 1);
                curAngle += mAngle;
                float cx = ((float) (mBigRedius / 12 * Math.cos(((double) (curAngle)))));
                float cy = ((float) (mBigRedius / 12 * Math.sin(((double) (curAngle)))));
                mPaint.setColor(colorArray[i]);
                canvas.drawCircle(cx, cy, mSmallRedius, mPaint);
                Log.d(TAG, "--> drawCircle :" + "cx = " + cx + ",cy = " + cy + ",mSmallRedius = " + mSmallRedius + ",mBigRedius = " + mBigRedius + ",mAngle = " + mAngle);

            }
        }

        private void drawBackground(Canvas canvas) {
            Log.d(TAG, "--> drawBackGround");
            canvas.drawColor(Color.WHITE);
        }


    }

    //第二个动画
    private class SecondState extends SimpleState {

        public SecondState() {
            animator = ObjectAnimator.ofFloat(0, mBigRedius / 12);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mMiddleRedius = ((float) animation.getAnimatedValue());
                    postInvalidate();
                    Log.d(TAG, "mAngle = " + mAngle);
                }
            });
            animator.setDuration(1000);
//            animator.setRepeatCount(ObjectAnimator.INFINITE);
            animator.setInterpolator(new OvershootInterpolator(10f));
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mState = new ThirdState();
                }
            });
            animator.reverse();
        }

        @Override
        void drawCanvas(Canvas canvas) {

            //画背景(擦黑板)
            drawBackground(canvas);

            //画圆
            drawCircles(canvas);
        }

        private void drawCircles(Canvas canvas) {
            for (int i = 0; i < colorArray.length; i++) {
                double curAngle = ((float) (Math.PI * 2 / 6)) * (i + 1);
                curAngle += mAngle;
                float cx = ((float) (mMiddleRedius * Math.cos(((double) (curAngle)))));
                float cy = ((float) (mMiddleRedius * Math.sin(((double) (curAngle)))));
                mPaint.setColor(colorArray[i]);
                canvas.drawCircle(cx, cy, mSmallRedius, mPaint);
                Log.d(TAG, "--> drawCircle :" + "cx = " + cx + ",cy = " + cy + ",mSmallRedius = " + mSmallRedius + ",mBigRedius = " + mBigRedius + ",mAngle = " + mAngle);

            }
        }

        private void drawBackground(Canvas canvas) {
            Log.d(TAG, "--> drawBackGround");
            canvas.drawColor(Color.WHITE);
        }

    }

    //第三个动画
    private class ThirdState extends SimpleState {

        public ThirdState() {
            animator = ObjectAnimator.ofFloat(0, mBigRedius / 2);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    paintWidth = ((float) animation.getAnimatedValue());
                    postInvalidate();
                    Log.d(TAG, "paintWidth = " + paintWidth);
                }
            });
            animator.setDuration(500);
//            animator.setRepeatCount(ObjectAnimator.INFINITE);
            animator.setInterpolator(new AccelerateInterpolator());
            animator.reverse();
        }

        @Override
        void drawCanvas(Canvas canvas) {

            //画圆
            drawCircles(canvas);

        }

        private void drawCircles(Canvas canvas) {
            mPaint.reset();
            mPaint.setColor(Color.parseColor("#000000"));
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(paintWidth);
            canvas.drawCircle(0, 0, mBigRedius / 2 / 2 + (mBigRedius / 2 - paintWidth), mPaint);
        }


    }


    public Progress1(Context context) {
        super(context);
        init();
    }

    private void init() {
        colorArray = getResources().getIntArray(R.array.androidcolors);
        mPaint = new Paint();
    }

    public Progress1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Progress1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBigRedius = ((float) Math.sqrt(((double) (w * w + h * h))));
        centerX = w / 2;
        centerY = h / 2;
        Log.d(TAG, "w = " + w + ",h = " + h + ",centerX = " + centerX + ",centerY = " + centerY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(centerX, centerY);
        if (mState == null) {
            mState = new FirstState();
        } else {
            mState.drawCanvas(canvas);
        }

        if (isFirstFinish && mState instanceof FirstState) {
            animator.cancel();
        }

        super.onDraw(canvas);

    }

    public void stopFirst() {
        isFirstFinish = true;
        postInvalidate();
    }
}
