package com.example.explorr.DependencyInjection;

import com.example.explorr.API.NetworkClientModule;
import com.example.explorr.ui.GeneralDestinationsActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkClientModule.class,AppModule.class,MAinActivitySubComponentModule.class})
public interface AppComponent  {

    MainActivityComponent.Factory mainActivityComponent();
    void inject(GeneralDestinationsActivity generalDestinationsActivity);

}
