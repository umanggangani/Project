package com.myclass.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {
    EditText name, schoolName, contact, email, password, cpassword;
    Button signup;
    Intent intent;
    String nameInput,schoolNameInput,contactInput,emailInput,passwordInput,cpasswordInput;
    private static final String TAG = "signup";
    boolean isAllFieldsChecked = false;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = findViewById(R.id.InputTName);
        schoolName = findViewById(R.id.InputSchool);
        contact = findViewById(R.id.InputCont);
        email = findViewById(R.id.InputEmail);
        password = findViewById(R.id.InputPass);
       // cpassword = findViewById(R.id.cpassword);
        signup = findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAllFieldsChecked = CheckAllFields();
                if(isAllFieldsChecked) {
                    userSignup();
                }
            }
        });
    }

    public void login(View view) {
        intent = new Intent(signup.this, signin.class);
        startActivity(intent);
    }
    private boolean CheckAllFields() {
        if (name.length() == 0) {
            name.setError("Please enter name");
            return false;
        }
        if (name.length() < 3) {
            name.setError("Please enter valid name");
            return false;
        }
        if (schoolName.length() == 0) {
            schoolName.setError("Please enter school name");
            return false;
        }
        if (schoolName.length() < 3) {
            schoolName.setError("Please enter valid school name");
            return false;
        }
        if (contact.length() == 0) {
            contact.setError("Please enter contact");
            return false;
        }
        if (contact.length() != 10) {
            contact.setError("Please enter valid contact");
            return false;
        }
        if (email.length() == 0) {
            email.setError("Please enter email");
            return false;
        }
        if (!email.getText().toString().matches(emailPattern)) {
            email.setError("Please enter valid email");
            return false;
        }
        if (password.length() == 0) {
            password.setError("Please enter password");
            return false;
        }
//        if (cpassword.length() == 0) {
//            cpassword.setError("Please enter password");
//            return false;
//        }
        if (password.length() < 8) {
            password.setError("Please enter password");
            return false;
        }

        return true;
    }
    private void userSignup() {
        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_signup, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("TAG", "onResponse: " + response);
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("message");

                    if (message.equals("Registration Complete")) {
                        Toast.makeText(signup.this, "Successfully Signup", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(signup.this, signin.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(signup.this, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse: " + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> data = new HashMap<>();

                nameInput = name.getText().toString();
                schoolNameInput = schoolName.getText().toString();
                contactInput = contact.getText().toString();
                emailInput = email.getText().toString();
                passwordInput = password.getText().toString();
               // cpasswordInput = cpassword.getText().toString();

                data.put("name",nameInput);
                data.put("sc_name", schoolNameInput);
                data.put("cont", contactInput);
                data.put("email",emailInput);
                data.put("pass", passwordInput);
                data.put("cpass", passwordInput);
                Log.d(TAG, "getParams: "+data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(signup.this).add(sr);
    }
}