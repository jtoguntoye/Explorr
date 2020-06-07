package com.example.explorr;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.explorr.Model.Destinations;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class PlacesDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ImageView destinationImage;
    private TextView destinationDescription, destinationAddress;

    private MapView mapView;

    private String imageUrl;
    public static final  String PARCELED_DESTINATION = "PARCELED DESTINATION";
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    private Destinations destination;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        destinationImage =findViewById(R.id.destination_detail_imageView);
        destinationDescription = findViewById(R.id.destination_description);
        destinationAddress = findViewById(R.id.destination_address);
        mapView = findViewById(R.id.mapView);

        Bundle mapViewBundle = null;
        if(savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }


        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);


        readDestinationDetails();


    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onStart() {
        mapView.onStart();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mapView.onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onLowMemory() {
        mapView.onLowMemory();
        super.onLowMemory();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_fav_menu, menu);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
    googleMap.setMyLocationEnabled(true);
    }

    private void readDestinationDetails() {
        Intent DestinationIntent = getIntent();

        if(DestinationIntent!= null && DestinationIntent.hasExtra(PARCELED_DESTINATION)) {
            destination = DestinationIntent.getParcelableExtra(PARCELED_DESTINATION);
            if(destination!= null) {
                setTitle(destination.getDestinationName());
                if(destination.getPhotos()!=null){
                    if(destination.getPhotos().getImages().getOriginalImage().getUrl() != null)
           imageUrl =  destination.getPhotos().getImages().getOriginalImage().getUrl();
                Glide.with(this)
                        .load(imageUrl)
                        .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                        .into(destinationImage);
                }
                else {
                    destinationImage.setImageResource(R.mipmap.default_thumbnail);
                }


                if(destination.getDestinationAddress()!=null) {
                    destinationAddress.setText(destination.getDestinationAddress());
                }
                else{
                    destinationAddress.setText("Address not available");
                }
                if(destination.getDestinationDescription() !=null) {
                    destinationDescription.setText(destination.getDestinationDescription());
                }
                else{
                    destinationDescription.setText("Overview not available");
                }
            }


        }
    }


}
