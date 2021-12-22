package com.example.findatutor.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.findatutor.Models.Constants;
import com.example.findatutor.R;
import com.example.findatutor.Singleton.MySingleton;
import com.example.findatutor.Singleton.SharedPreferenceManager;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TutorMyAccountActivity extends AppCompatActivity {

    ImageView textProfilePic;
    TextView textSubjects, textHourlyCost, textQualifications, textDescription;
    Button edit;

    private OkHttpClient client;
    private Response response;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_my_account);

        edit = findViewById(R.id.editTutorAccount);

        edit.setOnClickListener(v -> startActivity(new Intent(TutorMyAccountActivity.this, EditTutorMyAccountActivity.class)));

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);

        textProfilePic = findViewById(R.id.textProfilePic);
        textSubjects = findViewById(R.id.textSubjects);
        textHourlyCost = findViewById(R.id.textHourlyCost);
        textQualifications = findViewById(R.id.textQualifications);
        textDescription = findViewById(R.id.textDescription);

        progressDialog.show();
        client = new OkHttpClient();
        new GetUserDataRequest().execute();

    }

    @SuppressLint("StaticFieldLeak")
    public class GetUserDataRequest extends AsyncTask<Void, Void, Void>{

        String url = Constants.TUTOR_PROFILE_URL;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //@Todo do something
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TutorMyAccountActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("ID", SharedPreferenceManager.getmInstance(getApplicationContext()).getID());
                return param;
            }
        };

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        /*@Override
        protected Void doInBackground(Void... voids) {
            try {
                getParams();
            } catch (AuthFailureError authFailureError) {
                authFailureError.printStackTrace();
            }
            String apiUrl = Constants.TUTOR_PROFILE_URL;
            Request request = new Request.Builder().url(apiUrl).build();
            try{
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            try {
                String strJson = response.body().string();
                presentUserData(strJson);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    private void presentUserData(String strJson){

        try {
            JSONArray parent = new JSONArray(strJson);
            JSONObject child = parent.getJSONObject(0);
            String imgUrl = child.getString("Photo");
            String stringSubjects = child.getString("Subjects");
            String stringHourlyCost = child.getString("HourlyCost");
            String stringQualifications = child.getString("Qualifications");
            String stringDescription = child.getString("Description");
            Glide.with(this).load(imgUrl).into(textProfilePic);

            textSubjects.setText(stringSubjects);
            textHourlyCost.setText(stringHourlyCost);
            textQualifications.setText(stringQualifications);
            textDescription.setText(stringDescription);
            progressDialog.hide();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}