package com.cly.myapplication.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cly.myapplication.R;
import com.cly.myapplication.views.MyRecyclerVew;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MiaoShaFragment extends Fragment {


    private View rootView;
    private ViewPager viewPager;
    private MyRecyclerVew myRecyclerVew;
    private List<Integer> list = new ArrayList<>();

    public static MiaoShaFragment newInstance() {

        Bundle args = new Bundle();

        MiaoShaFragment fragment = new MiaoShaFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public MiaoShaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_miao_sha, container, false);
        viewPager = ((ViewPager) rootView.findViewById(R.id.vp_miaosha));
        myRecyclerVew = ((MyRecyclerVew) rootView.findViewById(R.id.myrecyclerview));
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        myRecyclerVew.setViewPager(viewPager);
    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter {


        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return MiaoShaInside.newInstance(String.valueOf(position));
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return String.valueOf(list.get(position));
        }
    }

}
