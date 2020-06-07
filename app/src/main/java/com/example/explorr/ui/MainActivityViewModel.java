package com.example.explorr.ui;

import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.explorr.DataSource.TripAdvisorSource;
import com.example.explorr.DependencyInjection.MainActivityScope;
import com.example.explorr.Model.Destinations;

import java.util.List;

import javax.inject.Inject;


@MainActivityScope
public class MainActivityViewModel extends ViewModel {
    private TripAdvisorSource tripAdvisorSource;
    private  LiveData<List<Destinations>> hotelListByCoordinates= new MutableLiveData<>();
    private  LiveData<List<Destinations>> attractionListByCoordinates= new MutableLiveData<>();
    private  LiveData<List<Destinations>> restaurantListByCoordinates=new MutableLiveData<>();
    private final MutableLiveData<Location> userLocation = new MutableLiveData<>();

    @Inject
    public MainActivityViewModel(TripAdvisorSource tripAdvisorSource) {
        this.tripAdvisorSource = tripAdvisorSource;
        Log.d("VIEWMODELTAG","MAinActivityViewmodel is created!");

    }

    public LiveData<List<Destinations>> getHotelsByCoordinates(double Latitude,double Longitude){
        if(hotelListByCoordinates.getValue() == null){
         hotelListByCoordinates =  tripAdvisorSource.getHotelsByLatLng(Latitude, Longitude);
        }
         return hotelListByCoordinates;
    }

    public LiveData<List<Destinations>> getRestaurantsByCoordinates(double Latitude,double Longitude){
        if(restaurantListByCoordinates.getValue()==null) {
            restaurantListByCoordinates = tripAdvisorSource.getRestaurantsByLatLng(Latitude, Longitude);
        }
        return restaurantListByCoordinates;
    }

    public LiveData<List<Destinations>> getAttractionsByCoordinates(double Latitude, double Longitude){
        if(attractionListByCoordinates.getValue() == null){
        attractionListByCoordinates =  tripAdvisorSource.getAttractionsByLatLng(Latitude,Longitude);
        }

        return attractionListByCoordinates;
    }


    public LiveData<Location> getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(Location location){
        userLocation.setValue(location);
    }


}



