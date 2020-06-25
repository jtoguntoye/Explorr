package com.example.explorr;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.explorr.Model.Destinations;
import com.example.explorr.RoomDB.FavoritesDestinationsDB;
import com.example.explorr.RoomDB.FavoritesDestinationsDao;


import java.util.List;

import javax.inject.Inject;


public class DestinationsRepository {

    private FavoritesDestinationsDao favoritesDestinationsDao;
    private LiveData<List<Destinations>> mFavorites;
    private LiveData<Destinations> favorite;

    @Inject
    public DestinationsRepository(Application application){
        FavoritesDestinationsDB favoritesDestinationsDB = FavoritesDestinationsDB.getDatabaseInstance(application);
        favoritesDestinationsDao =favoritesDestinationsDB.favoritesDao();
    }


    public LiveData<List<Destinations>> getAllFavorites() {
    mFavorites = favoritesDestinationsDao.getAllFavorites();
     return mFavorites;
    }

    public void insertFavorite(Destinations destinations) {
        FavoritesDestinationsDB.databaseWriteExecutor.execute(() -> {
            favoritesDestinationsDao.insertFavorite(destinations);
        });

    }

    public LiveData<Destinations> getFavorite(String id) {
        favorite = favoritesDestinationsDao.getFavorite(id);

        return favorite;
    }

    public void deleteFromFavorite(Destinations destination) {
        FavoritesDestinationsDB.databaseWriteExecutor.execute(() -> {
            favoritesDestinationsDao.deleteFromFavorites(destination);
        });

    }
}
