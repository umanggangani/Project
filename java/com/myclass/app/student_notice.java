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

public class student_notice extends AppCompatActivity {


    private ArrayList<noticeModel> noticeModalArrayList;
    private com.myclass.app.studentNoticeAdepter studentNoticeAdepter;
    private RecyclerView noticeList;
    ProgressDialog pd;
    SharedPreferences sharedPreferences;
    String c_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_notice);

        noticeList = findViewById(R.id.studentNoticeList);
        noticeModalArrayList = new ArrayList<noticeModel>();
        pd = new ProgressDialog(student_notice.this);
        getSupportActionBar().setTitle("Notice");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(student_notice.this);
        c_id = sharedPreferences.getString("c_id","");
        noticelist(c_id);
    }
    private void noticelist(String id) {
        pd.setTitle("");
        pd.setMessage("Loading...");
        pd.show();
        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_student_notice, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    pd.dismiss();
                    noticeModalArrayList.clear();
                    Log.d("TAG", "onResponse: " + response);
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        // on below line we are extracting data from our json object.
                        noticeModalArrayList.add(new noticeModel(object.getString("id"), object.getString("name"),object.getString("des"), object.getString("link"), object.getString("date")));
                        // passing array list to our adapter class.
                        studentNoticeAdepter = new studentNoticeAdepter(noticeModalArrayList, student_notice.this);
                        // setting layout manager to our recycler view.
                        noticeList.setLayoutManager(new LinearLayoutManager(student_notice.this));
                        // setting adapter to our recycler view.
                        noticeList.setAdapter(studentNoticeAdepter);
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
                data.put("c_id",id);
                Log.d(TAG, "f: "+data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(student_notice.this).add(sr);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                startActivity(new Intent(student_notice.this,student_home.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}