package com.example.explorr.API;

import com.example.explorr.Model.DestinationSpecificResponse;
import com.example.explorr.Model.LocationSearchResponse;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface TripAdvisorInterface {

    @GET("locations/search")
    Call<LocationSearchResponse> getLocationResponse(
            @Query("query") String locationString
    );

    @GET("restaurants/list")
    Call<DestinationSpecificResponse> getRestaurantResponse(
    @Query("location_id") String locationId
    );

    @GET("attractions/list")
    Call<DestinationSpecificResponse> getAttractionsResponse(
            @Query("location_id") String locationId
    );

    @GET("hotels/list")
    Call<DestinationSpecificResponse> getHotelResponse(
            @Query("location_id") String locationId
    );

    @GET("hotels/list-by-latlng")
    Call<DestinationSpecificResponse> getHotelsResponseByLatLng(
            @Query("latitude") String latitude,
            @Query("longitude") String longitude
    );


}
