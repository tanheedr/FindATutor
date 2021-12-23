package com.example.findatutor.Networking;

import com.example.findatutor.Models.Message;
import com.example.findatutor.Models.Tutor;
import com.example.findatutor.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("getAllMessages.php")
    Call<List<User>> getUsers ();

    @GET("getTutors.php")
    Call<List<Tutor>> getTutors (@Query("Subjects") String subjectSearch);

    @GET("getChat.php")
    Call<List<Message>> getMessages();

}
