package com.example.explorr.ui;

import android.app.SearchManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.example.explorr.DependencyInjection.MainActivityComponent;
import com.example.explorr.DependencyInjection.myApplication;
import com.example.explorr.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import static androidx.navigation.Navigation.findNavController;

public class MainActivity extends AppCompatActivity {
    //keep a reference to the MainComponent here since the MainComponent is attached to the
    //lifeycle of the MainActivity
     public MainActivityComponent mainActivityComponent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //creation of the MainActivityComponent using the application graph
        mainActivityComponent = ((myApplication) getApplicationContext())
                .appComponent.mainActivityComponent().create();
        mainActivityComponent.inject(this);
        super.onCreate(savedInstanceState);


        setContentView(R.layout.parent_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_favorites, R.id.navigation_nearby_places)
                .build();
        NavController navController = findNavController( this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);

        //get the searchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_icon).getActionView();
        searchView.setSearchableInfo((searchManager != null) ?
                searchManager.getSearchableInfo(new ComponentName(this, GeneralDestinationsActivity.class)) : null);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.privacy_policy) {

            AlertDialog dialog =  new
                    MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
                    .setTitle("Privacy Policy")
                    .setMessage(R.string.privacy_policy_details)
                    .setNegativeButton("Close",(dialog1, which) -> dialog1.cancel()).create();
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
