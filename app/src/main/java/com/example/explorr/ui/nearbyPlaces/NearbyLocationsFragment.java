package com.example.explorr.ui.nearbyPlaces;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.explorr.Adapters.GeneralDestinationsVerticalAdapter;
import com.example.explorr.Model.Destinations;
import com.example.explorr.R;
import com.example.explorr.ui.GeneralDestinationsActivity;
import com.example.explorr.ui.MainActivity;
import com.example.explorr.ui.MainActivityViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class NearbyLocationsFragment extends Fragment {
    private Location mLocation;
   private double latitude, longitude;
    private static final int PERMISSION_ID = 200;
    private List<List<Destinations>> GroupedList;

    @Inject
    MainActivityViewModel mainActivityViewModel;
    private GeneralDestinationsVerticalAdapter generalDestinationsVerticalAdapter;

    //client object to use to get the user's location coordinates
    private FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        //obtain the dependency graph from the MainActivity and instantiate the @inject fields
        ((MainActivity) requireActivity()).mainActivityComponent.inject(this);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.d("Callback called:", "OnCreateView");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        GroupedList = new ArrayList<>();
        generalDestinationsVerticalAdapter =
                new GeneralDestinationsVerticalAdapter(getContext(), new ArrayList<>()) ;

        return inflater.inflate(R.layout.fragment_nearby, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("Callback called:", "OnViewCreated");

        RecyclerView verticalRecyclerView = view.findViewById(R.id.nearby_destinations__vertical_recyclerView);
        verticalRecyclerView.setHasFixedSize(true);
        verticalRecyclerView.setLayoutManager
                (new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        verticalRecyclerView.setAdapter(generalDestinationsVerticalAdapter);


    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("ONRESUME_TAG", "onResume is called");
        getLastKnownLocation();
    }


    private void getPlacesByCoordinates(Location location){

        latitude = location.getLatitude();
        longitude  = location.getLongitude();
        mainActivityViewModel.getHotelsByCoordinates(latitude,longitude).observe(this,
                (List<Destinations> destinationsList) -> {
            if(!GroupedList.contains(destinationsList))
            GroupedList.add(destinationsList);
                    Log.d("GroupedListSIZE",String.valueOf(GroupedList.size()) );
                    generalDestinationsVerticalAdapter.setAdapterGroupedList(GroupedList);
                });

        mainActivityViewModel.getRestaurantsByCoordinates(latitude,longitude).observe(this,
                (List<Destinations> destinationList2)->{
            if(!GroupedList.contains(destinationList2))
                GroupedList.add(destinationList2);
                    Log.d("GroupedListSIZE",String.valueOf(GroupedList.size()) );
                    generalDestinationsVerticalAdapter.setAdapterGroupedList(GroupedList);
                });

        mainActivityViewModel.getAttractionsByCoordinates(latitude,longitude).observe(this,
                (List<Destinations> destinationList3) ->{
            if(!GroupedList.contains(destinationList3))
            GroupedList.add(destinationList3);
                    Log.d("GroupedListSIZE",String.valueOf(GroupedList.size()) );
                    generalDestinationsVerticalAdapter.setAdapterGroupedList(GroupedList);
                });


    }

    /*
    *  method to get the User's coordinates
    * */
    private void getLastKnownLocation(){
    Log.d("Method called:","getLastKnownLocation");
        if(checkPermissions()){
            if(isLocationEnabled()){
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener
                        (task -> {
                            Location location = task.getResult();
                            if(location==null){
                                Log.d("location null:", "requestNewLocation() method is called");
                                requestNewLocationData();
                            }
                            else{
                                getPlacesByCoordinates(location);
                                mLocation = location;
                                Log.d("1stCoordinates", mLocation.getLatitude()+ "" +mLocation.getLongitude());
                            }
                        });

            }
            else{
                Toast.makeText(getContext(),"Turn on location",Toast.LENGTH_SHORT).show();
                Intent intent  = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        }
        else {
            Log.d("permission request:","requestPermission() method will be called");
            requestPermissions();
        }


    }

    private void requestNewLocationData() {

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setFastestInterval(0);
        locationRequest.setInterval(0);
        locationRequest.setNumUpdates(1);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
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
                Location mLocation = locationResult.getLastLocation();
            getPlacesByCoordinates(mLocation);

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
        return ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
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
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("ONDESTROYVIEW_TAG", "onDestroyView is called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("ONDESTROY_TAG", "onDestroy is called");
    }
    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("ONDETACHED_TAG", "onDetached is called");
    }

}






