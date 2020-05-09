package com.example.explorr.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DestinationSpecificResponse {

    @SerializedName("data")
    private List<Destinations> DestinationsList;

    public List<Destinations> getDestinationsList() {
        return DestinationsList;
    }

    public void setDestinationsList(List<Destinations> destinationsList) {
        DestinationsList = destinationsList;
    }




}
