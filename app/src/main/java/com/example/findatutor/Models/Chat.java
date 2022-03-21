package com.example.findatutor.Models;

import com.google.gson.annotations.SerializedName;

public class Chat {

    /*
    Used alongside ChatsAdapter.
    Reads JSON response sent by getChat.php.
    The string in @SerializedName match with the keys of the array and bind the key to the variable created.
    They are accessible via get functions.
    */

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
    // Returns (hh:mm)
}
