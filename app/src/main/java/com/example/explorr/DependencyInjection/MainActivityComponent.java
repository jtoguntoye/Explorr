package com.example.explorr.DependencyInjection;

import com.example.explorr.ui.MainActivity;
import com.example.explorr.ui.favorites.FavoritesFragment;
import com.example.explorr.ui.nearbyPlaces.NearbyLocationsFragment;

import dagger.Subcomponent;

@MainActivityScope
@Subcomponent
public interface MainActivityComponent {

    @Subcomponent.Factory
    interface Factory{
        MainActivityComponent create();
    }
    void inject(MainActivity mainActivity);
    void inject(NearbyLocationsFragment nearbyLocationsFragment);
    void inject(FavoritesFragment favoritesFragment);

}
