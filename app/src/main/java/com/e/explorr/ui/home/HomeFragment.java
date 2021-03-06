package com.e.explorr.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.e.explorr.R;

public class HomeFragment extends Fragment {


    private ImageView hotelView,restaurantView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        hotelView = view.findViewById(R.id.hotels_icon);
        restaurantView = view.findViewById(R.id.restaurants_icon);
        //use Navigation to go to another destination in the nav_graph
       // hotelView.setOnClickListener( Navigation.createNavigateOnClickListener
         //       (R.id.action_navigation_home_to_navigation_favorites,null));

    }
}
