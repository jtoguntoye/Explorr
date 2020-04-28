package com.example.explorr.ui.nearbyPlaces;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NearbyPlacesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NearbyPlacesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}