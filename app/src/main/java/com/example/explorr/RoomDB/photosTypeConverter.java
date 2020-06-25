package com.example.explorr.RoomDB;

import androidx.room.TypeConverter;

import com.example.explorr.Model.DestinationPhotos;
import com.google.gson.Gson;


public class photosTypeConverter {

    @TypeConverter
    public String   photosToString(DestinationPhotos photos) {
        return new Gson().toJson(photos);
    }

    @TypeConverter
    public DestinationPhotos StringToPhotos (String jsonString) {

        return new Gson().fromJson(jsonString,DestinationPhotos.class);
    }

}
