package com.example.explorr.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.explorr.DestinationsRepository;
import com.example.explorr.Model.Destinations;

import java.util.List;

import javax.inject.Inject;

public class PlacesDetailsViewModel extends AndroidViewModel {

    private DestinationsRepository repository;
    private LiveData<Destinations> mFavorite;


    public PlacesDetailsViewModel(Application application) {
        super(application);
        repository = new DestinationsRepository(application);

    }

    public void insertFavorite(Destinations destinations) {
        repository.insertFavorite(destinations);
    }

    public LiveData<Destinations> getFavorite(String id){
        mFavorite = repository.getFavorite(id);
        return mFavorite;
    }

    public void deleteFromFavorites(Destinations destination) {
        repository.deleteFromFavorite( destination);
    }
}
