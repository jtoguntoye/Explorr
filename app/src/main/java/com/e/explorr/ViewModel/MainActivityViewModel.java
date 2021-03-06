package com.e.explorr.ViewModel;

import android.location.Location;
import android.util.Log;

import androidx.lifecycle.LiveData;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.e.explorr.DataSource.TripAdvisorSource;
import com.e.explorr.DependencyInjection.MainActivityScope;
import com.e.explorr.DestinationsRepository;
import com.e.explorr.Model.Destinations;

import java.util.List;

import javax.inject.Inject;


@MainActivityScope
public class MainActivityViewModel extends ViewModel {
    private TripAdvisorSource tripAdvisorSource;
    private  LiveData<List<Destinations>> hotelListByCoordinates= new MutableLiveData<>();
    private  LiveData<List<Destinations>> attractionListByCoordinates= new MutableLiveData<>();
    private  LiveData<List<Destinations>> restaurantListByCoordinates=new MutableLiveData<>();
    private LiveData<List<Destinations>>  favoritesList = new MutableLiveData<>();
    private final MutableLiveData<Location> userLocation = new MutableLiveData<>();
    private DestinationsRepository destinationsRepository;

    @Inject
    public MainActivityViewModel(TripAdvisorSource tripAdvisorSource, DestinationsRepository destinationsRepository) {
        this.destinationsRepository = destinationsRepository;
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


    public LiveData<List<Destinations>> getFavorites() {
        favoritesList = destinationsRepository.getAllFavorites();
        return favoritesList;
    }
}



