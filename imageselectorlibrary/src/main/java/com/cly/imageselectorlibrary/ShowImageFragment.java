package com.cly.imageselectorlibrary;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cly.imageselectorlibrary.bean.ImageInfo;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowImageFragment extends DialogFragment {

    private static final String TAG = "ShowImageFragment";

    public static final String IMAGE_IMFO_KEY = "IMAGE_IMFO_KEY";

    private ViewPager viewPager;

    private ImageInfo imageInfo;
    private ImageView imageView;
    private int screenWidth;

    public ShowImageFragment() {
        // Required empty public constructor
    }

    public static ShowImageFragment newInstance(ImageInfo imageInfo) {

        Bundle args = new Bundle();
        args.putParcelable(IMAGE_IMFO_KEY, imageInfo);
        ShowImageFragment fragment = new ShowImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parcelable parcelable = getArguments().getParcelable(IMAGE_IMFO_KEY);
        WindowManager systemService = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        screenWidth = systemService.getDefaultDisplay().getWidth();
        if (parcelable != null) {
            imageInfo = ((ImageInfo) parcelable);

            Log.d(TAG, "--> onCreate:imageImfo = " + imageInfo.toString());
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen)
                .setView(initView())
                .create();

        return alertDialog;
    }

    private View initView() {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_show_image, null);
        imageView = ((ImageView) rootView.findViewById(R.id.iv_showImage));
        return rootView;
    }


    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
        imageView.setVisibility(View.VISIBLE);
        startAnimation(imageView);

    }

    private void startAnimation(final ImageView imageView) {
        int[] screenLocation = imageInfo.getScreenLocation();
        int width = imageInfo.getWidth();
        int height = imageInfo.getHeight();

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
        imageView.setLayoutParams(layoutParams);

        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(width, screenWidth)
                .setDuration(300);
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Object animatedValue = animation.getAnimatedValue();
                float animatedValue1 = (float) animatedValue;
                imageView.getLayoutParams().width = ((int) animatedValue1);

            }
        });
//                .;

//        AnimationSet set = new AnimationSet(true);

//        ScaleAnimation scaleAnimation = new ScaleAnimation()

    }

//    @Override
//    public int show(FragmentTransaction transaction, String tag) {
//        Window window = getDialog().getWindow();
//        WindowManager.LayoutParams attributes = window.getAttributes();
//        float alpha = attributes.alpha;
//        Log.d(TAG, "--> show:alpha = " + alpha);
//        return super.show(transaction, tag);
//    }
}
