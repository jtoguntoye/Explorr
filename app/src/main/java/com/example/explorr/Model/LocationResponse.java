package com.example.explorr.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocationResponse {

    @SerializedName("data")
    private List<Locations> Locations;


    public List<Locations> getLocations() {
        return Locations;
    }
}
