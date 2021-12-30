package com.example.findatutor.Models;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("RecipientID") private String recipientID;
    @SerializedName("FirstName") private String firstName;
    @SerializedName("Surname") private String surname;
    @SerializedName("Photo") private String photo;
    @SerializedName("Message") private String lastMessage;
    @SerializedName("Timestamp") private String timestamp;

    public String getRecipientID() {
        return recipientID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhoto() {
        return photo;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getTimestamp() {
        return timestamp.substring(5, timestamp.length() - 3);
    }

}
