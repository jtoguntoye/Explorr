package com.e.explorr.Model;

import com.google.gson.annotations.SerializedName;

class Restaurant {


    @SerializedName("location_id")
    private String restaurantId;

    @SerializedName("name")
    private String RestaurantName;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("location_string")
    private String RestaurantLocation;

    @SerializedName("description")
    private String RestaurantDescription;

    @SerializedName("address")
    private String RestaurantAddress;

    @SerializedName("photos")
    private DestinationPhotos photos;


    @SerializedName("website")
    private String website;




    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return RestaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        RestaurantName = restaurantName;
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

    public String getRestaurantLocation() {
        return RestaurantLocation;
    }

    public void setRestaurantLocation(String restaurantLocation) {
        RestaurantLocation = restaurantLocation;
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

    public DestinationPhotos getPhotos() {
        return photos;
    }

    public void setPhotos(DestinationPhotos photos) {
        this.photos = photos;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

}
