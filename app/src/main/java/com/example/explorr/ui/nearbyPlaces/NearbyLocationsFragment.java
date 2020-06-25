package com.example.explorr.ui.nearbyPlaces;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.explorr.Adapters.GeneralDestinationsVerticalAdapter;
import com.example.explorr.Model.Destinations;
import com.example.explorr.R;
import com.example.explorr.ui.MainActivity;
import com.example.explorr.ViewModel.MainActivityViewModel;
import com.example.explorr.ViewModel.MainActivityViewModelFactory;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import static androidx.core.content.ContextCompat.checkSelfPermission;

public class NearbyLocationsFragment extends Fragment {
    public static final String GROUPED_LIST_SIZE = "GroupedListSIZE";
    private Location mLocation;
    private double latitude;
    private double longitude;
    private static final int PERMISSION_ID = 200;
    private static final int LOCATION_ENABLED_REQUEST_CODE = 300;
    private List<List<Destinations>> groupedList;

    private ProgressBar mProgressBar;
    @Inject
    MainActivityViewModelFactory viewModelFactory;
    private MainActivityViewModel mainActivityViewModel;
    private GeneralDestinationsVerticalAdapter generalDestinationsVerticalAdapter;

    //client object to use to get the user's location coordinates
    private FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        //obtain the dependency graph from the MainActivity and instantiate the @inject fields
        ((MainActivity) requireActivity()).mainActivityComponent.inject(this);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mainActivityViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel.class);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        groupedList = new ArrayList<>();
        generalDestinationsVerticalAdapter =
                new GeneralDestinationsVerticalAdapter(getContext(), new ArrayList<>());

        return inflater.inflate(R.layout.fragment_nearby, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mProgressBar = view.findViewById(R.id.simpleProgressBar);
        mProgressBar.setVisibility(View.VISIBLE);

        RecyclerView verticalRecyclerView = view.findViewById(R.id.nearby_destinations__vertical_recyclerView);
        verticalRecyclerView.setHasFixedSize(true);
        verticalRecyclerView.setLayoutManager
                (new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        verticalRecyclerView.setAdapter(generalDestinationsVerticalAdapter);

    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("ONRESUME_TAG", "onResume is called");
        getLastKnownLocation();
    }


    private void getPlacesByCoordinates() {
        //get the user's location from viewModel
        mainActivityViewModel.getUserLocation().observe(this, location -> {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            mainActivityViewModel.getHotelsByCoordinates(latitude, longitude).observe(this,
                    destinationsList -> {
                        if (!groupedList.contains(destinationsList))
                            groupedList.add(destinationsList);

                       mProgressBar.setVisibility(View.GONE);
                        generalDestinationsVerticalAdapter.setAdapterGroupedList(groupedList);
                    });

            mainActivityViewModel.getRestaurantsByCoordinates(latitude, longitude).observe(this,
                    (List<Destinations> destinationList2) -> {
                        if (!groupedList.contains(destinationList2))
                            groupedList.add(destinationList2);
                        Log.d(GROUPED_LIST_SIZE, String.valueOf(groupedList.size()));
                        generalDestinationsVerticalAdapter.setAdapterGroupedList(groupedList);
                    });

            mainActivityViewModel.getAttractionsByCoordinates(latitude, longitude).observe(this,
                    (List<Destinations> destinationList3) -> {
                        if (!groupedList.contains(destinationList3))
                            groupedList.add(destinationList3);
                        Log.d(GROUPED_LIST_SIZE, String.valueOf(groupedList.size()));
                        generalDestinationsVerticalAdapter.setAdapterGroupedList(groupedList);
                    });

        });

    }

    /*
     *  method to get the User's coordinates
     * */
    private void getLastKnownLocation() {
        Log.d("Method called:", "getLastKnownLocation");
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener
                        (task -> {
                            Location location = task.getResult();
                            if (location == null) {
                                Log.d("location null:", "requestNewLocation() method is called");
                                requestNewLocationData();
                            } else {
                                mainActivityViewModel.setUserLocation(location);
                                getPlacesByCoordinates();
                                mLocation = location;
                                Log.d("1stCoordinates", mLocation.getLatitude() + "" + mLocation.getLongitude());
                            }
                        });

            } else {
                Toast.makeText(getContext(), "Turn on location", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, LOCATION_ENABLED_REQUEST_CODE);
            }
        } else {
            Log.d("permission request:", "requestPermission() method will be called");
            requestPermissions();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG", "onActivityResult: called.");
        if (requestCode == LOCATION_ENABLED_REQUEST_CODE) {
            getLastKnownLocation();
        }
    }

    private void requestNewLocationData() {

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setFastestInterval(0);
        locationRequest.setInterval(0);
        //requesting one-time location update
        locationRequest.setNumUpdates(1);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                mLocationCallback,
                Looper.myLooper()
        );
    }

    private LocationCallback mLocationCallback = new LocationCallback(){
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Log.d("LocationCallback","OnLocationResult is called");
                mainActivityViewModel.setUserLocation(locationResult.getLastLocation());
            getPlacesByCoordinates();

        }
    };



    //method called when a User allow or deny a permission
    @Override
    public void onRequestPermissionsResult(int requestCode,
                @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID){
            if(grantResults.length>0 &&grantResults[0]==PackageManager.PERMISSION_GRANTED){

                //get user location here
                getLastKnownLocation();
            }
        }
    }



    private boolean checkPermissions(){
        return checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions(){
        ActivityCompat.requestPermissions(
                requireActivity(),
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                PERMISSION_ID
        );

    }

    private boolean isLocationEnabled(){
        LocationManager locationManager = (LocationManager)
                requireActivity().getSystemService(Context.LOCATION_SERVICE);
        return Objects.requireNonNull(locationManager).isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

    }

}