package com.example.findatutor.Models;

import com.google.gson.annotations.SerializedName;

public class Chat {

    @SerializedName("SenderID") private String senderID;
    @SerializedName("Message") private String message;
    @SerializedName("Timestamp") private String timestamp;

    public String getSenderID(){
        return senderID;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp.substring(11, timestamp.length() - 3);
    }
}
