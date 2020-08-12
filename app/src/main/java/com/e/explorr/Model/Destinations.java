package com.e.explorr.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Entity(tableName = "favorites_table")
public class Destinations implements Parcelable {

    public Destinations() {
    }

    @PrimaryKey
    @NonNull
    @SerializedName("location_id")
    private String destinationId;

    @ColumnInfo
    @SerializedName("name")
    private String DestinationName;

    @ColumnInfo
    @SerializedName("latitude")
    private String latitude;

    @ColumnInfo
    @SerializedName("longitude")
    private String longitude;

    @ColumnInfo
    @SerializedName("location_string")
    private String DestinationLocation;

    @ColumnInfo
    @SerializedName("description")
    private String DestinationDescription;


    @SerializedName("address")
    private String DestinationAddress;

    @SerializedName("website")
    private String website;

    @ColumnInfo
    @SerializedName("photo")
    private DestinationPhotos photos;



    @SerializedName("ranking_category")
    private String destinationType;


    @NotNull
    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    public String getDestinationName() {
        return DestinationName;
    }

    public void setDestinationName(String destinationName) {
        this.DestinationName = destinationName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDestinationLocation() {
        return DestinationLocation;
    }

    public void setDestinationLocation(String destinationLocation) {
        this.DestinationLocation = destinationLocation;
    }

    public String getDestinationDescription() {
        return DestinationDescription;
    }

    public void setDestinationDescription(String destinationDescription) {
        DestinationDescription = destinationDescription;
    }

    public String getDestinationAddress() {
        return DestinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        DestinationAddress = destinationAddress;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public DestinationPhotos getPhotos() {
        return photos;
    }

    public void setPhotos(DestinationPhotos photos) {
        this.photos = photos;
    }

    public String getDestinationType() {
        return destinationType;
    }

    public void setDestinationType(String destinationType) {
        this.destinationType = destinationType;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.destinationId);
        dest.writeString(this.DestinationName);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
        dest.writeString(this.DestinationLocation);
        dest.writeString(this.DestinationDescription);
        dest.writeString(this.DestinationAddress);
        dest.writeString(this.website);
        dest.writeParcelable(this.photos, flags);
        dest.writeString(this.destinationType);
    }


    protected Destinations(Parcel in) {
        this.destinationId = Objects.requireNonNull(in.readString());
        this.DestinationName = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
        this.DestinationLocation = in.readString();
        this.DestinationDescription = in.readString();
        this.DestinationAddress = in.readString();
        this.website = in.readString();
        this.photos = in.readParcelable(DestinationPhotos.class.getClassLoader());
        this.destinationType = in.readString();
    }

    public static final Parcelable.Creator<Destinations> CREATOR = new Parcelable.Creator<Destinations>() {
        @Override
        public Destinations createFromParcel(Parcel source) {
            return new Destinations(source);
        }

        @Override
        public Destinations[] newArray(int size) {
            return new Destinations[size];
        }
    };
}
