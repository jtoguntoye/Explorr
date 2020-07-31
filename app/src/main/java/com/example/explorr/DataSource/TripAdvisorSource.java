package com.example.explorr.DataSource;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.explorr.API.TripAdvisorInterface;
import com.example.explorr.Model.DestinationSpecificResponse;
import com.example.explorr.Model.Destinations;
import com.example.explorr.Model.LocationSearchResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripAdvisorSource {

    private TripAdvisorInterface mTripAdvisorInterface;
    private MutableLiveData<List<Destinations>> HotelSearchResult, restaurantSearchResult,
            attractionSearchResult;
    private MutableLiveData<String> locationId;
    private static final int RADIUS_OF_RESTAURANT_SEARCH = 10;
    private static final int RADIUS_OF_ATTRACTION_SEARCH = 25;



    @Inject
    public TripAdvisorSource(TripAdvisorInterface TripAdvisorInterface){
    this.mTripAdvisorInterface = TripAdvisorInterface;
    HotelSearchResult = new MutableLiveData<>();
    locationId = new MutableLiveData<>();
    restaurantSearchResult = new MutableLiveData<>();
    attractionSearchResult = new MutableLiveData<>();

}




        public MutableLiveData<String> getLocationResponseId(String location){

            mTripAdvisorInterface.getLocationResponse(location).enqueue
        (new Callback<LocationSearchResponse>() {
    @Override
    public void onResponse( Call<LocationSearchResponse> call,
                            Response<LocationSearchResponse> response) {

            if(response.body()!=null){

        locationId.postValue(Objects.requireNonNull(response.body()).getLocations().
                get(0).getLocationObject().getLocation_id());


            }
        }



    @Override
    public void onFailure(Call<LocationSearchResponse> call, Throwable t) {
        t.printStackTrace();
    }
});


return locationId;
}


public MutableLiveData<List<Destinations>> getHotelDestinationResponse(String locationId){

        mTripAdvisorInterface.getHotelResponse(locationId)
                .enqueue(new Callback<DestinationSpecificResponse>() {
            @Override
            public void onResponse(Call<DestinationSpecificResponse> call,
                                   Response<DestinationSpecificResponse> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        if(!response.body().getPagingInfo().getResults().equals("0"))
                        HotelSearchResult.postValue(response.body().getDestinationsList());

                    }
                }
            }

            @Override
            public void onFailure(Call<DestinationSpecificResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return HotelSearchResult;
}

public MutableLiveData<List<Destinations>> getRestaurantResponse(String locationId){
        mTripAdvisorInterface.getRestaurantResponse(locationId)
                .enqueue(new Callback<DestinationSpecificResponse>() {
            @Override
            public void onResponse(Call<DestinationSpecificResponse> call,
                                   Response<DestinationSpecificResponse> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        if(response.body().getDestinationsList().size()!=0)
                        restaurantSearchResult.postValue(response.body().getDestinationsList());
                    }
                }
            }

            @Override
            public void onFailure(Call<DestinationSpecificResponse> call, Throwable t) {
                t.printStackTrace();

            }
        });
        return restaurantSearchResult;
}

    public MutableLiveData<List<Destinations>> getAttractionsResponse(String locationID) {
        mTripAdvisorInterface.getAttractionsResponse(locationID)
                .enqueue(new Callback<DestinationSpecificResponse>() {
            @Override
            public void onResponse(@NotNull Call<DestinationSpecificResponse> call,
                                   Response<DestinationSpecificResponse> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        if(response.body().getDestinationsList().size()!=0)
                        attractionSearchResult.postValue(response.body().getDestinationsList());

                    }

                }
            }

            @Override
            public void onFailure(Call<DestinationSpecificResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    return attractionSearchResult;}


    //method to get list of hotels by longitude and latitude
    public LiveData<List<Destinations>> getHotelsByLatLng(double lat, double longitude){
            mTripAdvisorInterface.getHotelsResponseByLatLng(lat,longitude)
                    .enqueue(new Callback<DestinationSpecificResponse>() {
                @Override
                public void onResponse(Call<DestinationSpecificResponse> call,
                                       Response<DestinationSpecificResponse> response) {

                    if(response.isSuccessful()){
                        if(response.body()!=null){

                            if(response.body().getDestinationsList().size()!=0)
                            HotelSearchResult.postValue(response.body().getDestinationsList());

                        }
                    }

                }

                @Override
                public void onFailure(Call<DestinationSpecificResponse> call, Throwable t) {
                t.printStackTrace();
                }
            });

    return HotelSearchResult;
}

 public LiveData<List<Destinations>> getRestaurantsByLatLng(double latitude, double longitude) {
        mTripAdvisorInterface.getRestaurantResponseByLatLng(latitude,longitude, RADIUS_OF_RESTAURANT_SEARCH)
                .enqueue(new Callback<DestinationSpecificResponse>() {
                    @Override
                    public void onResponse(Call<DestinationSpecificResponse> call,
                                           Response<DestinationSpecificResponse> response) {

                        if(response.isSuccessful()){
                            if(response.body()!=null){
                                if(!response.body().getPagingInfo().getResults().equals("0"))
                                restaurantSearchResult.postValue(response.body().getDestinationsList());

                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<DestinationSpecificResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

        return restaurantSearchResult;

    }

    public LiveData<List<Destinations>> getAttractionsByLatLng(double Lat, double Longitude){
        mTripAdvisorInterface.getAttractionsResponseByLatLng(Lat,Longitude, RADIUS_OF_ATTRACTION_SEARCH)
                .enqueue(new Callback<DestinationSpecificResponse>() {
                    @Override
                    public void onResponse(Call<DestinationSpecificResponse> call,
                                           Response<DestinationSpecificResponse> response) {

                        if(response.isSuccessful()){
                            if(response.body()!=null){
                                if(!response.body().getPagingInfo().getResults().equals("0"))
                                attractionSearchResult.postValue(response.body().getDestinationsList());

                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<DestinationSpecificResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

        return attractionSearchResult;

    }
}
