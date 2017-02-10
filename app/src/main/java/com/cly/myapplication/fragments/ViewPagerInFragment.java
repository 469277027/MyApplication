package com.cly.myapplication.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cly.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerInFragment extends Fragment {

    private static final String KEY = "VIEWPAGER_IN_FRAGMENT_KEY";
    private String title;
    private View rootView;
    private TextView tvViewPagerIn;


    public ViewPagerInFragment() {
        // Required empty public constructor
    }

    public static ViewPagerInFragment newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(KEY, title);
        ViewPagerInFragment fragment = new ViewPagerInFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString(KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_view_pager_in, container, false);
        tvViewPagerIn = ((TextView) rootView.findViewById(R.id.tv_viewpager_in));
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvViewPagerIn.setText(title);
    }
}
