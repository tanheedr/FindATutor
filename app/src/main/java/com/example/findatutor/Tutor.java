package com.example.findatutor;

import com.google.gson.annotations.SerializedName;

public class Tutor {

    @SerializedName("FirstName") private String firstName;
    @SerializedName("Surname") private String surname;
    //@SerializedName("FullName") private String fullName;
    @SerializedName("Subjects") private String subjects;
    @SerializedName("HourlyCost") private String hourlyCost;
    @SerializedName("Qualifications") private String qualifications;
    @SerializedName("Description") private String description;

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    /*public String getFullName() {
        return fullName;
    }*/

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
