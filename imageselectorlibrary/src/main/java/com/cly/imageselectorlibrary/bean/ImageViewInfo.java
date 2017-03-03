package com.cly.imageselectorlibrary.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Created by 丛龙宇 on 17-2-15.
 */

public class ImageViewInfo implements Parcelable {

    private int[] screenLocation;
    private int height;
    private int width;

    public ImageViewInfo(int[] screenLocation, int height, int width) {
        this.screenLocation = screenLocation;
        this.height = height;
        this.width = width;
    }

    public int[] getScreenLocation() {
        return screenLocation;
    }

    public void setScreenLocation(int[] screenLocation) {
        this.screenLocation = screenLocation;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "ImageViewInfo{" +
                "screenLocation=" + Arrays.toString(screenLocation) +
                ", height=" + height +
                ", width=" + width +
                '}';
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(this.screenLocation);
        dest.writeInt(this.height);
        dest.writeInt(this.width);
    }

    public ImageViewInfo() {
    }

    protected ImageViewInfo(Parcel in) {
        this.screenLocation = in.createIntArray();
        this.height = in.readInt();
        this.width = in.readInt();
    }

    public static final Creator<ImageViewInfo> CREATOR = new Creator<ImageViewInfo>() {
        @Override
        public ImageViewInfo createFromParcel(Parcel source) {
            return new ImageViewInfo(source);
        }

        @Override
        public ImageViewInfo[] newArray(int size) {
            return new ImageViewInfo[size];
        }
    };
}
