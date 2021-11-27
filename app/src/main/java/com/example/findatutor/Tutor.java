package com.example.findatutor;

import com.google.gson.annotations.SerializedName;

public class Tutor {

    @SerializedName("FullName") private String fullName;
    @SerializedName("Subjects") private String subjects;
    @SerializedName("HourlyCost") private String hourlyCost;
    @SerializedName("Qualifications") private String qualifications;
    @SerializedName("Description") private String description;

    public String getFullName() {
        return fullName;
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
