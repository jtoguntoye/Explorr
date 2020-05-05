package com.example.explorr.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantResponse {

    @SerializedName("data")
    private List<Restaurant> restaurants;
}
