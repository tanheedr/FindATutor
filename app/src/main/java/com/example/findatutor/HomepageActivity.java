package com.example.findatutor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomepageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Tutor> tutors;
    private AdapterClass adapter;
    private ApiInterface apiInterface;

    MaterialEditText search;
    Button myAccount, calendar, messages, logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        final SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        myAccount = findViewById(R.id.myAccount);
        calendar = findViewById(R.id.calendar);
        messages = findViewById(R.id.messages);
        logout = findViewById(R.id.logout);
        search = findViewById(R.id.search);

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

        fetchTutors("ath");
    }

    public void fetchTutors(String subjectSearch){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Tutor>> call = apiInterface.getTutors(subjectSearch);
        call.enqueue(new Callback<List<Tutor>>() {
            @Override
            public void onResponse(Call<List<Tutor>> call, Response<List<Tutor>> response) {
                tutors = response.body();
                adapter = new AdapterClass(tutors, HomepageActivity.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Tutor>> call, Throwable t) {
                Toast.makeText(HomepageActivity.this, "Error on:" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())
        );
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchTutors(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fetchTutors(newText);
                return false;
            }
        });

        return true;
    }
}