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
import com.example.explorr.ui.nearbyPlaces.GeneralDestinationsViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class GeneralDestinationsActivity extends AppCompatActivity {

    @Inject
    GeneralDestinationsViewModelFactory generalDestinationsViewModelFactory;
    private GeneralDestinationsViewModel   generalDestinationsViewModel;
    private String locationId;
    private List<List<Destinations>> GroupedList;
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
        GroupedList = new ArrayList<>();
        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d("QUERYSTRING:", query);



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
        generalDestinationsViewModel.getLocationId(query).observe(this, (String s) -> {
           locationId =s;

            Log.d("ActivityLocationID", locationId);

            //get the hotels, restaurants and attractions for the queried location
            generalDestinationsViewModel.getHotelListResult(locationId).observe(this,
                    (List<Destinations> destinationList) ->{
                if(!GroupedList.contains(destinationList))
                        GroupedList.add(destinationList);
                        Log.d("GroupedListSIZE",String.valueOf(GroupedList.size()) );

                    });

            generalDestinationsViewModel.getRestaurantResult(locationId).observe(this,
                    (List<Destinations> destinationlist1)->{
                        if(!GroupedList.contains(destinationlist1))
                GroupedList.add(destinationlist1);
                Log.d("GroupSize:", String.valueOf(GroupedList.size()));
                    });

            generalDestinationsViewModel.getAttractionsResult(locationId).observe(this,
                    (List<Destinations> destinationlist2) ->{
                        if(!GroupedList.contains(destinationlist2))
                        GroupedList.add(destinationlist2);
                        Log.d("GroupSize:", String.valueOf(GroupedList.size()));
                        generalDestinationsVerticalAdapter.setAdapterGroupedList(GroupedList);
                    });

        });


    }

}

