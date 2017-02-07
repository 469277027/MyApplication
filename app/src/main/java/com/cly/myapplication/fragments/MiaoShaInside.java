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
public class MiaoShaInside extends Fragment {

    private static final String KEY = "MIAOSHA_KEY";
    private String id;
    private View rootView;
    private TextView tvInsideMiaosha;

    public static MiaoShaInside newInstance(String id) {

        Bundle args = new Bundle();
        args.putString(KEY, id);
        MiaoShaInside fragment = new MiaoShaInside();
        fragment.setArguments(args);
        return fragment;
    }

    public MiaoShaInside() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getString(KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_miao_sha_inside, container, false);
        tvInsideMiaosha = ((TextView) rootView.findViewById(R.id.tv_inside_miaosha));
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvInsideMiaosha.setText(id);
    }
}
