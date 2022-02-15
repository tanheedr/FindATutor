package com.example.findatutor.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.findatutor.Networking.Constants;
import com.example.findatutor.R;
import com.example.findatutor.Singleton.MySingleton;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;

public class ForgetPasswordActivity extends AppCompatActivity {

    MaterialEditText email;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        email = findViewById(R.id.forgetEmailType);
        submit = findViewById(R.id.forgetSubmit);

        submit.setOnClickListener(v -> {
            forgetPassword(email.getText().toString().trim());
        });
    }

    private void forgetPassword(String email){
        String url = Constants.FORGET_PASSWORD_URL;
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            if (response.equals("Please head to your email to reset your password")){
                Toast.makeText(ForgetPasswordActivity.this, response, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ForgetPasswordActivity.this, MainActivity.class));
                finish();
            }
            else{
                Toast.makeText(ForgetPasswordActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, error -> Toast.makeText(ForgetPasswordActivity.this, error.toString(), Toast.LENGTH_SHORT).show()){
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> param = new HashMap<>();
                param.put("Email", email);
                return param;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(ForgetPasswordActivity.this).addToRequestQueue(request);
    }
}