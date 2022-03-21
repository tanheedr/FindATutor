package com.example.findatutor.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findatutor.Adapters.CalendarAdapter;
import com.example.findatutor.Adapters.SessionsAdapter;
import com.example.findatutor.Models.Session;
import com.example.findatutor.Networking.ApiClient;
import com.example.findatutor.Networking.ApiInterface;
import com.example.findatutor.Networking.CalendarUtils;
import com.example.findatutor.R;
import com.example.findatutor.Singleton.SharedPreferenceManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.findatutor.Networking.CalendarUtils.daysOfWeekArray;
import static com.example.findatutor.Networking.CalendarUtils.monthYearFromDate;

public class CalendarWeeklyActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {

    /*
    Displays a weekly calendar from sunday to saturday, with the current date automatically selected
    Allows movement to other weeks
    Allows sessions to be created and displays currently stored sessions below
    */

    private RecyclerView list;
    private List<Session> sessions;
    private SessionsAdapter adapter;

    private TextView weekYear;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_weekly);

        weekYear = findViewById(R.id.calendarWeekYear);
        Button previousWeek = findViewById(R.id.calendarPreviousWeek);
        Button nextWeek = findViewById(R.id.calendarNextWeek);
        Button newSession = findViewById(R.id.calendarNewSession);
        recyclerView = findViewById(R.id.calendarWeeklyRecyclerView);
        CalendarUtils.date = LocalDate.now();

        list = findViewById(R.id.calendarList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
        list.setHasFixedSize(true);

        setWeek();
        getSessions();

        previousWeek.setOnClickListener(v -> {
            CalendarUtils.date = CalendarUtils.date.minusWeeks(1);
            setWeek();
        });

        nextWeek.setOnClickListener(v -> {
            CalendarUtils.date = CalendarUtils.date.plusWeeks(1);
            setWeek();
        });

        newSession.setOnClickListener(v -> {
            startActivity(new Intent(CalendarWeeklyActivity.this, CalendarEditWeeklyActivity.class));
            finish();
        });

    }


    private void setWeek(){
        weekYear.setText(monthYearFromDate(CalendarUtils.date));
        ArrayList<LocalDate> daysOfWeek = daysOfWeekArray(CalendarUtils.date);
        CalendarAdapter adapter = new CalendarAdapter(daysOfWeek, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        getSessions();
    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        CalendarUtils.date = date;
        setWeek();
    }


    private void getSessions() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Session>> call = apiInterface.getSessions(SharedPreferenceManager.getID(), CalendarUtils.date.toString());
        // Calls getSessions function in ApiInterface with the user's id and the current date as parameters
        call.enqueue(new Callback<List<Session>>() {
            @Override
            public void onResponse(@NonNull Call<List<Session>> call, @NonNull Response<List<Session>> response) {
                sessions = response.body();
                adapter = new SessionsAdapter(sessions, CalendarWeeklyActivity.this);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<List<Session>> call, @NonNull Throwable t) {
                Toast.makeText(CalendarWeeklyActivity.this, "Error on:" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}