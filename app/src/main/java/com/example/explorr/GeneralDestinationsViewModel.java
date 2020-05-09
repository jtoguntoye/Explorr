package com.example.explorr;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.explorr.Model.Destinations;
import java.util.List;
import javax.inject.Inject;


public class GeneralDestinationsViewModel  extends ViewModel {
    private  DestinationsRepository destinationsRepository;
    private MutableLiveData<List<Destinations>> hotelList;

    @Inject
    public GeneralDestinationsViewModel(Application application, DestinationsRepository destinationsRepository){
        this.destinationsRepository = destinationsRepository;
      hotelList = new MutableLiveData<>();

    }

    public String getLocationId(String locationQuery){
        return destinationsRepository.getLocationId(locationQuery);
    }


    public MutableLiveData<List<Destinations>> getHotelListResult(String locationId){
        hotelList.postValue(destinationsRepository.getHotelResult(locationId));
        return hotelList;
    }
}
