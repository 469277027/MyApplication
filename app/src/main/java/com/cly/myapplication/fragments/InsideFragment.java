package com.cly.myapplication.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cly.myapplication.R;
import com.cly.myapplication.views.Progress1;

/**
 * A simple {@link Fragment} subclass.
 */
public class InsideFragment extends Fragment {


    private View rootView;
    private Progress1 progress1;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progress1.stopFirst();
        }
    };

    public static InsideFragment newInstance() {

        Bundle args = new Bundle();

        InsideFragment fragment = new InsideFragment();
        fragment.setArguments(args);
        return fragment;
    }



    public InsideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_inside, container, false);
        progress1 = ((Progress1) rootView.findViewById(R.id.progress1));
        handler.sendEmptyMessageDelayed(0, 2000);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
