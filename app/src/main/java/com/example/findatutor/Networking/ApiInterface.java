package com.example.findatutor.Networking;

import com.example.findatutor.Models.Chat;
import com.example.findatutor.Models.Name;
import com.example.findatutor.Models.Session;
import com.example.findatutor.Models.Tutor;
import com.example.findatutor.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("getUsers.php")
    Call<List<User>> getUsers(@Query("ID") String ID);

    @GET("getTutors.php")
    Call<List<Tutor>> getTutors(@Query("ID") String ID, @Query("Subjects") String subjectSearch);

    @GET("getChat.php")
    Call<List<Chat>> getChat(@Query("SenderID") String ID, @Query("RecipientID") String RecipientID);

    @GET("getNames.php")
    Call<List<Name>> getNames(@Query("ID") String ID);

    @GET("getSessions.php")
    Call<List<Session>> getSessions(@Query("ID") String ID, @Query("Date") String Date);

}
