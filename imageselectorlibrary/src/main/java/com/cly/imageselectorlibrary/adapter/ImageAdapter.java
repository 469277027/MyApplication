package com.cly.imageselectorlibrary.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cly.imageselectorlibrary.ImageSelectorFragment;
import com.cly.imageselectorlibrary.bean.ImageInfo;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.cly.imageselectorlibrary.R;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by 丛龙宇 on 17-2-15.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder> implements View.OnClickListener {

    private static final String TAG = "ImageAdapter";
    private final RelativeLayout.LayoutParams layoutParams;

    private static final int DEFAULT_MAX_NUM = 9;

    private Context context;
    //数据源
    private List<ImageInfo> list;
    //使用何种方式展示
    private int showType;

    //被选中的图片集合
    private Set<ImageInfo> set = new HashSet<>();

    //最大图片数量
    private int maxNum = DEFAULT_MAX_NUM;

    private OnImageClickListener onImageClickListener;


    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }

    @Override
    public void onClick(View v) {
        View view = (View) v.getTag(R.id.image_tag);
        if (view != null) {
            int size = set.size();
            if (!view.isSelected() && size >= maxNum) {
                Toast.makeText(context, "最多选择" + maxNum + "张图片", Toast.LENGTH_SHORT).show();
            } else {
                view.setSelected(!view.isSelected());
                view.requestLayout();
                ImageInfo info = (ImageInfo) view.getTag();
                if (view.isSelected()) {
                    set.add(info);
                } else {
                    set.remove(info);
                }
            }
        }
        Log.d(TAG, "--> onClick:set = " + set.toString());
        onImageClickListener.imageClick(v);
    }

    //得到选择的图片集合
    public Set<ImageInfo> getSet() {
        return set;
    }

    //设置最大图片选择数量
    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public static interface OnImageClickListener {
        void imageClick(View view);
    }

    public ImageAdapter(Context context, List<ImageInfo> list, int showType) {
        Log.d(TAG, "--> ImageAdapter");
        this.context = context;
        this.list = list;
        this.showType = showType;
        WindowManager systemService = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = systemService.getDefaultDisplay().getWidth();
        int itemWidth = width / 3;
        layoutParams = new RelativeLayout.LayoutParams(itemWidth, itemWidth);
    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (showType == ImageSelectorFragment.SHOW_WITH_FRESCO) {
            view = LayoutInflater.from(context).inflate(R.layout.item_imageselector_fresco, parent, false);
        }

        if (showType == ImageSelectorFragment.SHOW_WITH_GLIDE) {
            view = LayoutInflater.from(context).inflate(R.layout.item_imageselector_glide, parent, false);
        }

        return new ImageHolder(view, showType);
    }

    @Override
    public void onBindViewHolder(ImageHolder holder, int position) {
//        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(itemWidth, itemWidth);
        if (showType == ImageSelectorFragment.SHOW_WITH_FRESCO) {
            SimpleDraweeView draweeView = holder.simpleDraweeView;
            draweeView.setOnClickListener(this);
            draweeView.setLayoutParams(layoutParams);
            Uri parse = Uri.parse("file:///" + list.get(position).getUri().toString());
            ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(parse)
                    .setResizeOptions(new ResizeOptions(50, 50))
                    .setLocalThumbnailPreviewsEnabled(true)
                    .build();

            AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(draweeView.getController())
                    .setImageRequest(imageRequest)
                    .build();

            draweeView.setController(controller);
            Log.d(TAG, "--> onBindViewHolder:uri = " + parse.toString());
        } else {
            holder.imageView.setLayoutParams(layoutParams);
            holder.imageView.setOnClickListener(this);
            holder.selectedState.setTag(list.get(position));
            holder.selectedState.setSelected(list.get(position).isSelected());
            holder.imageView.setTag(R.id.image_tag, holder.selectedState);
            Glide.with(context)
                    .load(new File(list.get(position).getUri().toString()))
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "--> getItemCount:size = " + list.size());
        return list.size();
    }

    static class ImageHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView simpleDraweeView;
        ImageView imageView, selectedState;

        public ImageHolder(View itemView, int showType) {
            super(itemView);
            if (showType == ImageSelectorFragment.SHOW_WITH_FRESCO)
                simpleDraweeView = ((SimpleDraweeView) itemView.findViewById(R.id.sdv_imageselector));
            if (showType == ImageSelectorFragment.SHOW_WITH_GLIDE) {
                imageView = ((ImageView) itemView.findViewById(R.id.iv_imageselector));
                selectedState = ((ImageView) itemView.findViewById(R.id.iv_imageselector_state));
            }
        }
    }
}
