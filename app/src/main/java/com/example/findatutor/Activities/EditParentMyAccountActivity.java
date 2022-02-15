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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.findatutor.Networking.Constants;
import com.example.findatutor.Networking.FileUtils;
import com.example.findatutor.R;
import com.example.findatutor.Singleton.MySingleton;
import com.example.findatutor.Singleton.SharedPreferenceManager;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class EditParentMyAccountActivity extends AppCompatActivity {

    ImageView photo;
    TextView firstName, surname;
    Button upload, save;
    CharSequence[] options = {"Gallery", "Cancel"};
    String selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_parent_my_account);

        photo = findViewById(R.id.editParentProfilePic);
        firstName = findViewById(R.id.parentFirstName);
        surname = findViewById(R.id.parentSurname);
        upload = findViewById(R.id.editParentUpload);
        save = findViewById(R.id.saveParentAccount);

        requirePermission();

        upload.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(EditParentMyAccountActivity.this);
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
            File newPhoto = new File(Uri.parse(selectedImage).getPath());
            updateUserData(newPhoto.toString());
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_CANCELED){
            switch(requestCode){
                case 0:
                    if(resultCode == RESULT_OK && data != null){
                        Bitmap image = (Bitmap) data.getExtras().get("Data");
                        selectedImage = FileUtils.getPath(EditParentMyAccountActivity.this, getImageUri(EditParentMyAccountActivity.this, image));
                        photo.setImageBitmap(image);
                    }
                    break;
                case 1:
                    if(resultCode == RESULT_OK && data != null){
                        Uri image = data.getData();
                        selectedImage = FileUtils.getPath(EditParentMyAccountActivity.this, image);
                        Picasso.get().load(image).into(photo);
                    }
            }
        }
    }

    public Uri getImageUri(@NonNull Context context, Bitmap bitmap){
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "myImage", "");
        return Uri.parse(path);
    }

    public void requirePermission(){
        ActivityCompat.requestPermissions(EditParentMyAccountActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }

    private void updateUserData(String photo){
        final ProgressDialog progressDialog = new ProgressDialog(EditParentMyAccountActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Updating Information");
        progressDialog.show();
        String url = Constants.EDIT_PARENT_PROFILE_URL;
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            if (response.equals("Successfully Updated")){
                progressDialog.dismiss();
                Toast.makeText(EditParentMyAccountActivity.this, response, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditParentMyAccountActivity.this, ParentMyAccountActivity.class));
                finish();
            }
            else{
                progressDialog.dismiss();
                Toast.makeText(EditParentMyAccountActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            progressDialog.dismiss();
            Toast.makeText(EditParentMyAccountActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
        }){
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> param = new HashMap<>();
                param.put("ID", SharedPreferenceManager.getID());
                param.put("Photo", photo);
                return param;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(EditParentMyAccountActivity.this).addToRequestQueue(request);
    }
}