package com.example.findatutor.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.findatutor.Models.Session;
import com.example.findatutor.Networking.CalendarUtils;
import com.example.findatutor.R;

import java.time.LocalTime;

public class CalendarEditWeeklyActivity extends AppCompatActivity {

    private EditText student;
    private TextView dateSet, timeSet;
    private Button confirm;
    private LocalTime time;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_edit_weekly);

        student = findViewById(R.id.sessionNameEnter);
        dateSet = findViewById(R.id.sessionDate);
        timeSet = findViewById(R.id.sessionTime);
        confirm = findViewById(R.id.sessionConfirm);
        time = LocalTime.now();

        dateSet.setText("Date: "+ CalendarUtils.formattedDate(CalendarUtils.date));
        timeSet.setText("Time: "+ CalendarUtils.formattedTime(time));

        confirm.setOnClickListener(v -> {
            String sessionName = student.getText().toString();
            Session newSession = new Session(sessionName, CalendarUtils.date, time);
            Session.sessionsList.add(newSession);
            finish();
        });

    }
}