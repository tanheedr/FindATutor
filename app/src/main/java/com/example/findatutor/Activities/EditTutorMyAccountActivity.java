package com.example.findatutor.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.findatutor.Networking.Constants;
import com.example.findatutor.R;
import com.example.findatutor.Singleton.MySingleton;
import com.example.findatutor.Singleton.SharedPreferenceManager;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditTutorMyAccountActivity extends AppCompatActivity {

    /*
    Same basics as EditParentMyAccountActivity
    This activity also allows the edit of the user's subjects, hourly cost, qualifications and description
    These variables are initially automatically filled with the user's currently saved information as it most
    likely the user would only like to edit one or two variables and keep the rest the same.
    The data is updated via a post request.
    */

    ImageView editProfilePic;
    MaterialEditText editSubjects, editHourlyCost, editQualifications, editDescription;
    Button upload, save;
    CharSequence[] options = {"Gallery", "Cancel"};
    String selectedImage;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tutor_my_account);

        editProfilePic = findViewById(R.id.editProfilePic);
        editSubjects = findViewById(R.id.editSubjects);
        editHourlyCost = findViewById(R.id.editHourlyCost);
        editQualifications = findViewById(R.id.editQualifications);
        editDescription = findViewById(R.id.editDescription);
        upload = findViewById(R.id.editUpload);
        save = findViewById(R.id.SaveTutorAccount);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Please Wait");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        GetTutorDataRequest();
        requirePermission();

        upload.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(EditTutorMyAccountActivity.this);
            builder.setTitle("Select Image");
            builder.setItems(options, (dialog, which) -> {
                if(options[which].equals("Gallery")){
                    Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(gallery, 1);
                }else{
                    dialog.dismiss();
                }
            });
            builder.show();
        });

        save.setOnClickListener(v -> {
            String newSubjects = Objects.requireNonNull(editSubjects.getText()).toString();
            String newHourlyCost = Objects.requireNonNull(editHourlyCost.getText()).toString();
            String newQualifications = Objects.requireNonNull(editQualifications.getText()).toString();
            String newDescription = Objects.requireNonNull(editDescription.getText()).toString();

            if (TextUtils.isEmpty(newSubjects) || TextUtils.isEmpty(newHourlyCost) || TextUtils.isEmpty(newQualifications) || TextUtils.isEmpty(newDescription)){
                Toast.makeText(EditTutorMyAccountActivity.this, "All fields required", Toast.LENGTH_SHORT).show();
            }
            else{
                updateUserData(selectedImage, newSubjects, newHourlyCost, newQualifications, newDescription);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        assert data != null;
        Uri uri = data.getData();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
            byte[] encodedString = stream.toByteArray();
            selectedImage = Base64.encodeToString(encodedString, Base64.DEFAULT);
            editProfilePic.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void requirePermission(){
        ActivityCompat.requestPermissions(EditTutorMyAccountActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }

    private void updateUserData(String photo, String subjects, String hourlyCost, String qualifications, String description){
        final ProgressDialog progressDialog = new ProgressDialog(EditTutorMyAccountActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Updating Information");
        progressDialog.show();
        String url = Constants.EDIT_TUTOR_PROFILE_URL;
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
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
        }, error -> {
            progressDialog.dismiss();
            Toast.makeText(EditTutorMyAccountActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
        }){
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> param = new HashMap<>();
                param.put("ID", SharedPreferenceManager.getID());
                param.put("Photo", photo);
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

                if (imgUrl.equals("false")){
                    imgUrl = Constants.BLANK_WHITE_SQUARE;
                    byte[] decodedString = Base64.decode(imgUrl, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    editProfilePic.setImageBitmap(decodedByte);
                }else{
                    byte[] decodedString = Base64.decode(imgUrl, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    editProfilePic.setImageBitmap(decodedByte);
                }

                editSubjects.setText(stringSubjects);
                editHourlyCost.setText(stringHourlyCost);
                editQualifications.setText(stringQualifications);
                editDescription.setText(stringDescription);
                dialog.hide();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(EditTutorMyAccountActivity.this, error.toString(), Toast.LENGTH_SHORT).show()) {

            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();
                param.put("ID", SharedPreferenceManager.getID());
                return param;
            }
        };

        MySingleton.getmInstance(EditTutorMyAccountActivity.this).addToRequestQueue(request);
    }
}