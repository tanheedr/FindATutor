package com.example.findatutor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

public class HomepageActivity extends AppCompatActivity {

    MaterialEditText search;
    Button myAccount, calendar, messages, logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        final SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);

        myAccount = findViewById(R.id.myAccount);
        calendar = findViewById(R.id.calendar);
        messages = findViewById(R.id.messages);
        logout = findViewById(R.id.logout);
        search = findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        myAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomepageActivity.this, TutorMyAccountActivity.class));
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