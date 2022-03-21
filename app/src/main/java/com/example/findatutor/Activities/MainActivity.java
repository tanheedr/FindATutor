package com.example.findatutor.Activities;

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

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    /*
    Login Page.
    User must enter a registered email and password in order to login.
    The user is presented the following options:
        Remain logged in automatically via a checkbox
        Change password via clickable text
        Create a new account
    */

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
        // Initializes shared preferences

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        remember = findViewById(R.id.remember);
        forget = findViewById(R.id.forgetPassword);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        forget.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ForgetPasswordActivity.class)));

        register.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            finish();
        });

        login.setOnClickListener(v -> {
            String txtEmail = Objects.requireNonNull(email.getText()).toString().trim();
            String txtPassword = Objects.requireNonNull(password.getText()).toString();
            if (TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword)) {
                Toast.makeText(MainActivity.this, "All fields required", Toast.LENGTH_SHORT).show();
            } else {
                login(txtEmail, txtPassword);
            }
        });

        String loginState = sharedPreferences.getString(getResources().getString(R.string.preferredLoginState), "");
        // Reads whether the user has previously chosen to remain automatically logged in or not
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
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
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
                    /*
                    If the user chooses "Remember Me", their preferred login state is saved and the program
                    will automatically open the homepage when the user next opens the app.
                    */
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
        }, error -> {
            progressDialog.dismiss();
            Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
        }){
            @Override
            protected Map<String,String> getParams() {
                Map<String,String> param = new HashMap<>();
                param.put("Email", email);
                param.put("Password", password);
                return param;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(MainActivity.this).addToRequestQueue(request);
    }
}