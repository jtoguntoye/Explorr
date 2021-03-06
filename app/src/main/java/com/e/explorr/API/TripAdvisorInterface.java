package com.e.explorr.API;

import com.e.explorr.Model.DestinationSpecificResponse;
import com.e.explorr.Model.LocationSearchResponse;


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
            @Query("latitude") double latitude,
            @Query("longitude") double longitude
    );

    @GET("restaurants/list-by-latlng")
    Call<DestinationSpecificResponse> getRestaurantResponseByLatLng(
            @Query("latitude") double latitude,
            @Query("longitude") double longitude,
            @Query("distance") int distance
    );


    @GET("attractions/list-by-latlng")
    Call<DestinationSpecificResponse> getAttractionsResponseByLatLng(
            @Query("latitude") double latitude,
            @Query("longitude") double longitude,
            @Query("distance") int distance
    );
}
