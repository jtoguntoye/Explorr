package com.e.explorr.Model;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Keep
public class RestaurantResponse {

    @SerializedName("data")
    private List<Restaurant> restaurants;
}
