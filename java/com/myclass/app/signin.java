package com.myclass.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import static android.content.ContentValues.TAG;

public class signin extends AppCompatActivity {

    EditText contact,password;
    Button login;
    Intent intent;
    String contactInput,passwordInput,tch_id;
    SharedPreferences sharedPreferences;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contact = findViewById(R.id.InputCont);
        password = findViewById(R.id.InputPass);
        login = findViewById(R.id.login);
        pd = new ProgressDialog(signin.this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(signin.this);
        boolean isStudentLogin = sharedPreferences.getBoolean("isStudentLogin", false);
        if(isStudentLogin){
            intent = new Intent(signin.this,student_home.class);
            startActivity(intent);
        }
        boolean isTeacherLogin = sharedPreferences.getBoolean("isTeacherLogin", false);
        if(isTeacherLogin){
            intent = new Intent(signin.this,home.class);
            startActivity(intent);
        }
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckAllFields()) {
                        userLogin();
                    }
                }
            });
        }


    public void loginWithOtp(View view){
        intent = new Intent(signin.this,login_with_otp.class);
        startActivity(intent);
    }
    public void signup(View view) {
        intent = new Intent(signin.this,signup.class);
        startActivity(intent);
    }


    private boolean CheckAllFields() {

        if (contact.length() == 0) {
            contact.setError("Please enter contact");
            return false;
        }
        if (contact.length() != 10) {
            contact.setError("Please enter valid contact");
            return false;
        }
        if (password.length() == 0) {
            password.setError("Please enter password");
            return false;
        }
        if (password.length() < 8) {
            password.setError("Please enter password");
            return false;
        }
        return true;
    }

    private void userLogin() {
        pd.setTitle("");
        pd.setMessage("Loading...");
        pd.show();
        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    pd.dismiss();
                    Log.d("TAG", "onResponse: " + response);
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("message");

                    if (message.equals("Login Success")) {
                       // Toast.makeText(MainActivity.this, "Successfully Login", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(signin.this,home.class);
                        startActivity(intent);
                        finish();
                        //sharedPreferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        //tch_id = object.getString("tch_id");
                        if(object.getString("regid").equalsIgnoreCase("NA")){
                            editor.putString("regid","none");
                        }else{
                            editor.putString("regid",object.getString("regid"));
                        }
                        editor.putString("address",object.getString("address"));
                        editor.putString("u_id",object.getString("tch_id"));
                        editor.putString("tch_id",object.getString("tch_id"));
                        editor.putString("ST","Teacher");
                        editor.putString("name",object.getString("name"));
                        editor.putString("sc_name",object.getString("sc_name"));
                        editor.putString("cont",object.getString("cont"));
                        editor.putString("email",object.getString("email"));
                        editor.putBoolean("isTeacherLogin", true);
                        editor.commit();
                    } else {
                        Toast.makeText(signin.this, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    pd.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Log.d("TAG", "onErrorResponse: " + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                contactInput = contact.getText().toString();
                passwordInput = password.getText().toString();
                data.put("cont", contactInput);
                data.put("pass", passwordInput);
                Log.d(TAG, "getParams: "+data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(signin.this).add(sr);
    }
    @Override
    protected void onStop() {
        super.onStop();
        finishAffinity();
    }
    public void signup1(View view) {
        intent = new Intent(signin.this,signup.class);
        startActivity(intent);
    }

    public void studentLogin(View view) {
        startActivity(new Intent(signin.this,student_login.class));
    }
}