package com.example.findatutor.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findatutor.Adapters.CalendarAdapter;
import com.example.findatutor.Networking.CalendarUtils;
import com.example.findatutor.R;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.example.findatutor.Networking.CalendarUtils.daysOfMonthArray;
import static com.example.findatutor.Networking.CalendarUtils.monthYearFromDate;

public class CalendarActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {

    private TextView monthYear;
    private Button previousMonth, nextMonth, goWeekly;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        monthYear = findViewById(R.id.calendarMonthYear);
        previousMonth = findViewById(R.id.calendarPreviousMonth);
        nextMonth = findViewById(R.id.calendarNextMonth);
        goWeekly = findViewById(R.id.calendarGoWeekly);
        recyclerView = findViewById(R.id.calendarRecyclerView);
        CalendarUtils.date = LocalDate.now();
        setMonth();

        previousMonth.setOnClickListener(v -> {
            CalendarUtils.date = CalendarUtils.date.minusMonths(1);
            setMonth();
        });

        nextMonth.setOnClickListener(v -> {
            CalendarUtils.date = CalendarUtils.date.plusMonths(1);
            setMonth();
        });

        goWeekly.setOnClickListener(v -> startActivity(new Intent(CalendarActivity.this, CalendarWeeklyActivity.class)));
    }

    private void setMonth(){
        monthYear.setText(monthYearFromDate(CalendarUtils.date));
        ArrayList<LocalDate> daysOfMonth = daysOfMonthArray(CalendarUtils.date);
        CalendarAdapter adapter = new CalendarAdapter(daysOfMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        if(date != null){
            CalendarUtils.date = date;
            setMonth();
        }
    }
}