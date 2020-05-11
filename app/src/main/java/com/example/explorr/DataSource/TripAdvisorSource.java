package com.example.explorr.DataSource;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.explorr.API.TripAdvisorInterface;
import com.example.explorr.Model.DestinationSpecificResponse;
import com.example.explorr.Model.Destinations;
import com.example.explorr.Model.LocationSearchResponse;
import com.example.explorr.Model.Locations;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripAdvisorSource {

    private TripAdvisorInterface mTripAdvisorInterface;
    private MutableLiveData<List<Destinations>> HotelSearchResult, restaurantSearchResult,
            attractionSearchResult;
    private MutableLiveData<String> locationId;

    @Inject
    public TripAdvisorSource(TripAdvisorInterface TripAdvisorInterface){
    this.mTripAdvisorInterface = TripAdvisorInterface;
    HotelSearchResult = new MutableLiveData<>();
    locationId = new MutableLiveData<>();
    restaurantSearchResult = new MutableLiveData<>();
    attractionSearchResult = new MutableLiveData<>();

}

public LiveData<String> getLocationResponse(String location){

    mTripAdvisorInterface.getLocationResponse(location).enqueue
        (new Callback<LocationSearchResponse>() {
    @Override
    public void onResponse(Call<LocationSearchResponse> call, Response<LocationSearchResponse> response) {
        if(response.isSuccessful()){
            if(response.body()!=null){
            locationId.postValue(response.body().getLocations().get(1).getLocationObject().getLocation_id());

            }


        }

    }

    @Override
    public void onFailure(Call<LocationSearchResponse> call, Throwable t) {
        t.printStackTrace();
    }
});

return locationId;
}


public LiveData<List<Destinations>> getHotelDestinationResponse(String locationId){

        mTripAdvisorInterface.getHotelResponse(locationId).enqueue(new Callback<DestinationSpecificResponse>() {
            @Override
            public void onResponse(Call<DestinationSpecificResponse> call, Response<DestinationSpecificResponse> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        HotelSearchResult.postValue(response.body().getDestinationsList());

                    }
                }
            }

            @Override
            public void onFailure(Call<DestinationSpecificResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return HotelSearchResult;
}


public LiveData<List<Destinations>> getRestaurantResponse(String locationId){
        mTripAdvisorInterface.getRestaurantResponse(locationId).enqueue(new Callback<DestinationSpecificResponse>() {
            @Override
            public void onResponse(Call<DestinationSpecificResponse> call, Response<DestinationSpecificResponse> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        restaurantSearchResult.postValue(response.body().getDestinationsList());
                    }
                }
            }

            @Override
            public void onFailure(Call<DestinationSpecificResponse> call, Throwable t) {
                t.printStackTrace();

            }
        });
        return restaurantSearchResult;
}

    public LiveData<List<Destinations>> getAttractionsResponse(String locationID) {
        mTripAdvisorInterface.getAttractionsResponse(locationID).enqueue(new Callback<DestinationSpecificResponse>() {
            @Override
            public void onResponse(Call<DestinationSpecificResponse> call, Response<DestinationSpecificResponse> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        attractionSearchResult.postValue(response.body().getDestinationsList());

                    }

                }
            }

            @Override
            public void onFailure(Call<DestinationSpecificResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    return attractionSearchResult;}
}
