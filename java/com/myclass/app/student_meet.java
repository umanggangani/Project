package com.myclass.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class student_meet extends AppCompatActivity {
   
    private ArrayList<meetModel> meetModalArrayList;
    private com.myclass.app.studentMeetAdapter studentMeetAdapter;
    private RecyclerView meetList;
    ProgressDialog pd;
    TextView classNameTextView;
    SharedPreferences sharedPreferences;
    String c_id,className;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_meet);

        meetList = findViewById(R.id.studentMeetList);
        classNameTextView = findViewById(R.id.className);
        meetModalArrayList = new ArrayList<meetModel>();
        pd = new ProgressDialog(student_meet.this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(student_meet.this);
        c_id = sharedPreferences.getString("c_id","");
        className = sharedPreferences.getString("className","");
//        classNameTextView.setText(className+ ":");
        meetlist(c_id);
        getSupportActionBar().setTitle("Meet "+"("+className+")");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void meetlist(String id) {
        pd.setTitle("");
        pd.setMessage("Loading...");
        pd.show();
        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_student_meeting, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    pd.dismiss();
                    meetModalArrayList.clear();
                    Log.d("TAG", "onResponse: " + response);
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        // on below line we are extracting data from our json object.
                        meetModalArrayList.add(new meetModel(object.getString("id"), object.getString("name"),object.getString("des"), object.getString("link"), object.getString("date"),object.getString("stime"),object.getString("etime")));
                        // passing array list to our adapter class.
                    }
                    studentMeetAdapter = new studentMeetAdapter(meetModalArrayList, student_meet.this);
                    // setting layout manager to our recycler view.
                    meetList.setLayoutManager(new LinearLayoutManager(student_meet.this));
                    // setting adapter to our recycler view.
                    meetList.setAdapter(studentMeetAdapter);
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
                data.put("c_id",id);
                Log.d(TAG, "f: "+data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(student_meet.this).add(sr);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                startActivity(new Intent(student_meet.this,student_home.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}