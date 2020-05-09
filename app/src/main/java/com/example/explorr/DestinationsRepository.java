package com.example.explorr;

import com.example.explorr.DataSource.TripAdvisorSource;
import com.example.explorr.Model.Destinations;

import java.util.List;

import javax.inject.Inject;

public class DestinationsRepository {
    private TripAdvisorSource tripAdvisorSource;

    @Inject
    public DestinationsRepository(TripAdvisorSource tripAdvisorSource){
        this.tripAdvisorSource = tripAdvisorSource;
    }

    public String getLocationId(String locationQuery){
        return tripAdvisorSource.getLocationResponse(locationQuery);
    }

    public List<Destinations> getHotelResult(String locationId){
       return tripAdvisorSource.getDestinationSpecificResponse(locationId);
    }
}
