package com.example.explorr.RoomDB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.explorr.Model.Destinations;

import java.util.List;

import retrofit2.http.GET;

@Dao
public interface FavoritesDestinationsDao {

    @Query("SELECT * FROM favorites_table")
    LiveData<List<Destinations>> getAllFavorites();

    @Insert(onConflict= OnConflictStrategy.IGNORE)
    void insertFavorite (Destinations destinations);

    @Query("SELECT * FROM favorites_table where destinationId=:ID")
    LiveData<Destinations> getFavorite(String ID);

    @Delete
    void deleteFromFavorites (Destinations destinations);


}
