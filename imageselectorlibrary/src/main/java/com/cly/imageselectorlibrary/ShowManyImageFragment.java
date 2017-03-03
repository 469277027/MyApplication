package com.cly.imageselectorlibrary;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;

/**
 * Created by 丛龙宇 on 17-3-3.
 */

public class ShowManyImageFragment extends DialogFragment {

    private ViewPager viewPager;

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
