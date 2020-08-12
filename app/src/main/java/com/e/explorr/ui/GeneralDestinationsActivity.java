package com.e.explorr.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.e.explorr.Adapters.GeneralDestinationsVerticalAdapter;
import com.e.explorr.ConnectivityUtils;
import com.e.explorr.DependencyInjection.myApplication;
import com.e.explorr.Model.Destinations;
import com.e.explorr.R;
import com.e.explorr.ViewModel.GeneralDestinationsViewModel;
import com.e.explorr.ViewModel.GeneralDestinationsViewModelFactory;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG;


public class GeneralDestinationsActivity extends AppCompatActivity {

    @Inject
    GeneralDestinationsViewModelFactory generalDestinationsViewModelFactory;
    private GeneralDestinationsViewModel   generalDestinationsViewModel;
    private String locationId;
    private List<List<Destinations>> groupedList;
    private GeneralDestinationsVerticalAdapter generalDestinationsVerticalAdapter;
    private ProgressBar progressBar;
    private boolean isConnected = false;
    private CoordinatorLayout coordinatorLayout;


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

        progressBar = findViewById(R.id.Progress_Bar);
        progressBar.setVisibility(View.VISIBLE);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);

        isConnected = new ConnectivityUtils().checkConnectivity(this);


        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);

            setTitle(query);


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

            if(!isConnected) {
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, R.string.Snack_bar_message, LENGTH_LONG)
                        .setAction(R.string.snack_bar_action, v -> getLocationID(query));
                snackbar.show();
            }
            generalDestinationsViewModel.getLocationId(query).observe(this, (String s) -> {
                locationId = s;



                //get the hotels, restaurants and attractions for the queried location
                generalDestinationsViewModel.getHotelListResult(locationId).observe(this,

                        (List<Destinations> destinationList) -> {
                            if (!groupedList.contains(destinationList))
                                groupedList.add(destinationList);
                            progressBar.setVisibility(View.GONE);
                            generalDestinationsVerticalAdapter.setAdapterGroupedList(groupedList);

                        });


                generalDestinationsViewModel.getRestaurantResult(locationId).observe(this,
                        (List<Destinations> destinationlist1) -> {
                            if (!groupedList.contains(destinationlist1))
                                groupedList.add(destinationlist1);
                            progressBar.setVisibility(View.GONE);
                            generalDestinationsVerticalAdapter.setAdapterGroupedList(groupedList);

                        });


                generalDestinationsViewModel.getAttractionsResult(locationId).observe(this,
                        (List<Destinations> destinationlist2) -> {
                            if (!groupedList.contains(destinationlist2))
                                groupedList.add(destinationlist2);
                            progressBar.setVisibility(View.GONE);
                        });
                generalDestinationsVerticalAdapter.setAdapterGroupedList(groupedList);


            });

        }
    }



}

