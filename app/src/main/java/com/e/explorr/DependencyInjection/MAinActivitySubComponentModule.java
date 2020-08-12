package com.e.explorr.DependencyInjection;

import androidx.lifecycle.ViewModel;

import com.e.explorr.DataSource.TripAdvisorSource;
import com.e.explorr.DestinationsRepository;
import com.e.explorr.ViewModel.MainActivityViewModel;
import com.e.explorr.ViewModel.MainActivityViewModelFactory;

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
