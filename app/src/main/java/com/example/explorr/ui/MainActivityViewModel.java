package com.example.explorr.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.explorr.DataSource.TripAdvisorSource;
import com.example.explorr.DependencyInjection.MainActivityScope;
import com.example.explorr.Model.Destinations;

import java.util.List;

import javax.inject.Inject;

@MainActivityScope
public class MainActivityViewModel extends ViewModel {
    private TripAdvisorSource tripAdvisorSource;

    @Inject
    public MainActivityViewModel(TripAdvisorSource tripAdvisorSource) {
        this.tripAdvisorSource = tripAdvisorSource;
    }

    public LiveData<List<Destinations>> getHotelsByCoordinates(double Latitude,double Longitude){

        return tripAdvisorSource.getHotelsByLatLng(Latitude, Longitude);
    }

    public LiveData<List<Destinations>> getRestaurantsByCoordinates(double Latitude,double Longitude){

        return tripAdvisorSource.getRestaurantsByLatLng(Latitude, Longitude);
    }

    public LiveData<List<Destinations>> getAttractionsByCoordinates(double Latitude, double Longitude){
        return tripAdvisorSource.getAttractionsByLatLng(Latitude,Longitude);
    }

}