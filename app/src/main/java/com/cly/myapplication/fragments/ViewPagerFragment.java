package com.cly.myapplication.fragments;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cly.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerFragment extends Fragment implements ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener {

    private static final String TAG = "ViewPagerFragment";

    private View rootView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] tabTitles;
    private int[] indicatorColors;

    private boolean isFirstScroll = true;
    private boolean isChangeCur = false;
    private float firstOffset = -1f;


    public ViewPagerFragment() {
        // Required empty public constructor
    }

    public static ViewPagerFragment newInstance() {

        Bundle args = new Bundle();

        ViewPagerFragment fragment = new ViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        tabTitles = getActivity().getResources().getStringArray(R.array.tabTitles);
        indicatorColors = getActivity().getResources().getIntArray(R.array.indicatorColors);
        for (int i : indicatorColors) {
            Log.d(TAG, "color:binary = " + Integer.toBinaryString(i) + ",decimal = " + i);
//            int j = 0x00FF0000;
//            int k = i & j;
//            k = k >> 16;
//            Log.d(TAG, "color:r:binary = " + Integer.toBinaryString(k) + ",decimal = " + k);
            int r = (i & 0x00FF0000) >> 16;
            Log.d(TAG, "color:r:binary = " + Integer.toBinaryString(r) + ",decimal = " + r);
            int g = (i & 0x0000FF00) >> 8;
            Log.d(TAG, "color:g:binary = " + Integer.toBinaryString(g) + ",decimal = " + g);
            int b = i & 0x000000FF;
            Log.d(TAG, "color:b:binary = " + Integer.toBinaryString(b) + ",decimal = " + b);

        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_view_pager, container, false);
        tabLayout = ((TabLayout) rootView.findViewById(R.id.tablayout));
        tabLayout.setSelectedTabIndicatorColor(indicatorColors[0]);
        tabLayout.setTabTextColors(Color.parseColor("#7a7a7a"), indicatorColors[0]);
        tabLayout.addOnTabSelectedListener(this);
        viewPager = ((ViewPager) rootView.findViewById(R.id.viewpager));
        viewPager.addOnPageChangeListener(this);
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager.setAdapter(new TabAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        if (positionOffset != 0) {
            if (isFirstScroll) {
                firstOffset = positionOffset;
                isFirstScroll = false;
            } else {
                int indicatorColorOld = indicatorColors[isChangeCur ? position == viewPager.getCurrentItem() ? position + 1 : position : position == viewPager.getCurrentItem() ? position : position + 1];
                int oldR = getR(indicatorColorOld);
                int oldG = getG(indicatorColorOld);
                int oldB = getB(indicatorColorOld);
                Log.d(TAG, "--> onPageScrolled:indicatorColorOld = " + Integer.toBinaryString(indicatorColorOld));
                Log.d(TAG, "--> onPageScrolled:oldR = " + oldR + ",oldG = " + oldG + ",oldB = " + oldB);

                int indicatorColorNew = indicatorColors[isChangeCur ? position == viewPager.getCurrentItem() ? position : position + 1 : position == viewPager.getCurrentItem() ? position + 1 : position];
                int newR = getR(indicatorColorNew);
                int newG = getG(indicatorColorNew);
                int newB = getB(indicatorColorNew);
                Log.d(TAG, "--> onPageScrolled:indicatorColorNew = " + Integer.toBinaryString(indicatorColorNew));
                Log.d(TAG, "--> onPageScrolled:newR = " + newR + ",newG = " + newG + ",newB = " + newB);

                int rDifference = getColorDifference(oldR, newR);
                int gDifference = getColorDifference(oldG, newG);
                int bDifference = getColorDifference(oldB, newB);
                Log.d(TAG, "--> onPageScrolled:rDifference = " + rDifference + ",gDifference = " + gDifference + ",bDifference = " + bDifference);

                float vR1 = oldR - rDifference * (positionOffset > firstOffset ? positionOffset : (1 - positionOffset));
                float vR2 = oldR + rDifference * (positionOffset > firstOffset ? positionOffset : (1 - positionOffset));

                float vG1 = oldG - gDifference * (positionOffset > firstOffset ? positionOffset : (1 - positionOffset));
                float vG2 = oldG + gDifference * (positionOffset > firstOffset ? positionOffset : (1 - positionOffset));

                float vB1 = oldB - bDifference * (positionOffset > firstOffset ? positionOffset : (1 - positionOffset));
                float vB2 = oldB + bDifference * (positionOffset > firstOffset ? positionOffset : (1 - positionOffset));


                int argb = Color.argb(0xff,
                        oldR > newR ? ((int) vR1) : ((int) vR2),
                        oldG > newG ? ((int) vG1) : ((int) vG2),
                        oldB > newB ? ((int) vB1) : ((int) vB2));
                Log.d(TAG, "argb:binary = " + Integer.toBinaryString(argb));
                tabLayout.setSelectedTabIndicatorColor(argb);
                tabLayout.setTabTextColors(Color.parseColor("#7a7a7a"), argb);
            }

        }

        Log.d(TAG, "--> onPageScrolled:isChangeCur = " + isChangeCur);
        Log.d(TAG, "--> onPageScrolled:curPosition = " + viewPager.getCurrentItem());
        Log.d(TAG, "--> onPageScrolled:position = " + position + ",positionOffset = " + positionOffset + ",positionOffsetPixels = " + positionOffsetPixels);
    }

    private int getColorDifference(int oldColor, int newColor) {
        return Math.max(oldColor, newColor) - Math.min(oldColor, newColor);
    }

    private int getR(int argb) {
        return (argb & 0x00FF0000) >> 16;
    }

    private int getG(int argb) {
        return (argb & 0x0000FF00) >> 8;
    }

    private int getB(int argb) {
        return (argb & 0x000000FF);
    }

    private int getA(int argb) {
        return (argb & 0xFF000000) >> 24;
    }

    @Override
    public void onPageSelected(int position) {
//        tabLayout.setSelectedTabIndicatorColor(indicatorColors[position]);
        tabLayout.setTabTextColors(Color.parseColor("#7a7a7a"), indicatorColors[position]);
        isFirstScroll = false;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.d(TAG, "--> onPageScrollStateChanged:state = " + state);
        switch (state) {
            case 0:
                if (isChangeCur) {
                    isChangeCur = false;
                }
                break;
            case 1:
//                isChangeCur =
                break;
            case 2:
                if (!isChangeCur) {
                    isChangeCur = true;
                }
                break;
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    private class TabAdapter extends FragmentPagerAdapter {

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = getChildFragmentManager().findFragmentByTag(ViewPagerInFragment.class.getSimpleName() + position);
            if (fragment == null) {
                fragment = ViewPagerInFragment.newInstance(tabTitles[position]);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }
}
