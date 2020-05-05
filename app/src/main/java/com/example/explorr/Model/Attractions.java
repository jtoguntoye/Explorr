package com.example.explorr.Model;

import com.google.gson.annotations.SerializedName;

public class Attractions {

    @SerializedName("location_id")
    private String attractionId;

    @SerializedName("name")
    private String attractionName;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("location_string")
    private String attractionLocation;

    @SerializedName("description")
    private String RestaurantDescription;

    @SerializedName("address")
    private String RestaurantAddress;

    @SerializedName("website")
    private String website;

    @SerializedName("photos")
    private DestinationPhotos photos;


    public String getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(String attractionId) {
        this.attractionId = attractionId;
    }

    public String getAttractionName() {
        return attractionName;
    }

    public void setAttractionName(String attractionName) {
        this.attractionName = attractionName;
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

    public String getAttractionLocation() {
        return attractionLocation;
    }

    public void setAttractionLocation(String attractionLocation) {
        this.attractionLocation = attractionLocation;
    }

    public String getRestaurantDescription() {
        return RestaurantDescription;
    }

    public void setRestaurantDescription(String restaurantDescription) {
        RestaurantDescription = restaurantDescription;
    }

    public String getRestaurantAddress() {
        return RestaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        RestaurantAddress = restaurantAddress;
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

}
