package com.example.explorr.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.explorr.Adapters.GeneralDestinationsVerticalAdapter;
import com.example.explorr.DependencyInjection.myApplication;
import com.example.explorr.Model.Destinations;
import com.example.explorr.R;
import com.example.explorr.ViewModel.GeneralDestinationsViewModel;
import com.example.explorr.ViewModel.GeneralDestinationsViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class GeneralDestinationsActivity extends AppCompatActivity {

    @Inject
    GeneralDestinationsViewModelFactory generalDestinationsViewModelFactory;
    private GeneralDestinationsViewModel   generalDestinationsViewModel;
    private String locationId;
    private List<List<Destinations>> groupedList;
    private GeneralDestinationsVerticalAdapter generalDestinationsVerticalAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((myApplication) getApplicationContext()).appComponent.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_destinations);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        generalDestinationsViewModel = ViewModelProviders.of(this,generalDestinationsViewModelFactory)
                .get(GeneralDestinationsViewModel.class);
        groupedList = new ArrayList<>();
        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);




        generalDestinationsVerticalAdapter =
                new GeneralDestinationsVerticalAdapter(this, new ArrayList<>()) ;
        RecyclerView verticalRecyclerView = findViewById(R.id.destinations__vertical_recyclerView);
        verticalRecyclerView.setHasFixedSize(true);
        verticalRecyclerView.setLayoutManager(new LinearLayoutManager
                (this,RecyclerView.VERTICAL,false));
        verticalRecyclerView.setAdapter(generalDestinationsVerticalAdapter);


           getLocationID(query);

        }

    }

    //helper method to get the locationID
    private void getLocationID(String query){
        //get location_id from TripAdvisor API
        if (query!=null) {
            Log.d("QUERYSTRING:", query);
            generalDestinationsViewModel.getLocationId(query).observe(this, (String s) -> {
                locationId = s;

                Log.d("ActivityLocationID", locationId);

                //get the hotels, restaurants and attractions for the queried location
                generalDestinationsViewModel.getHotelListResult(locationId).observe(this,
                        (List<Destinations> destinationList) -> {
                            if (!groupedList.contains(destinationList))
                                groupedList.add(destinationList);
                        });
                Log.d("GroupedListSIZE", String.valueOf(groupedList.size()));

                generalDestinationsViewModel.getRestaurantResult(locationId).observe(this,
                        (List<Destinations> destinationlist1) -> {
                            if (!groupedList.contains(destinationlist1))
                                groupedList.add(destinationlist1);

                        });
                Log.d("GroupSize:", String.valueOf(groupedList.size()));

                generalDestinationsViewModel.getAttractionsResult(locationId).observe(this,
                        (List<Destinations> destinationlist2) -> {
                            if (!groupedList.contains(destinationlist2))
                                groupedList.add(destinationlist2);
                        });
                generalDestinationsVerticalAdapter.setAdapterGroupedList(groupedList);
                Log.d("GroupSize:", String.valueOf(groupedList.size()));
            });

        }
    }

}

