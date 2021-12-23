package com.example.findatutor.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.findatutor.R;
import com.github.drjacky.imagepicker.ImagePicker;

public class EditParentMyAccountActivity extends AppCompatActivity {

    ImageView photo;
    TextView firstName, surname;
    Button upload, save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_parent_my_account);

        photo = findViewById(R.id.parentProfilePic);
        firstName = findViewById(R.id.parentFirstName);
        surname = findViewById(R.id.parentSurname);
        upload = findViewById(R.id.editParentUpload);
        save = findViewById(R.id.SaveParentAccount);

        upload.setOnClickListener(v -> ImagePicker.Companion.with(EditParentMyAccountActivity.this).crop().cropSquare().compress(1024).maxResultSize(1080, 1080).start());


    }
}