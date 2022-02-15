package com.example.findatutor.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findatutor.Adapters.CalendarAdapter;
import com.example.findatutor.Adapters.NamesAdapter;
import com.example.findatutor.Adapters.SessionAdapter;
import com.example.findatutor.Models.Name;
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

import static com.example.findatutor.Networking.CalendarUtils.date;
import static com.example.findatutor.Networking.CalendarUtils.daysOfWeekArray;
import static com.example.findatutor.Networking.CalendarUtils.monthYearFromDate;

public class CalendarWeeklyActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {

    private RecyclerView list;
    private List<Session> sessions;
    private SessionAdapter adapter;

    private TextView weekYear;
    private Button previousWeek, nextWeek, newSession;
    private RecyclerView recyclerView;
    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_weekly);

        weekYear = findViewById(R.id.calendarWeekYear);
        previousWeek = findViewById(R.id.calendarPreviousWeek);
        nextWeek = findViewById(R.id.calendarNextWeek);
        newSession = findViewById(R.id.calendarNewSession);
        recyclerView = findViewById(R.id.calendarWeeklyRecyclerView);
        layout = findViewById(R.id.rowSessionLayout);
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

        newSession.setOnClickListener(v -> startActivity(new Intent(CalendarWeeklyActivity.this, CalendarEditWeeklyActivity.class)));

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
        call.enqueue(new Callback<List<Session>>() {
            @Override
            public void onResponse(@NonNull Call<List<Session>> call, @NonNull Response<List<Session>> response) {
                sessions = response.body();
                adapter = new SessionAdapter(sessions, CalendarWeeklyActivity.this);
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