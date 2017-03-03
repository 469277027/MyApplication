package com.cly.imageselectorlibrary.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.cly.imageselectorlibrary.bean.ImageInfo;
import com.cly.imageselectorlibrary.bean.ImageViewInfo;

import java.io.File;

/**
 * Created by 丛龙宇 on 17-3-3.
 */

public class ShowImageAnimHelper {

    //动画持续时间
    public static final int ANIM_DURATION = 500;
    private static final String TAG = "ShowImageAnimHelper";
    //屏幕宽度
    private final int screenWidth;
    //屏幕宽度
    private final int screenHeight;

    //执行动画的View
    private View mView;

    //View的位置信息
    private ImageViewInfo mImageViewInfo;

    //动画的两种状态
    public static enum AnimState {
        IN_STATE,
        OUT_STATE
    }

    public ShowImageAnimHelper(View view) {
        this.mView = view;
        WindowManager systemService = (WindowManager) mView.getContext().getSystemService(Context.WINDOW_SERVICE);
        screenWidth = systemService.getDefaultDisplay().getWidth();
        screenHeight = systemService.getDefaultDisplay().getHeight();
    }

    //设置必要的位置信息
    public void setImageViewInfo(ImageViewInfo imageViewInfo) {
        mImageViewInfo = imageViewInfo;
    }

    //开始动画
    public void startAnim(AnimState animState) {
        if (mImageViewInfo == null)
            return;

        if (animState == AnimState.IN_STATE) {
            inAnim();
        } else {
            outAnim();
        }
    }


    //View进入的动画
    private void inAnim() {
        int[] screenLocation = mImageViewInfo.getScreenLocation();
        int width = mImageViewInfo.getWidth();
        final int height = mImageViewInfo.getHeight();

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
        mView.setLayoutParams(layoutParams);

        //使用属性动画不断改变View的左边距
        ValueAnimator leftMarginAnim = ObjectAnimator.ofFloat(screenLocation[0], 0);
        leftMarginAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Object animatedValue = animation.getAnimatedValue();
                float animatedValue1 = (float) animatedValue;
                ((RelativeLayout.LayoutParams) mView.getLayoutParams()).leftMargin = (int) animatedValue1;
                Log.d(TAG, "--> startInAnimation:marginLeft = " + animatedValue1);
                mView.requestLayout();
            }
        });

        //使用属性动画不断改变View的上边距
        ValueAnimator topMarginAnim = ObjectAnimator.ofFloat(screenLocation[1], 0);
        topMarginAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Object animatedValue = animation.getAnimatedValue();
                float animatedValue1 = (float) animatedValue;
                ((RelativeLayout.LayoutParams) mView.getLayoutParams()).topMargin = (int) animatedValue1;
                mView.requestLayout();
            }
        });


        //使用属性动画不断改变View的宽度
        ValueAnimator widthAnim = ObjectAnimator.ofFloat(width, screenWidth);
        widthAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Object animatedValue = animation.getAnimatedValue();
                float animatedValue1 = (float) animatedValue;
                mView.getLayoutParams().width = ((int) animatedValue1);
                mView.requestLayout();

            }
        });
//                .;

        //使用属性动画不断改变View的高度
        ValueAnimator heightAnim = ObjectAnimator.ofFloat(height, screenHeight);
        heightAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Object animatedValue = animation.getAnimatedValue();
                float animatedValue1 = (float) animatedValue;
                mView.getLayoutParams().height = ((int) animatedValue1);
                mView.requestLayout();
            }
        });

        AnimatorSet set = new AnimatorSet();
        set.play(heightAnim).with(widthAnim).with(topMarginAnim).with(leftMarginAnim);
        set.setDuration(ANIM_DURATION);
        set.start();
    }


    //View退出的动画
    private void outAnim() {
        int[] screenLocation = mImageViewInfo.getScreenLocation();
        int width = mImageViewInfo.getWidth();
        final int height = mImageViewInfo.getHeight();

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
        mView.setLayoutParams(layoutParams);

        //使用属性动画不断改变View的左边距
        ValueAnimator leftMarginAnim = ObjectAnimator.ofFloat(0, screenLocation[0]);
        leftMarginAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Object animatedValue = animation.getAnimatedValue();
                float animatedValue1 = (float) animatedValue;
                ((RelativeLayout.LayoutParams) mView.getLayoutParams()).leftMargin = (int) animatedValue1;
                Log.d(TAG, "--> startInAnimation:marginLeft = " + animatedValue1);
                mView.requestLayout();
            }
        });

        //使用属性动画不断改变View的上边距
        ValueAnimator topMarginAnim = ObjectAnimator.ofFloat(0, screenLocation[1]);
        topMarginAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Object animatedValue = animation.getAnimatedValue();
                float animatedValue1 = (float) animatedValue;
                ((RelativeLayout.LayoutParams) mView.getLayoutParams()).topMargin = (int) animatedValue1;
                mView.requestLayout();
            }
        });


        //使用属性动画不断改变View的宽度
        ValueAnimator widthAnim = ObjectAnimator.ofFloat(screenWidth, width);
        widthAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Object animatedValue = animation.getAnimatedValue();
                float animatedValue1 = (float) animatedValue;
                mView.getLayoutParams().width = ((int) animatedValue1);
                mView.requestLayout();

            }
        });
//                .;

        //使用属性动画不断改变View的高度
        ValueAnimator heightAnim = ObjectAnimator.ofFloat(screenHeight, height);
        heightAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Object animatedValue = animation.getAnimatedValue();
                float animatedValue1 = (float) animatedValue;
                mView.getLayoutParams().height = ((int) animatedValue1);
                mView.requestLayout();
            }
        });

        AnimatorSet set = new AnimatorSet();
        set.play(heightAnim).with(widthAnim).with(topMarginAnim).with(leftMarginAnim);
        set.setDuration(ANIM_DURATION);
        set.start();
    }


    //进入动画监听
    private class InAnimListener extends AnimatorListenerAdapter{
        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
        }
    }

    //退出动画监听
    private class OutAnimListener extends AnimatorListenerAdapter{
        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
        }
    }


}
