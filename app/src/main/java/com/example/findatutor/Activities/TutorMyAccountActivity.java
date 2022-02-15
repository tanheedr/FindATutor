package com.example.findatutor.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.findatutor.Networking.Constants;
import com.example.findatutor.R;
import com.example.findatutor.Singleton.MySingleton;
import com.example.findatutor.Singleton.SharedPreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TutorMyAccountActivity extends AppCompatActivity {

    ImageView textProfilePic;
    TextView textSubjects, textHourlyCost, textQualifications, textDescription;
    Button edit;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_my_account);

        textProfilePic = findViewById(R.id.textProfilePic);
        textSubjects = findViewById(R.id.textSubjects);
        textHourlyCost = findViewById(R.id.textHourlyCost);
        textQualifications = findViewById(R.id.textQualifications);
        textDescription = findViewById(R.id.textDescription);
        edit = findViewById(R.id.editTutorAccount);
        edit.setOnClickListener(v -> startActivity(new Intent(TutorMyAccountActivity.this, EditTutorMyAccountActivity.class)));

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        GetTutorDataRequest();

    }

    @SuppressLint("StaticFieldLeak")
    public void GetTutorDataRequest() {
        String url = Constants.TUTOR_PROFILE_URL;
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            try {
                JSONObject object = new JSONObject(response);
                String imgUrl = object.getString("Photo");
                String stringSubjects = object.getString("Subjects");
                String stringHourlyCost = object.getString("HourlyCost");
                String stringQualifications = object.getString("Qualifications");
                String stringDescription = object.getString("Description");
                Glide.with(TutorMyAccountActivity.this).load(imgUrl).into(textProfilePic);

                textSubjects.setText(stringSubjects);
                textHourlyCost.setText(stringHourlyCost);
                textQualifications.setText(stringQualifications);
                textDescription.setText(stringDescription);
                progressDialog.hide();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(TutorMyAccountActivity.this, error.toString(), Toast.LENGTH_SHORT).show()) {

            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();
                param.put("ID", SharedPreferenceManager.getID());
                return param;
            }
        };

        MySingleton.getmInstance(TutorMyAccountActivity.this).addToRequestQueue(request);
    }
}