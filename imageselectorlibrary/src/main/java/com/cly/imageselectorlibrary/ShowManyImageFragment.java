package com.cly.imageselectorlibrary;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.SparseArray;

import com.cly.imageselectorlibrary.bean.ImageInfo;
import com.cly.imageselectorlibrary.bean.ImageViewInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 丛龙宇 on 17-3-3.
 */

public class ShowManyImageFragment extends DialogFragment {

    private ViewPager viewPager;

    private static final String IMAGEVIEWINFO_KEY = "IMAGEVIEWINFO_KEY";
    private static final String IMAGE_LIST_KEY = "IMAGE_LIST_KEY";
    private ImageViewInfo imageViewInfo;
    private ArrayList<ImageInfo> imageInfos;

    public static ShowManyImageFragment newInstance(ImageViewInfo imageViewInfo, List<ImageInfo> imageInfos) {

        Bundle args = new Bundle();
        args.putParcelable(IMAGEVIEWINFO_KEY, imageViewInfo);
        args.putParcelableArrayList(IMAGE_LIST_KEY, ((ArrayList) imageInfos));
        ShowManyImageFragment fragment = new ShowManyImageFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parcelable parcelable = getArguments().getParcelable(IMAGE_LIST_KEY);
        imageViewInfo = ((ImageViewInfo) parcelable);
        imageInfos = ((ArrayList) getArguments().getParcelableArrayList(IMAGE_LIST_KEY));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        viewPager = new ViewPager(getContext());
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen)
                .setView(viewPager)
                .create();
        return alertDialog;
    }


}
