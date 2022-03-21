package com.example.findatutor.Networking;

import com.example.findatutor.Models.Chat;
import com.example.findatutor.Models.Name;
import com.example.findatutor.Models.Session;
import com.example.findatutor.Models.Tutor;
import com.example.findatutor.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    /*
    Interface to get JSON requests and put them into a list
    For query parameters with Retrofit interface, @Query must be used
    Every method is a singular API call
    Return value wrapped in a Call object
    */

    @GET("getUsers.php")
    Call<List<User>> getUsers(@Query("ID") String ID);
    // GET request with getUsers.php which uses ID as a parameter

    @GET("getTutors.php")
    Call<List<Tutor>> getTutors(@Query("ID") String ID, @Query("Subjects") String subjectSearch);
    // GET request with getTutors.php which uses ID and subjectSearch as parameters

    @GET("getChat.php")
    Call<List<Chat>> getChat(@Query("SenderID") String ID, @Query("RecipientID") String RecipientID);
    // GET request with getChat.php which uses ID and RecipientID as parameters

    @GET("getNames.php")
    Call<List<Name>> getNames(@Query("ID") String ID);
    // GET request with getNames.php which uses ID as a parameter

    @GET("getSessions.php")
    Call<List<Session>> getSessions(@Query("ID") String ID, @Query("Date") String Date);
    // GET request with getSessions.php which uses ID and Data as parameters

}
