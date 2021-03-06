package com.e.explorr.API;

import com.e.explorr.BuildConfig;


import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkClientModule {
    private static volatile Retrofit retrofitInstance = null;
    private static String base_url= "https://tripadvisor1.p.rapidapi.com/";

    //INSERT YOUR OWN TRIP ADVISOR API KEY OBTAINED FROM RAPIDAPI.COM HERE BELOW
    private static final String API_KEY = BuildConfig.TRIP_ADVISOR_API_KEY;

    private static final String host_name = "tripadvisor1.p.rapidapi.com";


    private  static OkHttpClient customClient;


    //helper method to create a custom okHttp client
    @Provides
    static OkHttpClient createCustomClient(){

        //create a logging interceptor
        //also pass the api key and host string to the client to be used for all requests headers
        HttpLoggingInterceptor  loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        customClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();

                        Request newRequest = originalRequest.newBuilder()
                                .header("x-rapidapi-host", host_name)
                                .header("x-rapidapi-key", API_KEY)
                                .build();

                        return chain.proceed(newRequest);
                    }
                })
                .addInterceptor(loggingInterceptor).build();
        return customClient;
    }

    @Singleton
    @Provides
    public static Retrofit getRetrofitInstance(OkHttpClient customClient) {

        if(retrofitInstance == null){
            synchronized (NetworkClientModule.class){
                if(retrofitInstance ==null){

                    retrofitInstance = new Retrofit.Builder()
                            .baseUrl(base_url)
                            .client(customClient)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return retrofitInstance;
    }

    @Provides
    public static TripAdvisorInterface providesInterface(Retrofit retrofit){
        return retrofit.create(TripAdvisorInterface.class);
    }




}
