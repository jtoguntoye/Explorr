package com.example.explorr.DataSource;

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
    private List<Destinations> HotelSearchResult;
    private String locationId;

    @Inject
    public TripAdvisorSource(TripAdvisorInterface TripAdvisorInterface){
    this.mTripAdvisorInterface = TripAdvisorInterface;
    HotelSearchResult = new ArrayList<>();
}

public String getLocationResponse(String location){

mTripAdvisorInterface.getLocationResponse(location).enqueue
        (new Callback<LocationSearchResponse>() {
    @Override
    public void onResponse(Call<LocationSearchResponse> call, Response<LocationSearchResponse> response) {
        if(response.isSuccessful()){
            if(response.body()!=null){
            locationId = response.body().getLocations().get(0).getLocationObject().getLocation_id();
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


public List<Destinations> getDestinationSpecificResponse(String locationId){

        mTripAdvisorInterface.getHotelResponse(locationId).enqueue(new Callback<DestinationSpecificResponse>() {
            @Override
            public void onResponse(Call<DestinationSpecificResponse> call, Response<DestinationSpecificResponse> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        HotelSearchResult = response.body().getDestinationsList();

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

}
