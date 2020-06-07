package com.example.explorr.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SmallImage implements Parcelable {
    @SerializedName("width")
    private String Width;

    @SerializedName("height")
    private String height;

    @SerializedName("url")
    private String Url;



    public void setWidth(String width) {
        Width = width;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setUrl(String url) {
        Url = url;
    }


    public String getWidth() {
        return Width;
    }

    public String getHeight() {
        return height;
    }

    public String getUrl() {
        return Url;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Width);
        dest.writeString(this.height);
        dest.writeString(this.Url);
    }

    public SmallImage() {
    }

    protected SmallImage(Parcel in) {
        this.Width = in.readString();
        this.height = in.readString();
        this.Url = in.readString();
    }

    public static final Creator<SmallImage> CREATOR = new Creator<SmallImage>() {
        @Override
        public SmallImage createFromParcel(Parcel source) {
            return new SmallImage(source);
        }

        @Override
        public SmallImage[] newArray(int size) {
            return new SmallImage[size];
        }
    };
}
