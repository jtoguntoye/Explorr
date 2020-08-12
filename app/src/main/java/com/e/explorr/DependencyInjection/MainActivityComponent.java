package com.e.explorr.DependencyInjection;

import com.e.explorr.ui.MainActivity;
import com.e.explorr.ui.favorites.FavoritesFragment;
import com.e.explorr.ui.nearbyPlaces.NearbyLocationsFragment;

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
