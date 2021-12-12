package com.example.findatutor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {


    MaterialEditText firstName, surname, email, password, confirmPassword;
    RadioGroup radioGroup;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstName = findViewById(R.id.firstName);
        surname = findViewById(R.id.surname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        radioGroup = findViewById(R.id.radiogp);
        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtFirstName = firstName.getText().toString();
                String txtSurname = surname.getText().toString();
                String txtEmail = email.getText().toString();
                String txtPassword = password.getText().toString();
                String txtConfirmPassword = confirmPassword.getText().toString();
                if (TextUtils.isEmpty(txtFirstName) || TextUtils.isEmpty(txtSurname) || TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword) || TextUtils.isEmpty(txtConfirmPassword)){
                    Toast.makeText(RegisterActivity.this, "All fields required", Toast.LENGTH_SHORT).show();
                }
                else{
                    int accountTypeId = radioGroup.getCheckedRadioButtonId();
                    RadioButton chosenAccountType = radioGroup.findViewById(accountTypeId);
                    if (chosenAccountType == null){
                        Toast.makeText(RegisterActivity.this, "Account type required", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String chooseAccountType = chosenAccountType.getText().toString();
                        registerNewAccount(txtFirstName, txtSurname, txtEmail, txtPassword, txtConfirmPassword, chooseAccountType);
                    }
                }
            }
        });
    }

    private void registerNewAccount(String firstName, String surname, String email, String password, String confirmPassword, String accountType){
        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Creating New Account");
        progressDialog.show();
        String url = "http://192.168.0.19/FindATutor/register.php"; /*AT HOME: 192.168.0.19, AT SCHOOL 2ND PC: 192.168.137.228*/
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Successfully Registered")){
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    finish();
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("FirstName", firstName);
                param.put("Surname", surname);
                param.put("Email", email);
                param.put("Password", password);
                param.put("ConfirmPassword", confirmPassword);
                param.put("AccountType", accountType);
                return param;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(RegisterActivity.this).addToRequestQueue(request);
    }

}