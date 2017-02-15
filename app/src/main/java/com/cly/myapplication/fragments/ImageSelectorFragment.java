package com.cly.myapplication.fragments;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cly.myapplication.R;
import com.cly.myapplication.adapter.ImageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageSelectorFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "ImageSelectorFragment";

    private View rootView;
    private RecyclerView recyclerView;

    private List<Uri> list = new ArrayList<>();
    private ImageAdapter adapter;


    public static ImageSelectorFragment newInstance() {

        Bundle args = new Bundle();

        ImageSelectorFragment fragment = new ImageSelectorFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public ImageSelectorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        Bundle bundle = new Bundle();
        getLoaderManager().initLoader(0, null, this);
        adapter = new ImageAdapter(getActivity(), list);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null)
            rootView = inflater.inflate(R.layout.fragment_image_selector, container, false);
        recyclerView = ((RecyclerView) rootView.findViewById(R.id.rv_image_selector));
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter);
    }

    static final String[] columns = {
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media._ID};

    static final String orderBy = MediaStore.Images.Media.DATE_ADDED;

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "--> onCreateLoader");

        CursorLoader cursorLoader = new CursorLoader(
                getActivity(),
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                columns,
                null,
                null,
                orderBy);
//        cursorLoader.setUri(uri);


        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "--> onLoadFinished");
        if (data != null) {
            while (data.moveToNext()) {
//                Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI.buildUpon().appendPath(data.getString(data.getColumnIndex(columns[0]))).build();
                String imagePath = data.getString(data.getColumnIndexOrThrow(columns[0]));
                Log.d(TAG, "--> onLoadFinished:imagePath = " + imagePath);
                list.add(Uri.parse(imagePath));
//                for (String s : columns)
//                    Log.d(TAG, "--> onLoadFinished:" + s + " = " + data.getString(data.getColumnIndex(s)));
            }
            Log.d(TAG, "--> onLoadFinished:list.size = " + list.size());
            recyclerView.setAdapter(new ImageAdapter(getActivity(), list));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(TAG, "--> onLoaderReset");
    }
}
