package com.cly.myapplication.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cly.myapplication.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.List;

/**
 * Created by 丛龙宇 on 17-2-15.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder> {

    private static final String TAG = "ImageAdapter";

    private Context context;
    private List<Uri> list;

    public ImageAdapter(Context context, List<Uri> list) {
        Log.d(TAG, "--> ImageAdapter");
        this.context = context;
        this.list = list;
    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_imageselector, parent, false);

        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageHolder holder, int position) {
        SimpleDraweeView draweeView = holder.simpleDraweeView;
        Uri parse = Uri.parse("file:///" + list.get(position).toString());
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
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "--> getItemCount:size = " + list.size());
        return list.size();
    }

    static class ImageHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView simpleDraweeView;

        public ImageHolder(View itemView) {
            super(itemView);
            simpleDraweeView = ((SimpleDraweeView) itemView.findViewById(R.id.sdv_imageselector));
        }
    }
}
