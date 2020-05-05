package com.example.explorr.API;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static volatile Retrofit retrofitInstance = null;
    private static String base_url= "https://tripadvisor1.p.rapidapi.com/";
    private static final String API_KEY = "4a7275b628msh5625a57913e87d3p164b25jsn5df82b134778";
    private static final String host_name = "tripadvisor1.p.rapidapi.com";

    private  static OkHttpClient customClient;



    private RetrofitClient(){}

    public  TripAdvisorInterface getRetrofitInstance() {

        if(retrofitInstance ==null){
            synchronized (RetrofitClient.class){
                if(retrofitInstance ==null){

                    retrofitInstance = new Retrofit.Builder()
                            .baseUrl(base_url)
                            .client(createCustomClient())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return retrofitInstance.create(TripAdvisorInterface.class);
    }

    //helper method to create a custom okHttp client
    private  OkHttpClient createCustomClient(){

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

}
