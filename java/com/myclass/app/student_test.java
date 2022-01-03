package com.myclass.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;

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

public class student_test extends AppCompatActivity {

    private ArrayList<testModel> testModalArrayList;
    private com.myclass.app.studentTestAdapter studentTestAdapter;
    private RecyclerView testList;
    ProgressDialog pd;
    SharedPreferences sharedPreferences;
    String c_id,className;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_test);

        testList = findViewById(R.id.studentTestList);
        testModalArrayList = new ArrayList<testModel>();
        pd = new ProgressDialog(student_test.this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(student_test.this);
        c_id = sharedPreferences.getString("c_id","");
        className = sharedPreferences.getString("className","");
//        classNameTextView.setText(className+ ":");
        testlist(c_id);
        Log.d(TAG, "onCreate: "+c_id);
        getSupportActionBar().setTitle("Test "+"("+className+")");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void testlist(String id) {
        pd.setTitle("");
        pd.setMessage("Loading...");
        pd.show();
        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_student_test, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    pd.dismiss();
                    testModalArrayList.clear();
                    Log.d("TAG", "onResponse: " + response);
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        // on below line we are extracting data from our json object.
                        testModalArrayList.add(new testModel(object.getString("testid"), object.getString("name"),object.getString("des"), object.getString("file"),object.getString("ans"), object.getString("mark"),object.getString("getmark"),object.getString("num"), object.getString("date"),object.getString("stime"),object.getString("etime")));
                        // passing array list to our adapter class.
                    }
                    studentTestAdapter = new studentTestAdapter(testModalArrayList, student_test.this);
                    // setting layout manager to our recycler view.
                    testList.setLayoutManager(new LinearLayoutManager(student_test.this));
                    // setting adapter to our recycler view.
                    testList.setAdapter(studentTestAdapter);
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

                String stu_id = sharedPreferences.getString("stu_id","");
                data.put("c_id",id);
                data.put("stu_id",stu_id);
                Log.d(TAG, "f: "+data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(student_test.this).add(sr);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                startActivity(new Intent(student_test.this,student_home.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}