package com.e.explorr.ui.nearbyPlaces;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
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

import com.e.explorr.Adapters.GeneralDestinationsVerticalAdapter;
import com.e.explorr.ConnectivityUtils;
import com.e.explorr.Model.Destinations;
import com.e.explorr.R;
import com.e.explorr.ui.MainActivity;
import com.e.explorr.ViewModel.MainActivityViewModel;
import com.e.explorr.ViewModel.MainActivityViewModelFactory;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import static androidx.core.content.ContextCompat.checkSelfPermission;
import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG;

public class NearbyLocationsFragment extends Fragment {

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
    private boolean isConnected;
    private View hostView;


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

        hostView = requireActivity().findViewById(android.R.id.content);

        return inflater.inflate(R.layout.fragment_nearby, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mProgressBar = view.findViewById(R.id.Progress_Bar);
        mProgressBar.setVisibility(View.VISIBLE);

        isConnected = new ConnectivityUtils().checkConnectivity(requireActivity());

        RecyclerView verticalRecyclerView = view.findViewById(R.id.nearby_destinations__vertical_recyclerView);
        verticalRecyclerView.setHasFixedSize(true);
        verticalRecyclerView.setLayoutManager
                (new LinearLayoutManager(getContext(),RecyclerView.VERTICAL, false));
        verticalRecyclerView.setAdapter(generalDestinationsVerticalAdapter);

    }


    @Override
    public void onResume() {
        super.onResume();
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
                        mProgressBar.setVisibility(View.GONE);
                        generalDestinationsVerticalAdapter.setAdapterGroupedList(groupedList);
                    });

            mainActivityViewModel.getAttractionsByCoordinates(latitude, longitude).observe(this,
                    (List<Destinations> destinationList3) -> {
                        if (!groupedList.contains(destinationList3))
                            groupedList.add(destinationList3);
                        mProgressBar.setVisibility(View.GONE);
                        generalDestinationsVerticalAdapter.setAdapterGroupedList(groupedList);
                    });

        });

    }

    /*
     *  method to get the User's coordinates
     * */
    private void getLastKnownLocation() {
        if(!isConnected){
            mProgressBar.setVisibility(View.GONE);
            Snackbar snackbar = Snackbar
                    .make(hostView, R.string.Snack_bar_message, LENGTH_LONG)
                    .setAction(R.string.snack_bar_action, v -> getLastKnownLocation());
            snackbar.show();

        }
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                if (ActivityCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener
                        (task -> {
                            Location location = task.getResult();
                            if (location == null) {
                                requestNewLocationData();
                            } else {
                                mainActivityViewModel.setUserLocation(location);
                                getPlacesByCoordinates();
                                mLocation = location;
                            }
                        });

            } else {
                Toast.makeText(getContext(), R.string.message_turn_on_location, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, LOCATION_ENABLED_REQUEST_CODE);
            }
        } else {

            requestPermissions();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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