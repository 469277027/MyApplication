package com.cly.myapplication.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * Created by 丛龙宇 on 17-2-10.
 */

public class ActivityUtils {

    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int resId) {
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment fragment1 : fragments) fragmentManager.beginTransaction().hide(fragment1).commit();
        }
        Fragment fragmentByTag = fragmentManager.findFragmentByTag(fragment.getClass().getSimpleName());
        if (fragmentByTag == null) {
            fragmentManager.beginTransaction().add(resId, fragment, fragment.getClass().getSimpleName()).commit();
        } else {
            fragmentManager.beginTransaction().show(fragmentByTag).commit();
        }
    }
}
