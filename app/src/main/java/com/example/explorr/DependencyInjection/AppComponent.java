package com.example.explorr.DependencyInjection;

import com.example.explorr.API.NetworkClientModule;
import com.example.explorr.ui.GeneralDestinationsActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkClientModule.class,AppModule.class})
public interface AppComponent  {

    void inject(GeneralDestinationsActivity generalDestinationsActivity);

}
