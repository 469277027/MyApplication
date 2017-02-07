package com.cly.myapplication.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by 丛龙宇 on 17-1-13.
 */

public class Progress2 extends View {

    //外圆缩放时间
    private static final int scaleDuration = 500;
    private static final String TAG = "Progress2";

    //外圆画笔
    private Paint outPaint;

    //内圆画笔
    private Paint inPaint;

    //中心点
    private float[] centerPoint = new float[2];

    //外圆半径
    private float outRadius = 80;
    //内圆半径
    private float inRadius = 75;

    //外圆当前半径
    private float ourCurRadius;

    //当前状态
    private AnimState mState;

    //动画
    private ValueAnimator animator;

    //弧线起始角度
    private float mStartAngle = -90;
    //弧线终止角度
    private float mStopAngle = mStartAngle + 120;

    //当前起始角度
    private float mCurStartAngle;

    //责任链模式
    private abstract class AnimState {
        abstract void drawCanvas(Canvas canvas);
    }


    //第一个动画
    private class FirstState extends AnimState {

        public FirstState() {
            animator = ObjectAnimator.ofFloat(0, outRadius);
            animator.setDuration(scaleDuration);
            animator.setInterpolator(new LinearInterpolator());
            animator.start();

            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    ourCurRadius = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (mState != null && mState instanceof FirstState) {
                        mState = new SecondState();
                        postInvalidate();
                    }
                }
            });

        }

        @Override
        void drawCanvas(Canvas canvas) {

            //画外圆
            drawOutCircle(canvas);

        }


        private void drawOutCircle(Canvas canvas) {
            canvas.drawCircle(0, 0, ourCurRadius, outPaint);
        }


    }

    //第二个动画
    private class SecondState extends AnimState {

        public SecondState() {

            animator = ObjectAnimator.ofFloat(-180, 180);
            animator.setDuration(1000);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setInterpolator(new LinearInterpolator());
            animator.start();
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mCurStartAngle = ((float) animation.getAnimatedValue());
                    Log.d(TAG, "mCurStartAngle = " + mCurStartAngle);
                    postInvalidate();
                }
            });

        }

        @Override
        void drawCanvas(Canvas canvas) {

            //画外圆
            drawOutCircle(canvas);

            //画内圆
            drawInCircle(canvas);
        }

        private void drawInCircle(Canvas canvas) {
            RectF rectF = new RectF(-(inRadius / 2), -(inRadius / 2), inRadius / 2, inRadius / 2);
            canvas.drawArc(rectF, mCurStartAngle, mCurStartAngle + 120, false, inPaint);
            Log.d(TAG, "mCurStartAngle = " + mCurStartAngle + ",mCurEndAngle = " + (mCurStartAngle + 120));
        }

        private void drawOutCircle(Canvas canvas) {
            canvas.drawCircle(0, 0, ourCurRadius, outPaint);
        }
    }


    public Progress2(Context context) {
        super(context);
        init();
    }

    public Progress2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Progress2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        outPaint = new Paint();
        outPaint.setColor(Color.WHITE);

        inPaint = new Paint();
        inPaint.setColor(Color.BLUE);
        inPaint.setStyle(Paint.Style.STROKE);
        inPaint.setStrokeWidth(10);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerPoint[0] = w / 2;
        centerPoint[1] = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.translate(centerPoint[0], centerPoint[1]);

        if (mState == null) {
            mState = new FirstState();
        } else {
            mState.drawCanvas(canvas);
        }
        super.onDraw(canvas);
    }
}

