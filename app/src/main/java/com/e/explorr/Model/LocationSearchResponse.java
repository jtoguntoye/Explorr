package com.e.explorr.Model;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Keep
public class LocationSearchResponse {

    @SerializedName("data")
    private List<Locations> Locations;


    public List<Locations> getLocations() {
        return Locations;
    }
}
