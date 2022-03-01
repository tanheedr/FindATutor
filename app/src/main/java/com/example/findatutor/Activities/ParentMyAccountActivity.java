package com.example.findatutor.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.findatutor.Networking.Constants;
import com.example.findatutor.R;
import com.example.findatutor.Singleton.MySingleton;
import com.example.findatutor.Singleton.SharedPreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ParentMyAccountActivity extends AppCompatActivity {

    ImageView photo;
    TextView firstName, surname;
    Button edit;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_my_account);

        photo = findViewById(R.id.parentProfilePic);
        firstName = findViewById(R.id.parentFirstName);
        surname = findViewById(R.id.parentSurname);
        edit = findViewById(R.id.editParentAccount);
        edit.setOnClickListener(v -> {
            startActivity(new Intent(ParentMyAccountActivity.this, EditParentMyAccountActivity.class));
            finish();
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        GetParentDataRequest();
    }

    public void GetParentDataRequest() {
        String url = Constants.PARENT_PROFILE_URL;
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            try {
                JSONObject object = new JSONObject(response);
                String imgUrl = object.getString("Photo");
                String stringFirstName = object.getString("FirstName");
                String stringSurname = object.getString("Surname");

                byte[] decodedString = Base64.decode(imgUrl, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                photo.setImageBitmap(decodedByte);

                firstName.setText(stringFirstName);
                surname.setText(stringSurname);
                progressDialog.hide();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(ParentMyAccountActivity.this, error.toString(), Toast.LENGTH_SHORT).show()) {

            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();
                param.put("ID", SharedPreferenceManager.getID());
                return param;
            }
        };

        MySingleton.getmInstance(ParentMyAccountActivity.this).addToRequestQueue(request);
    }
}