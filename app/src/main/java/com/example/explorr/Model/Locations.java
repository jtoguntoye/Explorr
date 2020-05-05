package com.example.explorr.Model;

import com.google.gson.annotations.SerializedName;

public class Locations {

    @SerializedName("result_type")
    private String result_type;

    @SerializedName("result_object")
    private LocationObject locationObject;

    public LocationObject getLocationObject() {
        return locationObject;
    }

    public void setLocationObject(LocationObject locationObject) {
        this.locationObject = locationObject;
    }



    public static class LocationObject {

        @SerializedName("location_id")
        private String location_id;

        @SerializedName("latitude")
        private String latitude;

        @SerializedName("longitude")
        private String longitude;


        public String getLocation_id() {
            return location_id;
        }

        public void setLocation_id(String location_id) {
            this.location_id = location_id;
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
    }
}
