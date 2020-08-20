package com.e.explorr.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

@Keep
public class DestinationPhotos implements Parcelable {

    public DestinationPhotos() {
    }

    @SerializedName("images")
    private ImagesClass images;

    public ImagesClass getImages() {
        return images;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.images, flags);
    }



    protected DestinationPhotos(Parcel in) {
        this.images = in.readParcelable(ImagesClass.class.getClassLoader());
    }

    public static final Parcelable.Creator<DestinationPhotos> CREATOR = new Parcelable.Creator<DestinationPhotos>() {
        @Override
        public DestinationPhotos createFromParcel(Parcel source) {
            return new DestinationPhotos(source);
        }

        @Override
        public DestinationPhotos[] newArray(int size) {
            return new DestinationPhotos[size];
        }
    };
}
