package com.cly.imageselectorlibrary.bean;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 丛龙宇 on 17-3-3.
 */

public class ImageInfo implements Parcelable {
    private Uri uri;
    private boolean isSelected;

    public ImageInfo() {
    }

    public ImageInfo(Uri uri, boolean isSelected) {
        this.uri = uri;
        this.isSelected = isSelected;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj instanceof ImageInfo) {
            if (((ImageInfo) obj).getUri().equals(uri))
                return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return uri.hashCode();
    }

    @Override
    public String toString() {
        return "ImageInfo{" +
                "uri=" + uri +
                ", isSelected=" + isSelected +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.uri, flags);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }

    protected ImageInfo(Parcel in) {
        this.uri = in.readParcelable(Uri.class.getClassLoader());
        this.isSelected = in.readByte() != 0;
    }

    public static final Parcelable.Creator<ImageInfo> CREATOR = new Parcelable.Creator<ImageInfo>() {
        @Override
        public ImageInfo createFromParcel(Parcel source) {
            return new ImageInfo(source);
        }

        @Override
        public ImageInfo[] newArray(int size) {
            return new ImageInfo[size];
        }
    };
}
