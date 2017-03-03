package com.cly.imageselectorlibrary;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.cly.imageselectorlibrary.bean.ImageInfo;
import com.cly.imageselectorlibrary.bean.ImageViewInfo;
import com.cly.imageselectorlibrary.utils.ShowImageAnimHelper;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowImageFragment extends DialogFragment {

    private static final String TAG = "ShowImageFragment";

    public static final String IMAGEVIEW_IMFO_KEY = "IMAGEVIEW_IMFO_KEY";
    public static final String IMAGE_INFO_KEY = "IMAGE_INFO_KEY";

    private static final long ANIM_DURATION = 500;

    private ViewPager viewPager;

    private ImageViewInfo imageViewInfo;
    private ImageView imageView;
    private int screenWidth;
    private ImageInfo imageInfo;
    private int screenHeight;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dismiss();
        }
    };
    private ShowImageAnimHelper animHelper;


    public ShowImageFragment() {
        // Required empty public constructor
    }

    public static ShowImageFragment newInstance(ImageViewInfo imageViewInfo, ImageInfo imageInfo) {

        Bundle args = new Bundle();
        args.putParcelable(IMAGEVIEW_IMFO_KEY, imageViewInfo);
        args.putParcelable(IMAGE_INFO_KEY, imageInfo);
        ShowImageFragment fragment = new ShowImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parcelable parcelable = getArguments().getParcelable(IMAGEVIEW_IMFO_KEY);
        Parcelable parcelable1 = getArguments().getParcelable(IMAGE_INFO_KEY);
        WindowManager systemService = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        screenWidth = systemService.getDefaultDisplay().getWidth();
        screenHeight = systemService.getDefaultDisplay().getHeight();
        if (parcelable != null && parcelable1 != null) {
            imageViewInfo = ((ImageViewInfo) parcelable);
            imageInfo = ((ImageInfo) parcelable1);
            Log.d(TAG, "--> onCreate:imageViewImfo = " + imageViewInfo.toString());
            Log.d(TAG, "--> onCreate:imageInfo = " + imageInfo.toString());
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen)
                .setView(initView())
                .create();
        Window window = alertDialog.getWindow();
//        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Log.d(TAG, "--> onCreateDialog");
        return alertDialog;
    }

    private View initView() {
        Log.d(TAG, "--> initView");
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_show_image, null);
        imageView = ((ImageView) rootView.findViewById(R.id.iv_showImage));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startOutAnim();
                animHelper.startAnim(ShowImageAnimHelper.AnimState.OUT_STATE);
                mHandler.sendEmptyMessageDelayed(0, ANIM_DURATION);
            }
        });
        animHelper = new ShowImageAnimHelper(imageView);
        animHelper.setImageViewInfo(imageViewInfo);
        Glide.with(getActivity())
                .load(new File(imageInfo.getUri().toString()))
                .into(imageView);

        return rootView;
    }


    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
        Log.d(TAG, "--> show");

    }

    @Override
    public void onResume() {
        super.onResume();
        imageView.setVisibility(View.VISIBLE);
        animHelper.startAnim(ShowImageAnimHelper.AnimState.IN_STATE);
//        startInAnimation(imageView);
        Log.d(TAG, "--> onResume");
    }


//    private void startOutAnim() {
//        int[] screenLocation = imageViewInfo.getScreenLocation();
//        int width = imageViewInfo.getWidth();
//        final int height = imageViewInfo.getHeight();
//
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
//        imageView.setLayoutParams(layoutParams);
//
//
//        ValueAnimator valueAnimator2 = ObjectAnimator.ofFloat(0, screenLocation[0]).setDuration(ANIM_DURATION);
//        valueAnimator2.start();
//        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                Object animatedValue = animation.getAnimatedValue();
//                float animatedValue1 = (float) animatedValue;
//                ((RelativeLayout.LayoutParams) imageView.getLayoutParams()).leftMargin = (int) animatedValue1;
//                Log.d(TAG, "--> startInAnimation:marginLeft = " + animatedValue1);
//                imageView.requestLayout();
//            }
//        });
//
//        valueAnimator2.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//            }
//        });
//
//
//        ValueAnimator valueAnimator3 = ObjectAnimator.ofFloat(0, screenLocation[1]).setDuration(ANIM_DURATION);
//        valueAnimator3.start();
//        valueAnimator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                Object animatedValue = animation.getAnimatedValue();
//                float animatedValue1 = (float) animatedValue;
//                ((RelativeLayout.LayoutParams) imageView.getLayoutParams()).topMargin = (int) animatedValue1;
//                imageView.requestLayout();
//            }
//        });
//
//
//        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(screenWidth, width)
//                .setDuration(ANIM_DURATION);
//        valueAnimator.start();
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                Object animatedValue = animation.getAnimatedValue();
//                float animatedValue1 = (float) animatedValue;
//                imageView.getLayoutParams().width = ((int) animatedValue1);
//                imageView.requestLayout();
//
//            }
//        });
//
//        ValueAnimator valueAnimator1 = ObjectAnimator.ofFloat(screenHeight, height).setDuration(ANIM_DURATION);
//        valueAnimator1.start();
//        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                Object animatedValue = animation.getAnimatedValue();
//                float animatedValue1 = (float) animatedValue;
//                imageView.getLayoutParams().height = ((int) animatedValue1);
//                imageView.requestLayout();
//            }
//        });
//    }
//
//    private void startInAnimation(final ImageView imageView) {
//        int[] screenLocation = imageViewInfo.getScreenLocation();
//        int width = imageViewInfo.getWidth();
//        Glide.with(getActivity())
//                .load(new File(imageInfo.getUri().toString()))
//                .into(imageView);
//        final int height = imageViewInfo.getHeight();
//
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
//        imageView.setLayoutParams(layoutParams);
//
//
//        ValueAnimator valueAnimator2 = ObjectAnimator.ofFloat(screenLocation[0], 0).setDuration(ANIM_DURATION);
//        valueAnimator2.start();
//        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                Object animatedValue = animation.getAnimatedValue();
//                float animatedValue1 = (float) animatedValue;
//                ((RelativeLayout.LayoutParams) imageView.getLayoutParams()).leftMargin = (int) animatedValue1;
//                Log.d(TAG, "--> startInAnimation:marginLeft = " + animatedValue1);
//                imageView.requestLayout();
//            }
//        });
//
//
//        ValueAnimator valueAnimator3 = ObjectAnimator.ofFloat(screenLocation[1], 0).setDuration(ANIM_DURATION);
//        valueAnimator3.start();
//        valueAnimator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                Object animatedValue = animation.getAnimatedValue();
//                float animatedValue1 = (float) animatedValue;
//                ((RelativeLayout.LayoutParams) imageView.getLayoutParams()).topMargin = (int) animatedValue1;
//                imageView.requestLayout();
//            }
//        });
//
//
//        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(width, screenWidth)
//                .setDuration(ANIM_DURATION);
//        valueAnimator.start();
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                Object animatedValue = animation.getAnimatedValue();
//                float animatedValue1 = (float) animatedValue;
//                imageView.getLayoutParams().width = ((int) animatedValue1);
//                imageView.requestLayout();
//
//            }
//        });
////                .;
//
//        ValueAnimator valueAnimator1 = ObjectAnimator.ofFloat(height, screenHeight).setDuration(ANIM_DURATION);
//        valueAnimator1.start();
//        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                Object animatedValue = animation.getAnimatedValue();
//                float animatedValue1 = (float) animatedValue;
//                imageView.getLayoutParams().height = ((int) animatedValue1);
//                imageView.requestLayout();
//            }
//        });


//    }

}
