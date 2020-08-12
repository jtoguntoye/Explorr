package com.e.explorr.DependencyInjection;

import com.e.explorr.API.NetworkClientModule;
import com.e.explorr.ui.GeneralDestinationsActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkClientModule.class,AppModule.class,MAinActivitySubComponentModule.class})
public interface AppComponent  {

    MainActivityComponent.Factory mainActivityComponent();
    void inject(GeneralDestinationsActivity generalDestinationsActivity);

}
