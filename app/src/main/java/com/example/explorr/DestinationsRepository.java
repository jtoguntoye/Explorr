package com.example.explorr;

import android.util.Log;

import com.example.explorr.DataSource.TripAdvisorSource;


import java.util.List;



public class DestinationsRepository {
    private TripAdvisorSource tripAdvisorSource;
    private String locationID;


    public DestinationsRepository(TripAdvisorSource tripAdvisorSource){
        this.tripAdvisorSource = tripAdvisorSource;
    }



}
