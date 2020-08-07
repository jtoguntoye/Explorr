package com.example.explorr.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.explorr.Model.Destinations;
import com.example.explorr.R;
import com.example.explorr.ViewModel.PlacesDetailsViewModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PlacesDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int ERROR_DIALOG_REQUEST = 400;
    private static final int PERMISSION_ID = 200;
    private static final int LOCATION_ENABLE_REQUEST = 300;

    private ImageView destinationImage;
    private TextView destinationDescription, destinationAddress;
    private String destinationId;

    private PlacesDetailsViewModel placesDetailsViewModel;
    private MapView mapView;

    private String imageUrl;
    public static final  String PARCELED_DESTINATION = "PARCELED DESTINATION";
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    private Destinations destination;
    private boolean mLocationPermissionGranted =false;

    //initializing the favorites boolean to false
    private Boolean isFavorites = false;


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


        readDestinationDetails();

        placesDetailsViewModel = ViewModelProviders.of(this)
                .get(PlacesDetailsViewModel.class);

        initGoogleMaps(savedInstanceState);

        initFavoriteButton (destinationId);

    }

    private void initFavoriteButton(String id) {

    }

    private void initGoogleMaps(Bundle savedInstanceState) {
        Bundle mapViewBundle = null;
        if(savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
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
    protected void onResume() {
        mapView.onResume();
        super.onResume();

        if(checkMapServices()){
            if(mLocationPermissionGranted){
               mapView.getMapAsync(this);
            }
            else{
                getLocationPermission();
            }
        }

    }

    private void getLocationPermission() {

        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            mapView.getMapAsync(this);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_ID);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        mLocationPermissionGranted = false;
        if (requestCode == PERMISSION_ID) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
            }
        }
    }

    private boolean checkMapServices() {

        if(isServicesOK()){
            return isMapsEnabled();
        }
        return false;
    }

    private boolean isMapsEnabled() {
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                      Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, LOCATION_ENABLE_REQUEST);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == LOCATION_ENABLE_REQUEST) {
            if (mLocationPermissionGranted) {
               mapView.getMapAsync(this);
            } else {
                getLocationPermission();
            }
        }
    }

    private boolean isServicesOK() {


        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this, available,
                    ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_fav_menu, menu);

        // initialize the status of the favorite menu item
        MenuItem favMenuItem = menu.findItem(R.id.add_to_favorites);

        placesDetailsViewModel.getFavorite(destinationId).observe(this , Destinations ->{
            if (Destinations!=null) {
                isFavorites = true;
                favMenuItem.setIcon(R.drawable.ic_favorite_black_24dp);
            }

            else {
                isFavorites = false;
                favMenuItem.setIcon(R.drawable.ic_favorite_border_24px);

            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add_to_favorites) {
            if(!isFavorites) {
                placesDetailsViewModel.insertFavorite(destination);
                Toast.makeText(this, R.string.message_favorite_added, Toast.LENGTH_SHORT).show();
                item.setIcon(R.drawable.ic_favorite_black_24dp);
                isFavorites = true;
            }

            else {
            placesDetailsViewModel.deleteFromFavorites(destination);
            Toast.makeText(this, R.string.message_favorite_deleted, Toast.LENGTH_SHORT).show();
            item.setIcon(R.drawable.ic_favorite_border_24px);
            isFavorites = false;
            }

        }
        return super.onOptionsItemSelected(item);
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
    public void onLowMemory() {
        mapView.onLowMemory();
        super.onLowMemory();
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(destination.getLongitude()!=null && destination.getLatitude()!=null)
            googleMap.addMarker(new MarkerOptions().position
                (new LatLng(Double.parseDouble(destination.getLatitude()), Double.parseDouble(destination.getLongitude()))));
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
                destinationId = destination.getDestinationId();


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
                    if(destination.getDestinationAddress().length()!=0)
                        destinationAddress.setText(destination.getDestinationAddress());
                }
                else{
                    destinationAddress.setText(R.string.message_address_not_available);
                }
                if(destination.getDestinationDescription()!=null) {
                    if(destination.getDestinationDescription().length()!= 0)
                        destinationDescription.setText(destination.getDestinationDescription());
                }
                 else{
                    destinationDescription.setText(R.string.message_overview_not_available);
                }
            }


        }
    }


}
