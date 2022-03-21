package com.example.findatutor.Models;

import com.google.gson.annotations.SerializedName;

public class Name {

    /*
    Used alongside NamesAdapter.
    Reads JSON response sent by getNames.php.
    The string in @SerializedName match with the keys of the array and bind the key to the variable created.
    They are accessible via get functions.
    */

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
