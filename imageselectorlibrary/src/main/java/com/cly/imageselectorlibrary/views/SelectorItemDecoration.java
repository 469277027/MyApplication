package com.cly.imageselectorlibrary.views;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by 丛龙宇 on 17-2-15.
 */

public class SelectorItemDecoration extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = 1;
        outRect.right = 1;
        outRect.bottom = 1;
        outRect.top = 1;
    }
}
