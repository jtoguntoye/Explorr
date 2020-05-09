package com.example.explorr.Model;

import com.google.gson.annotations.SerializedName;

public class Destinations {

    @SerializedName("location_id")
    private String destinationId;

    @SerializedName("name")
    private String DestinationName;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("location_string")
    private String DestinationLocation;

    @SerializedName("description")
    private String DestinationDescription;

    @SerializedName("address")
    private String DestinationAddress;

    @SerializedName("website")
    private String website;

    @SerializedName("photos")
    private DestinationPhotos photos;



    @SerializedName("ranking_category")
    private String destinationType;


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

}
