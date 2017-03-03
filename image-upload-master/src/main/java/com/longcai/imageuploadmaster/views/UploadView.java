package com.longcai.imageuploadmaster.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcai.imageuploadmaster.R;
import com.longcai.imageuploadmaster.utils.ScalingUtilities;

/**
 * Created by 丛龙宇 on 17-3-1.
 */

public class UploadView extends FrameLayout {
    //进度条颜色
    private int progressBgColor;
    //进度条字体颜色
    private int progressTextColor;

    //显示图片的ImageView
    private ImageView imageView;
    //显示进度的TextView
    private TextView textView;
    //用作进度条的View
    private View view;

    private Bitmap scaledBitmap;

    public UploadView(Context context) {
        super(context);
        init(context);
    }

    public UploadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.UploadView,
                0, 0
        );
        progressBgColor = typedArray.getColor(R.styleable.UploadView_progressBgColor, Color.BLUE);
        progressTextColor = typedArray.getColor(R.styleable.UploadView_progressTextColor, Color.BLACK);

        typedArray.recycle();

        init(context);
    }

    public UploadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_uploadview, this, true);

        imageView = ((ImageView) view.findViewById(R.id.iv_uploadview));
        textView = ((TextView) view.findViewById(R.id.tv_uploadview));
        this.view = view.findViewById(R.id.v_uploadview);

        this.view.setBackgroundColor(progressBgColor);
        textView.setTextColor(progressTextColor);
    }

    public void setImageUrl(String url) {

    }
}
