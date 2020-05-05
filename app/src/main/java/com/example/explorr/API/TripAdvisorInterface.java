package com.example.explorr.API;

import com.example.explorr.Model.AttractionsResponse;
import com.example.explorr.Model.LocationResponse;
import com.example.explorr.Model.RestaurantResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TripAdvisorInterface {

    @GET("locations/search")
    Call<LocationResponse> getLocationResponse();

    @GET("restaurants/list")
    Call<RestaurantResponse> getRestaurantResponse(
    @Query("location_id") String locationId
    );

    @GET("attractions/list")
    Call<AttractionsResponse> getAttractionsResponse(
            @Query("location_id") String locationId
    );



}
