package com.example.findatutor.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.findatutor.Adapters.CalendarAdapter;
import com.example.findatutor.Adapters.SessionAdapter;
import com.example.findatutor.Models.Session;
import com.example.findatutor.Networking.CalendarUtils;
import com.example.findatutor.R;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.example.findatutor.Networking.CalendarUtils.daysOfWeekArray;
import static com.example.findatutor.Networking.CalendarUtils.monthYearFromDate;

public class CalendarWeeklyActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {

    private TextView weekYear;
    private Button previousWeek, nextWeek, goMonthly, newSession;
    private RecyclerView recyclerView;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_weekly);

        weekYear = findViewById(R.id.calendarWeekYear);
        previousWeek = findViewById(R.id.calendarPreviousWeek);
        nextWeek = findViewById(R.id.calendarNextWeek);
        goMonthly = findViewById(R.id.calendarGoMonthly);
        newSession = findViewById(R.id.calendarNewSession);
        recyclerView = findViewById(R.id.calendarWeeklyRecyclerView);
        listView = findViewById(R.id.calendarList);
        CalendarUtils.date = LocalDate.now();
        setWeek();

        previousWeek.setOnClickListener(v -> {
            CalendarUtils.date = CalendarUtils.date.minusWeeks(1);
            setWeek();
        });

        nextWeek.setOnClickListener(v -> {
            CalendarUtils.date = CalendarUtils.date.plusWeeks(1);
            setWeek();
        });

        goMonthly.setOnClickListener(v -> startActivity(new Intent(CalendarWeeklyActivity.this, CalendarActivity.class)));

        newSession.setOnClickListener(v -> startActivity(new Intent(CalendarWeeklyActivity.this, CalendarEditWeeklyActivity.class)));

    }


    private void setWeek(){
        weekYear.setText(monthYearFromDate(CalendarUtils.date));
        ArrayList<LocalDate> daysOfWeek = daysOfWeekArray(CalendarUtils.date);
        CalendarAdapter adapter = new CalendarAdapter(daysOfWeek, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        setSessionAdapter();
    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        CalendarUtils.date = date;
        setWeek();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setSessionAdapter();
    }

    private void setSessionAdapter() {
        ArrayList<Session> sessions = Session.sessionsPerDay(CalendarUtils.date);
        SessionAdapter adapter = new SessionAdapter(getApplicationContext(), sessions);
        listView.setAdapter(adapter);
    }
}