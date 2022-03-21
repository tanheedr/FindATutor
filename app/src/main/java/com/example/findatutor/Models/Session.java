package com.example.findatutor.Models;

import com.google.gson.annotations.SerializedName;

public class Session {

    /*
    Used alongside SessionsAdapter.
    Reads JSON response sent by getSessions.php.
    The string in @SerializedName match with the keys of the array and bind the key to the variable created.
    They are accessible via get functions.
    */

    @SerializedName("ID") private String ID;
    @SerializedName("FirstName") private String firstName;
    @SerializedName("Surname") private String surname;
    @SerializedName("StartTime") private String startTime;
    @SerializedName("EndTime") private String endTime;

    public String getID(){
        return ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getStartTime(){
        return startTime.substring(11, startTime.length() - 3);
    }
    // Returns (hh:mm)

    public String getEndTime(){
        return endTime.substring(11, endTime.length() - 3);
    }
    // Returns (hh:mm)

}
