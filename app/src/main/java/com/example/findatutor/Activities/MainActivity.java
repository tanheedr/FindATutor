package com.example.findatutor.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.findatutor.Networking.Constants;
import com.example.findatutor.Singleton.MySingleton;
import com.example.findatutor.R;
import com.example.findatutor.Singleton.SharedPreferenceManager;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    MaterialEditText email, password;
    CheckBox remember;
    TextView forget;
    Button login, register;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        remember = findViewById(R.id.remember);
        forget = findViewById(R.id.forgetPassword);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        forget.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ForgetPasswordActivity.class));
        });

        register.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            finish();
        });

        login.setOnClickListener(v -> {
            String txtEmail = email.getText().toString().trim();
            String txtPassword = password.getText().toString();
            if (TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword)) {
                Toast.makeText(MainActivity.this, "All fields required", Toast.LENGTH_SHORT).show();
            } else {
                login(txtEmail, txtPassword);
            }
        });

        String loginState = sharedPreferences.getString(getResources().getString(R.string.preferredLoginState), "");
        if(loginState.equals("Logged in")){
            startActivity(new Intent(MainActivity.this, HomepageActivity.class));
            finish();
        }

    }

    private void login(String email, String password){
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Logging In");
        progressDialog.show();
        String url = Constants.LOGIN_URL;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("Login Successful")){
                    progressDialog.dismiss();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    try {
                        JSONObject object = new JSONObject(response);
                        SharedPreferenceManager.getmInstance(getApplicationContext()).UserLogin(object.getInt("ID"), object.getInt("AccountType"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (remember.isChecked()){
                        editor.putString(getResources().getString(R.string.preferredLoginState), "Logged in");
                    }
                    else{
                        editor.putString(getResources().getString(R.string.preferredLoginState), "Logged out");
                    }
                    editor.apply();
                    startActivity(new Intent(MainActivity.this, HomepageActivity.class));
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<String,String>();
                param.put("Email", email);
                param.put("Password", password);
                return param;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(MainActivity.this).addToRequestQueue(request);
    }
}