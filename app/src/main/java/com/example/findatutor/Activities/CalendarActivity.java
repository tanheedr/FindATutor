package com.example.findatutor.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findatutor.Adapters.CalendarAdapter;
import com.example.findatutor.R;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {

    private TextView monthYear;
    private RecyclerView recyclerView;
    private LocalDate date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        monthYear = findViewById(R.id.calendarMonthYear);
        recyclerView = findViewById(R.id.calendarRecyclerView);
        date = LocalDate.now();
        setMonth();

    }

    private void setMonth(){
        monthYear.setText(monthYearFromDate(date));
        ArrayList<String> daysOfMonth = daysOfMonthArray(date);
        CalendarAdapter adapter = new CalendarAdapter(daysOfMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<String> daysOfMonthArray(LocalDate date) {
        ArrayList<String> daysOfMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);
        int daysOfMonth = yearMonth.lengthOfMonth();
        LocalDate firstDayOfMonth = date.withDayOfMonth(1);
        int daysOfWeek = firstDayOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i <= 42; i++){
            if(i <= daysOfWeek || i > daysOfMonth + daysOfWeek){
                daysOfMonthArray.add("");
            }else{
                daysOfMonthArray.add(String.valueOf(i - daysOfWeek));
            }
        }
        return  daysOfMonthArray;
    }

    private String monthYearFromDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM YYYY");
        return date.format(formatter);
    }

    @Override
    public void onItemClick(int position, String dayText) {
        if(dayText.equals("")){
            String message = "Selected Date: " + dayText + " " + monthYearFromDate(date);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    public void previousMonth(View view){
        date = date.minusMonths(1);
        setMonth();
    }

    public void nextMonth(View view) {
        date = date.plusMonths(1);
        setMonth();
    }
}