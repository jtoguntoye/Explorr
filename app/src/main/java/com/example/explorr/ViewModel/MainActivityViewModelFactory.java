package com.example.explorr.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

//create a ViewModelFactory to help with retaining the viewModel instance when configuration changes
public class MainActivityViewModelFactory implements ViewModelProvider.Factory{

    private final Map<Class<? extends ViewModel>, Provider<ViewModel>> mProviderMap;

    @Inject
    public MainActivityViewModelFactory( Map<Class<? extends ViewModel>, Provider<ViewModel>> mProviderMap){
        this.mProviderMap = mProviderMap;

    }


    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {


        return (T) mProviderMap.get(modelClass).get();
    }
}
