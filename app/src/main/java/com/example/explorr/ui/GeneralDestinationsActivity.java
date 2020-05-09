package com.example.explorr.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.explorr.Adapters.GeneralDestinationsVerticalAdapter;
import com.example.explorr.DependencyInjection.myApplication;
import com.example.explorr.GeneralDestinationsViewModel;
import com.example.explorr.Model.Destinations;
import com.example.explorr.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class GeneralDestinationsActivity extends AppCompatActivity {

    @Inject
    GeneralDestinationsViewModel generalDestinationsViewModel;
    private String locationId;
    private List<List<Destinations>> GroupedList;
    private GeneralDestinationsVerticalAdapter generalDestinationsVerticalAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ((myApplication) getApplicationContext()).appComponent.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_destinations);

        generalDestinationsVerticalAdapter =
                new GeneralDestinationsVerticalAdapter(this, new ArrayList<List<Destinations>>()) ;
        RecyclerView verticalRecyclerView = findViewById(R.id.destinations__vertical_recyclerView);
        verticalRecyclerView.setHasFixedSize(true);
        verticalRecyclerView.setLayoutManager(new LinearLayoutManager
                (this,RecyclerView.VERTICAL,false));
        verticalRecyclerView.setAdapter(generalDestinationsVerticalAdapter);



        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getStringExtra(SearchManager.QUERY))){
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d("QUERYSTRING:",query);

            //get location_id from TripAdvisor API
            locationId = generalDestinationsViewModel.getLocationId(query);
            //get the hotels, restaurants and attractions for the queried location
            generalDestinationsViewModel.getHotelListResult(locationId).observe(this,
                    (List<Destinations> destinationList) ->{
                        GroupedList.add(destinationList);
                        generalDestinationsVerticalAdapter.setAdapterGroupedList(GroupedList);

                    });
        }

    }
}
