package com.example.findatutor.Singleton;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {

    private static SharedPreferenceManager mInstance;
    private static Context mCtx;
    private static final String SHARED_PREFERENCE_NAME = "sharedPref";
    private static final String KEY_ID = "ID";
    private static final String KEY_ACCOUNT_TYPE = "AccountType";

    public SharedPreferenceManager(Context mCtx) {
        this.mCtx = mCtx;
    }

    public static synchronized SharedPreferenceManager getmInstance(Context context){
        if (mInstance == null){
            mInstance = new SharedPreferenceManager(context);
        }
        return mInstance;
    }

    public boolean UserLogin(int id, int accountType){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, id);
        editor.putInt(KEY_ACCOUNT_TYPE, accountType);
        editor.apply();
        return true;
    }

    public static String getID(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return String.valueOf(sharedPreferences.getInt(KEY_ID, 0));
    }

    public static String getAccountType(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return String.valueOf(sharedPreferences.getInt(KEY_ACCOUNT_TYPE, 0));
    }
}
