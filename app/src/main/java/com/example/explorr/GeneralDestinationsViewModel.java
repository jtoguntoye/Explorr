package com.example.explorr;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.explorr.DataSource.TripAdvisorSource;
import com.example.explorr.Model.Destinations;
import com.example.explorr.Model.LocationSearchResponse;

import java.util.List;
import javax.inject.Inject;


public class GeneralDestinationsViewModel  extends ViewModel {
    private TripAdvisorSource tripAdvisorSource;


    @Inject
    public GeneralDestinationsViewModel(Application application, TripAdvisorSource tripAdvisorSource){
        this.tripAdvisorSource= tripAdvisorSource;


    }

    public  LiveData<String> getLocationId(String locationQuery){

        return tripAdvisorSource.getLocationResponseId(locationQuery);

    }




    public LiveData<List<Destinations>> getHotelListResult(String locationId){
        return   tripAdvisorSource.getHotelDestinationResponse(locationId);

    }

    public LiveData<List<Destinations>> getRestaurantResult(String locationID){
        return tripAdvisorSource.getRestaurantResponse(locationID);
    }

    public LiveData<List<Destinations>> getAttractionsResult(String locationID) {
        return tripAdvisorSource.getAttractionsResponse(locationID);
    }

}
