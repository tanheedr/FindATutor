package com.example.findatutor.Networking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = Constants.BASE_URL;
    public static Retrofit retrofit;

    /*
    GSON is a Java API
    Converts Java objects into JSON representation and vice versa
    Serializes/Parses the objects
    */
    public static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    /*
    Retrofit is a REST client for Java and Android
    Allows JSON retrieval and uploading
    */
    public static Retrofit getApiClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}