package com.example.explorr.DependencyInjection;

import androidx.lifecycle.ViewModel;

import com.example.explorr.DataSource.TripAdvisorSource;
import com.example.explorr.DestinationsRepository;
import com.example.explorr.ViewModel.MainActivityViewModel;
import com.example.explorr.ViewModel.MainActivityViewModelFactory;

import java.util.Map;

import javax.inject.Provider;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module(subcomponents=MainActivityComponent.class)
public class MAinActivitySubComponentModule {

    @Provides
      MainActivityViewModelFactory  provideMainViewModelFactory
             (Map<Class<? extends ViewModel>, Provider<ViewModel>> providerMap){
      return new MainActivityViewModelFactory(providerMap);
    }

    //add provider method for the MainActivityViewModel
    @Provides
    @IntoMap
    @ViewModelKey(MainActivityViewModel.class)
    ViewModel provideMainActivityViewModel(TripAdvisorSource tripAdvisorSource, DestinationsRepository repository){
        return new MainActivityViewModel(tripAdvisorSource, repository);
    }



}
