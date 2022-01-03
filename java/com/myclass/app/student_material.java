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

public class student_material extends AppCompatActivity {
    
    private ArrayList<materialModel> materialModalArrayList;
    private com.myclass.app.studentMaterialAdapter studentMaterialAdapter;
    private RecyclerView materialList;
    ProgressDialog pd;
    TextView classNameTextView;
    SharedPreferences sharedPreferences;
    String c_id,className;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_material);

        materialList = findViewById(R.id.studentMaterialList);
        classNameTextView = findViewById(R.id.className);
        materialModalArrayList = new ArrayList<materialModel>();
        pd = new ProgressDialog(student_material.this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(student_material.this);
        c_id = sharedPreferences.getString("c_id","");
        className = sharedPreferences.getString("className","");
//        classNameTextView.setText(className+ ":");
        materiallist(c_id);
        Log.d(TAG, "onCreate: "+c_id);
        getSupportActionBar().setTitle("Material "+"("+className+")");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void materiallist(String id) {
        pd.setTitle("");
        pd.setMessage("Loading...");
        pd.show();
        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_student_material, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    pd.dismiss();
                    materialModalArrayList.clear();
                    Log.d("TAG", "onResponse: " + response);
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        // on below line we are extracting data from our json object.
                        materialModalArrayList.add(new materialModel(object.getString("id"),object.getString("name"),object.getString("des"), object.getString("file"), object.getString("date")));
                        // passing array list to our adapter class.
                    }
                    studentMaterialAdapter = new studentMaterialAdapter(materialModalArrayList, student_material.this);
                    // setting layout manager to our recycler view.
                    materialList.setLayoutManager(new LinearLayoutManager(student_material.this));
                    // setting adapter to our recycler view.
                    materialList.setAdapter(studentMaterialAdapter);
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
        Volley.newRequestQueue(student_material.this).add(sr);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                startActivity(new Intent(student_material.this,student_home.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}