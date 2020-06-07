package com.example.explorr.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ImagesClass implements Parcelable {

    @SerializedName("small")
    private SmallImage small;

    @SerializedName("original")
    private OriginalImage originalImage;


    public SmallImage getSmall() {
        return small;
    }

    public OriginalImage getOriginalImage() {
        return originalImage;
    }


    public void setSmall(SmallImage small) {
        this.small = small;
    }

    public void setOriginalImage(OriginalImage originalImage) {
        this.originalImage = originalImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.small, flags);
        dest.writeParcelable(this.originalImage, flags);
    }

    public ImagesClass() {
    }

    protected ImagesClass(Parcel in) {
        this.small = in.readParcelable(SmallImage.class.getClassLoader());
        this.originalImage = in.readParcelable(OriginalImage.class.getClassLoader());
    }

    public static final Creator<ImagesClass> CREATOR = new Creator<ImagesClass>() {
        @Override
        public ImagesClass createFromParcel(Parcel source) {
            return new ImagesClass(source);
        }

        @Override
        public ImagesClass[] newArray(int size) {
            return new ImagesClass[size];
        }
    };
}
