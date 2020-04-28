package com.example.explorr.ui.nearbyPlaces;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.explorr.R;

public class NearbyLocationsFragment extends Fragment {

    private NearbyPlacesViewModel nearbyPlacesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        nearbyPlacesViewModel =
                ViewModelProviders.of(this).get(NearbyPlacesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_nearby, container, false);
        final TextView textView = root.findViewById(R.id.text_nearby);
        nearbyPlacesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
