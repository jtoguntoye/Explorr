package com.e.explorr.RoomDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.e.explorr.Model.Destinations;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Destinations.class}, version = 1, exportSchema = false)
@TypeConverters(photosTypeConverter.class)
public abstract class FavoritesDestinationsDB extends RoomDatabase {

    //create an abstract getter method for each DAO
    public  abstract  FavoritesDestinationsDao favoritesDao();


    private static volatile FavoritesDestinationsDB INSTANCE;
    private static int NUM_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUM_OF_THREADS);

    //instantiate the RoomDatabase using a single   ton pattern
public static FavoritesDestinationsDB getDatabaseInstance(final Context context) {
        if(INSTANCE == null) {
            synchronized (FavoritesDestinationsDB.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            FavoritesDestinationsDB.class,
                            "favorites_database")
                            .build();
                }
            }
        }
    return INSTANCE;
}
}
