package com.myclass.app;

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

public class login_with_otp extends AppCompatActivity {

    EditText contact, otp;
    Button login, sendOtp;
    String otpString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_otp);

        contact = findViewById(R.id.contactOtp);
        otp = findViewById(R.id.otp);
        login = findViewById(R.id.loginOtp);
        sendOtp = findViewById(R.id.sendOtp);

        sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckAllFields()){
                    SendOtp(contact.getText().toString());
                   sendOtp.setClickable(false);
                   sendOtp.setEnabled(false);
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckAllFields1()) {
                    Log.d(TAG, "onClick: "+otpString  +otp.getText().toString());
                    if (otpString.equals(otp.getText().toString())) {
                        Log.d(TAG, "onClick:YESSSS ");
                        Intent intent = new Intent(login_with_otp.this, home.class);
                        startActivity(intent);
                    }
                }
            }
        });
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

        return true;
    }
    private boolean CheckAllFields1() {

        if (otp.length() == 0) {
            otp.setError("Please enter otp");
            return false;
        }
        if (otp.length() != 6) {
            otp.setError("Please enter valid otp");
            return false;
        }

        return true;
    }
    public void SendOtp(String contact){
        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_otp, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("TAG", "onResponse: " + response);
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("message");

                    if (message.equals("success")) {
                        Log.d(TAG, "on: "+object.getString("otp"));
                        otpString = object.getString("otp");
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(login_with_otp.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        //tch_id = object.getString("tch_id");
                        editor.putString("tch_id",object.getString("tch_id"));
                        editor.putString("name",object.getString("name"));
                        editor.putString("sc_name",object.getString("sc_name"));
                        editor.putString("cont",object.getString("cont"));
                        editor.putString("email",object.getString("email"));
                        editor.putBoolean("isLogin", true);
                        editor.commit();
                    } else {
                        Toast.makeText(login_with_otp.this, message, Toast.LENGTH_SHORT).show();
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
                Map<String, String> data = new HashMap<>();

                data.put("cont", contact);
                Log.d(TAG, "getParams: "+data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(login_with_otp.this).add(sr);
    }
}


