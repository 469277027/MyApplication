package com.cly.myapplication.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 丛龙宇 on 17-2-6.
 */

public class MyRecyclerVew extends RecyclerView {

    private static final String TAG = "MyHorizontalScrollView";

    private static final boolean IS_DEBUG = true;

    //组合的ViewPager
    private ViewPager mViewPager;
    //RecyclerView布局管理器
    private LinearLayoutManager mLinearLayoutManager;
    //RecyclerView适配器
    private MyAdapter mAdapter;
    //标题集合
    private List<String> list = new ArrayList<>();

    //控件高度
    private int mHeight;
    //控件宽度
    private int mWidth;

    //是否是滑动状态
    private boolean isScroll = false;

    //当前位置
    private int curPosition;


    public MyRecyclerVew(Context context) {
        super(context);
        init();
    }

    public MyRecyclerVew(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyRecyclerVew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w / 5;
    }

    private void init() {

        WindowManager systemService = (WindowManager) this.getContext().getSystemService(Context.WINDOW_SERVICE);
        mWidth = systemService.getDefaultDisplay().getWidth() / 5;
        int height = systemService.getDefaultDisplay().getHeight();

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        setLayoutManager(mLinearLayoutManager);

        initRecyclerView();

        mAdapter = new MyAdapter(list);
        setAdapter(mAdapter);

    }

    private void initRecyclerView() {
        this.setOnFlingListener(new OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                if (IS_DEBUG)
                    Log.d(TAG, "--> onFling:velocityX = " + velocityX + ",velocityY = " + velocityY);
                return false;
            }
        });

        this.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (IS_DEBUG) Log.d(TAG, "--> onScrollStateChanged:newState = " + newState);
                switch (newState) {
                    case 0:
                        if (isScroll) {
                            mViewPager.setCurrentItem(mLinearLayoutManager.findFirstVisibleItemPosition(), false);
                            isScroll = false;
                        }
                        break;
                    case 1:
                        isScroll = true;
                        break;
                    case 2:

                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (IS_DEBUG) Log.d(TAG, "--> onScrolled: dx = " + dx + ",dy = " + dy);
                curPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
            }
        });

    }

    public void setViewPager(ViewPager viewPager) {
        if (viewPager != null) {
            mViewPager = viewPager;
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (IS_DEBUG)
                        Log.d(TAG, "--> onPageScrolled:position = " + position + ",positionOffset = " + positionOffset + ",positionOffsetPixels = " + positionOffsetPixels);

                    int i = (int) (mWidth * positionOffset);
                    Log.d(TAG, "i = " + i);
//                    if (i != 0)
                    mLinearLayoutManager.scrollToPositionWithOffset(position, -i);
                }

                @Override
                public void onPageSelected(int position) {
                    if (IS_DEBUG) Log.d(TAG, "--> onPageSelected:position = " + position);
//                    mLinearLayoutManager.scrollToPosition(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (IS_DEBUG) Log.d(TAG, "--> onPageScrollStateChanged:state = " + state);
                    switch (state) {
                        case 2:
                            isScroll = true;
                            break;
                        case 0:
                            if (isScroll) {
//                                mLinearLayoutManager.scrollToPosition(mLinearLayoutManager.findFirstVisibleItemPosition() + 1);
                                isScroll = false;
                            }
                            break;
                    }

                }
            });

            PagerAdapter adapter = viewPager.getAdapter();
            for (int i = 0; i < adapter.getCount(); i++) {
                list.add(((String) adapter.getPageTitle(i)));
            }
            Log.d(TAG, "list = " + list.toString());
            mAdapter.replaceDatas(list);

        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        private List<String> list;

        public MyAdapter(List<String> list) {
            this.list = list;
        }

        public void replaceDatas(List<String> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView textView = new TextView(getContext());
            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(mWidth, ViewGroup.LayoutParams.MATCH_PARENT);
            textView.setLayoutParams(layoutParams);
            textView.setBackgroundColor(Color.parseColor("#ffffff"));
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(20);
            return new MyViewHolder(textView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
//            Log.d(TAG, "CONTENT:" + list.get(position));
//            holder.textView.setText(position <= 1 ? "" : position >= list.size() ? "" : list.get(position));
            holder.textView.setText(position <= 1 ? "" : position - 2 >= list.size() ? "" : list.get(position - 2));
//            holder.textView.setTag(position - 2);
            holder.textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(position - 2);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size() + 4;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView textView;

            public MyViewHolder(View itemView) {
                super(itemView);
                textView = ((TextView) itemView);
            }
        }
    }
}
