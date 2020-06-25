package com.example.explorr.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.explorr.DataSource.TripAdvisorSource;
import com.example.explorr.Model.Destinations;

import java.util.List;
import javax.inject.Inject;


public class GeneralDestinationsViewModel  extends ViewModel {
    private TripAdvisorSource tripAdvisorSource;
    private LiveData<List<Destinations>> hotelList = new MutableLiveData<>();
    private LiveData<List<Destinations>> restaurantList = new MutableLiveData<>();
    private LiveData<List<Destinations>> attractionsList = new MutableLiveData<>();
    private  MutableLiveData<String> locationId = new MutableLiveData<>();


    @Inject
    public GeneralDestinationsViewModel( TripAdvisorSource tripAdvisorSource){
        this.tripAdvisorSource= tripAdvisorSource;
        Log.d("TAG","General destinations ViewModel is created");
    }

    public LiveData<String> getLocationId(String locationQuery){

        locationId = tripAdvisorSource.getLocationResponseId(locationQuery);
        return locationId;
    }

    public LiveData<List<Destinations>> getHotelListResult(String locationId){
        if(hotelList.getValue() == null){
        hotelList =  tripAdvisorSource.getHotelDestinationResponse(locationId);
        }
    return hotelList;
    }

    public LiveData<List<Destinations>> getRestaurantResult(String locationID){
        if(restaurantList.getValue() ==null){
         restaurantList = tripAdvisorSource.getRestaurantResponse(locationID);
        }
         return restaurantList;
    }

    public LiveData<List<Destinations>> getAttractionsResult(String locationID) {
        if(attractionsList.getValue() ==null){
        attractionsList = tripAdvisorSource.getAttractionsResponse(locationID);
        }
        return attractionsList;
    }

}
