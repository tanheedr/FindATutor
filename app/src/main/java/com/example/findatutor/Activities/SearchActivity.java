package com.example.findatutor.Activities;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findatutor.Adapters.TutorsAdapter;
import com.example.findatutor.Models.Tutor;
import com.example.findatutor.Networking.ApiClient;
import com.example.findatutor.Networking.ApiInterface;
import com.example.findatutor.R;
import com.example.findatutor.Singleton.SharedPreferenceManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    /*
    Allows users to search for a tutor by subject.
    If a tutor is searching, their option will not appear
    */

    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private List<Tutor> tutors;
    private TutorsAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        fetchTutors("");

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        android.widget.SearchView searchView = findViewById(R.id.searchTutor);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //do something when text submitted
                fetchTutors(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                //do something when text changes
                fetchTutors(newText);
                return true;
            }
        });

    }

    public void fetchTutors(String subjectSearch){
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Tutor>> call = apiInterface.getTutors(SharedPreferenceManager.getID(), subjectSearch);
        // Calls getTutors function in ApiInterface, with the user's id and the contents of the search box as parameters
        call.enqueue(new Callback<List<Tutor>>() {
            @Override
            public void onResponse(@NonNull Call<List<Tutor>> call, @NonNull Response<List<Tutor>> response) {
                tutors = response.body();
                adapter = new TutorsAdapter(tutors, SearchActivity.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<List<Tutor>> call, @NonNull Throwable t) {
                Toast.makeText(SearchActivity.this, "Error on:" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
