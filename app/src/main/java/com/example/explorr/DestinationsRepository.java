package com.example.explorr;

import android.util.Log;

import com.example.explorr.DataSource.TripAdvisorSource;
import com.example.explorr.Model.Destinations;

import java.util.List;

import javax.inject.Inject;

public class DestinationsRepository {
    private TripAdvisorSource tripAdvisorSource;
    private String locationID;

    @Inject
    public DestinationsRepository(TripAdvisorSource tripAdvisorSource){
        this.tripAdvisorSource = tripAdvisorSource;
    }

    public String getLocationId(String locationQuery){

        //locationID =tripAdvisorSource.getLocationResponse(locationQuery);

        return locationID;
    }

   // public List<Destinations> getHotelResult(String locationId){
       //return tripAdvisorSource.getDestinationSpecificResponse(locationId);
   // }
}
