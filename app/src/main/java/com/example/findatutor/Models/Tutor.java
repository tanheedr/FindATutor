package com.example.findatutor.Models;

import com.google.gson.annotations.SerializedName;

public class Tutor {

    /*
    Used alongside TutorsAdapter.
    Reads JSON response sent by getTutors.php.
    The string in @SerializedName match with the keys of the array and bind the key to the variable created.
    They are accessible via get functions.
    */

    @SerializedName("ID") private String ID;
    @SerializedName("FirstName") private String firstName;
    @SerializedName("Surname") private String surname;
    @SerializedName("Photo") private String photo;
    @SerializedName("Subjects") private String subjects;
    @SerializedName("HourlyCost") private String hourlyCost;
    @SerializedName("Qualifications") private String qualifications;
    @SerializedName("Description") private String description;

    public String getID(){
        return ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhoto(){
        return photo;
    }

    public String getSubjects() {
        return subjects;
    }

    public String getHourlyCost() {
        return hourlyCost;
    }

    public String getQualifications() {
        return qualifications;
    }

    public String getDescription() {
        return description;
    }
}
