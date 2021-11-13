package com.example.findatutor;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class TutorMyAccountActivity extends AppCompatActivity {

    ImageView textProfilePic;
    TextView textSubjects, textHourlyCost, textQualifications, textDescription;
    Button edit;

    private String strJson, apiUrl = "http://192.168.0.19/FindATutor/tutorData.php";
    private OkHttpClient client;
    private Response response;

    private RequestBody requestBody;

    private Request request;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_my_account);

        edit = findViewById(R.id.editTutorAccount);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TutorMyAccountActivity.this, EditTutorMyAccountActivity.class));
            }
        });

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

    public class GetUserDataRequest extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            request = new Request.Builder().url(apiUrl).build();
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
                strJson = response.body().string();
                presentUserData(strJson);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

            /*String ImageUrl = "https://images.pexels.com/photos/771742/pexels-photo-771742.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500";
            URL url = new URL(ImageUrl.toString());
            Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            Canvas canvas = new Canvas(bitmap);

            textProfilePic.draw(canvas);*/
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