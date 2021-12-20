package com.example.findatutor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("getAllMessages.php")
    Call<List<User>> getUsers ();

    @GET("getTutorData.php")
    Call<List<Tutor>> getTutors (@Query("subject") String subjectSearch);

}
