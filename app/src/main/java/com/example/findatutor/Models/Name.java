package com.example.findatutor.Models;

import com.google.gson.annotations.SerializedName;

public class Name {

    @SerializedName("ID") private String ID;
    @SerializedName("FirstName") private String firstName;
    @SerializedName("Surname") private String surname;

    public String getID() {
        return ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }
}
