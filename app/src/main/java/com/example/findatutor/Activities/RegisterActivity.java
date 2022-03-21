package com.example.findatutor.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.findatutor.Networking.Constants;
import com.example.findatutor.R;
import com.example.findatutor.Singleton.MySingleton;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    /*
    Activity for user to create an account
    The user must enter their full name, email address, password and account type in order to register.
    They must confirm this password by typing it again.
    If the above is complete to a suitable standard (based on restriction in Operations.php such as password
    length), the user can then register.
    If a tutor account is created, not only does the program create a field in the 'accounts' table in the
    database, but it will also create a field in the 'tutordescription' table.
    */

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

        register.setOnClickListener(v -> {
            String txtFirstName = Objects.requireNonNull(firstName.getText()).toString().trim();
            String txtSurname = Objects.requireNonNull(surname.getText()).toString().trim();
            String txtEmail = Objects.requireNonNull(email.getText()).toString().trim();
            String txtPassword = Objects.requireNonNull(password.getText()).toString();
            String txtConfirmPassword = Objects.requireNonNull(confirmPassword.getText()).toString();
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
        });
    }

    private void registerNewAccount(String firstName, String surname, String email, String password, String confirmPassword, String accountType){
        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Creating New Account");
        progressDialog.show();
        String url = Constants.REGISTER_URL;
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
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
        }, error -> {
            progressDialog.dismiss();
            Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
        }){
            @Override
            protected Map<String, String> getParams() {
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