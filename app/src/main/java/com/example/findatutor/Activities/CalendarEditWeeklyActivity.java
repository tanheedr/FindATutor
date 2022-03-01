package com.example.findatutor.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.findatutor.Adapters.ChatsAdapter;
import com.example.findatutor.Adapters.NamesAdapter;
import com.example.findatutor.Models.Chat;
import com.example.findatutor.Models.Name;
import com.example.findatutor.Models.Session;
import com.example.findatutor.Networking.ApiClient;
import com.example.findatutor.Networking.ApiInterface;
import com.example.findatutor.Networking.CalendarUtils;
import com.example.findatutor.Networking.Constants;
import com.example.findatutor.R;
import com.example.findatutor.Singleton.MySingleton;
import com.example.findatutor.Singleton.SharedPreferenceManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarEditWeeklyActivity extends AppCompatActivity {

    private Spinner spinner;
    private List<Name> names;
    private NamesAdapter adapter;

    private TextView dateSet;
    private Button confirm, startTime, endTime;
    int hour, minute;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_edit_weekly);

        dateSet = findViewById(R.id.sessionDate);
        startTime = findViewById(R.id.sessionStartTime);
        endTime = findViewById(R.id.sessionEndTime);
        confirm = findViewById(R.id.sessionConfirm);
        spinner = findViewById(R.id.sessionNameEnter);

        displayNames();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Name name = names.get(position);

                Integer ID = Integer.valueOf(name.getID());
                SharedPreferenceManager.getmInstance(adapter.context.getApplicationContext()).NameUser(ID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        dateSet.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.date));

        startTime.setOnClickListener(v -> pickStartTime());
        endTime.setOnClickListener(v -> pickEndTime());

        confirm.setOnClickListener(v -> {
            String start = startTime.getText().toString() + ":00";
            String end = endTime.getText().toString() + ":00";
            String startDateTime = CalendarUtils.date.toString() + " " + start;
            String endDateTime = CalendarUtils.date.toString() + " " + end;
            if(start.equals("Start:00") || end.equals("End:00")){
                Toast.makeText(CalendarEditWeeklyActivity.this, "Please choose a time", Toast.LENGTH_SHORT).show();
            }else{
                newSession(SharedPreferenceManager.getNameID(), startDateTime, endDateTime);
                finish();
            }
        });
    }

    public void pickStartTime(){
        TimePickerDialog.OnTimeSetListener listener = (timePicker, hourSelected, minuteSelected) -> {
            hour = hourSelected;
            minute = minuteSelected;
            startTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, listener, hour, minute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public void pickEndTime(){
        TimePickerDialog.OnTimeSetListener listener = (timePicker, hourSelected, minuteSelected) -> {
            hour = hourSelected;
            minute = minuteSelected;
            endTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, listener, hour, minute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public void displayNames(){
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Name>> call = apiInterface.getNames(SharedPreferenceManager.getID());
        call.enqueue(new Callback<List<Name>>() {
            @Override
            public void onResponse(@NonNull Call<List<Name>> call, @NonNull Response<List<Name>> response) {
                names = response.body();
                adapter = new NamesAdapter(CalendarEditWeeklyActivity.this, names);
                spinner.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<List<Name>> call, @NonNull Throwable t) {
                Toast.makeText(CalendarEditWeeklyActivity.this, "Error on:" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void newSession(String ID, String start, String end){
        String url = Constants.SET_SESSION_URL;
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            if (response.equals("Error Setting Session")){
                Toast.makeText(CalendarEditWeeklyActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, error -> Toast.makeText(CalendarEditWeeklyActivity.this, error.toString(), Toast.LENGTH_SHORT).show()){
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> param = new HashMap<>();

                String tutorID, parentID;
                if(SharedPreferenceManager.getAccountType().equals(String.valueOf('1'))){
                    tutorID = ID;
                    parentID = SharedPreferenceManager.getID();
                }else{
                    tutorID = SharedPreferenceManager.getID();
                    parentID = ID;
                }

                param.put("TutorID", tutorID);
                param.put("ParentID", parentID);
                param.put("StartTime", start);
                param.put("EndTime", end);
                return param;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(CalendarEditWeeklyActivity.this).addToRequestQueue(request);
    }

}