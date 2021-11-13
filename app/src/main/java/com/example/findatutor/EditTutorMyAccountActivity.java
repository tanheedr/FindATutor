package com.example.findatutor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;

public class EditTutorMyAccountActivity extends AppCompatActivity {

    ImageView editProfilePic;
    MaterialEditText editSubjects, editHourlyCost, editQualifications, editDescription;
    Button upload, save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tutor_my_account);

        editProfilePic = findViewById(R.id.editProfilePic);
        editSubjects = findViewById(R.id.editSubjects);
        editHourlyCost = findViewById(R.id.editHourlyCost);
        editQualifications = findViewById(R.id.editQualifications);
        editDescription = findViewById(R.id.editQualifications);
        upload = findViewById(R.id.editUpload);
        save = findViewById(R.id.SaveTutorAccount);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Code to push data from input to db, get from register
                String newSubjects = editSubjects.getText().toString();
                String newHourlyCost = editHourlyCost.getText().toString();
                String newQualifications = editQualifications.getText().toString();
                String newDescription = editDescription.getText().toString();
                if (TextUtils.isEmpty(newSubjects) || TextUtils.isEmpty(newHourlyCost) || TextUtils.isEmpty(newQualifications) || TextUtils.isEmpty(newDescription)){
                    Toast.makeText(EditTutorMyAccountActivity.this, "All fields required", Toast.LENGTH_SHORT).show();
                }
                else{
                    updateUserData(newSubjects, newHourlyCost, newQualifications, newDescription);
                }
            }
        });

    }

    private void updateUserData(String subjects, String hourlyCost, String qualifications, String description){
        final ProgressDialog progressDialog = new ProgressDialog(EditTutorMyAccountActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Updating Information");
        progressDialog.show();
        String url = "http://192.168.0.19/FindATutor/editTutorData.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Successfully Updated")){
                    progressDialog.dismiss();
                    Toast.makeText(EditTutorMyAccountActivity.this, response, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditTutorMyAccountActivity.this, TutorMyAccountActivity.class));
                    finish();
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(EditTutorMyAccountActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(EditTutorMyAccountActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("Subjects", subjects);
                param.put("HourlyCost", hourlyCost);
                param.put("Qualifications", qualifications);
                param.put("Description", description);

                return param;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(EditTutorMyAccountActivity.this).addToRequestQueue(request);
    }
}