package com.example.findatutor.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.findatutor.Models.Constants;
import com.example.findatutor.Networking.FileUtils;
import com.example.findatutor.Singleton.MySingleton;
import com.example.findatutor.R;
import com.example.findatutor.Singleton.SharedPreferenceManager;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditTutorMyAccountActivity extends AppCompatActivity {

    ImageView editProfilePic;
    MaterialEditText editSubjects, editHourlyCost, editQualifications, editDescription;
    Button upload, save;
    CharSequence[] options = {"Gallery", "Cancel"};
    String selectedImage;

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
                File newPhoto = new File(Uri.parse(selectedImage).getPath());
                updateUserData(newPhoto.toString(), newSubjects, newHourlyCost, newQualifications, newDescription);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_CANCELED){
            switch(requestCode){
                case 0:
                    if(resultCode == RESULT_OK && data != null){
                        Bitmap image = (Bitmap) data.getExtras().get("Data");
                        selectedImage = FileUtils.getPath(EditTutorMyAccountActivity.this, getImageUri(EditTutorMyAccountActivity.this, image));
                        editProfilePic.setImageBitmap(image);
                    }
                    break;
                case 1:
                    if(resultCode == RESULT_OK && data != null){
                        Uri image = data.getData();
                        selectedImage = FileUtils.getPath(EditTutorMyAccountActivity.this, image);
                        Picasso.get().load(image).into(editProfilePic);
                    }
            }
        }
    }

    public Uri getImageUri(@NonNull Context context, Bitmap bitmap){
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "myImage", "");
        return Uri.parse(path);
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
}