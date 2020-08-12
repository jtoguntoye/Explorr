package com.e.explorr.DependencyInjection;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.e.explorr.DataSource.TripAdvisorSource;
import com.e.explorr.ViewModel.GeneralDestinationsViewModel;
import com.e.explorr.ViewModel.GeneralDestinationsViewModelFactory;

import java.util.Map;

import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
public class AppModule {

    Application mApplication;

    public AppModule(Application application) {

        mApplication = application;
    }

    @Provides
    GeneralDestinationsViewModelFactory provideGeneralDestinationFactory
            (Map<Class<? extends ViewModel>, Provider<ViewModel>> providerMap){
        return new GeneralDestinationsViewModelFactory(providerMap);
    }


    @Provides
    @IntoMap
    @ViewModelKey(GeneralDestinationsViewModel.class)
    ViewModel provideGeneralDestinationViewModel(TripAdvisorSource tripAdvisorSource){
    return new GeneralDestinationsViewModel(tripAdvisorSource);
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }
}
