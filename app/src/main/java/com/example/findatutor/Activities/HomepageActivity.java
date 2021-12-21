package com.example.findatutor.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.findatutor.R;
import com.rengwuxian.materialedittext.MaterialEditText;

public class HomepageActivity extends AppCompatActivity {

    MaterialEditText search;
    Button myAccount, calendar, messages, logout;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        final SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);

        myAccount = findViewById(R.id.myAccount);
        calendar = findViewById(R.id.calendar);
        messages = findViewById(R.id.messages);
        logout = findViewById(R.id.logout);
        search = findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomepageActivity.this, SearchActivity.class));
            }
        });

        myAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomepageActivity.this, TutorMyAccountActivity.class));
            }
        });

        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomepageActivity.this, MessageActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getResources().getString(R.string.preferredLoginState), "Logged Out");
                editor.apply();
                startActivity(new Intent(HomepageActivity.this, MainActivity.class));
                finish();
            }
        });

    }
}