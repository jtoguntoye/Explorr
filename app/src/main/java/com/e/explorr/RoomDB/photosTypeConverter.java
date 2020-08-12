package com.e.explorr.RoomDB;

import androidx.room.TypeConverter;

import com.e.explorr.Model.DestinationPhotos;
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
