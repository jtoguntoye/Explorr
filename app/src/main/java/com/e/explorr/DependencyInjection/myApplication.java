package com.e.explorr.DependencyInjection;

import android.app.Application;

// appComponent lives in the Application class to share its lifecycle
public class myApplication extends Application {

    public AppComponent appComponent = DaggerAppComponent.builder()
            .appModule(new AppModule(this))
            .build();

}
