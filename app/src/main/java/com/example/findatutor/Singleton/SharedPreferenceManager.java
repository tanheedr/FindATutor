package com.example.findatutor.Singleton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {

    /*
    Stores user preferences and data that must remain available throughout use of the program
    */

    @SuppressLint("StaticFieldLeak")
    private static SharedPreferenceManager mInstance;
    @SuppressLint("StaticFieldLeak")
    private static Context mCtx;
    private static final String SHARED_PREFERENCE_NAME = "sharedPref";
    private static final String KEY_ID = "ID";
    private static final String KEY_ACCOUNT_TYPE = "AccountType";
    private static final String KEY_RECIPIENT_ID = null;
    private static final String KEY_NAME_ID = null;

    public SharedPreferenceManager(Context mCtx) {
        SharedPreferenceManager.mCtx = mCtx;
    }

    public static synchronized SharedPreferenceManager getmInstance(Context context){
        if (mInstance == null){
            mInstance = new SharedPreferenceManager(context);
        }
        return mInstance;
    }

    // After logging in, store the user's id and account type
    public void UserLogin(int id, int accountType){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, id);
        editor.putInt(KEY_ACCOUNT_TYPE, accountType);
        editor.apply();
    }

    // After clicking on someone to message, store their id
    public void MessageUser(Integer recipientId){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_RECIPIENT_ID, recipientId);
        editor.apply();
    }

    // After clicking on someone to create a session with, store their id
    public void NameUser(Integer nameID){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_NAME_ID, nameID);
        editor.apply();
    }

    public static String getID(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return String.valueOf(sharedPreferences.getInt(KEY_ID, 0));
    }

    public static String getAccountType(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return String.valueOf(sharedPreferences.getInt(KEY_ACCOUNT_TYPE, 0));
    }

    public static String getRecipientID(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return String.valueOf(sharedPreferences.getInt(KEY_RECIPIENT_ID, 0));
    }

    public static String getNameID(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return String.valueOf(sharedPreferences.getInt(KEY_NAME_ID, 0));
    }
}
