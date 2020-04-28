package com.example.explorr;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

public class GeneralDestinations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_destinations);

        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getStringExtra(SearchManager.QUERY))){
            String query = intent.getStringExtra(SearchManager.QUERY);
        }
    }
}
