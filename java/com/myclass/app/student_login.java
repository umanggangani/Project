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


public class student_login extends AppCompatActivity {

    EditText id,password;
    Button login;
    Intent intent;
    String idInput,passwordInput;
    SharedPreferences sharedPreferences;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        id = findViewById(R.id.InputId);
        password = findViewById(R.id.InputPass);
        login = findViewById(R.id.slogin);
        pd = new ProgressDialog(student_login.this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(student_login.this);
        boolean isStudentLogin = sharedPreferences.getBoolean("isStudentLogin", false);
        if (isStudentLogin) {
            intent = new Intent(student_login.this, student_home.class);
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

        private boolean CheckAllFields() {

            if (id.length() == 0) {
                id.setError("Please enter student id");
                return false;
            }
            if (id.length() < 8) {
                id.setError("Please enter valid id");
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
        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_student_login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    pd.dismiss();
                    Log.d("TAG", "onResponse: " + response);
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("message");

                    if (message.equals("Login Success")) {

                        // Toast.makeText(MainActivity.this, "Successfully Login", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(student_login.this,student_home.class);
                        startActivity(intent);
                        finish();

                        //sharedPreferences
                        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(student_login.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        //tch_id = object.getString("tch_id");
                        if(object.getString("regid").equalsIgnoreCase("NA") ||
                                object.getString("regid").equalsIgnoreCase("Not set")){
                            editor.putString("regid","none");
                        }else{
                            editor.putString("regid",object.getString("regid"));
                        }
                        editor.putString("c_id",object.getString("cid"));
                        editor.putString("address",object.getString("address"));
                        editor.putString("bloodgroup",object.getString("bloodgroup"));
                        editor.putString("remark",object.getString("remark"));
                        editor.putString("ST","Student");
                        editor.putString("u_id",object.getString("stid"));
                        editor.putString("stu_id",object.getString("stid"));
                        editor.putString("name",object.getString("name"));
                        editor.putString("cont",object.getString("cont"));
                        editor.putString("email",object.getString("email"));
                        editor.putString("parentsName",object.getString("parentsname"));
                        editor.putString("parentsNumber",object.getString("parentsnumber"));
                        editor.putBoolean("isStudentLogin", true);
                        editor.commit();

                    } else {
                        Toast.makeText(student_login.this, message, Toast.LENGTH_SHORT).show();
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
                idInput = id.getText().toString();
                passwordInput = password.getText().toString();
                data.put("stid", idInput);
                data.put("pass", passwordInput);
                Log.d(TAG, "getParams: "+data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(student_login.this).add(sr);
    }
    public void teacherLogin(View view) {
        startActivity(new Intent(student_login.this,signin.class));
    }
}