package com.myclass.app;

import android.content.Intent;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class search extends AppCompatActivity {

    SearchView search;
    RecyclerView searchStudent;

    ArrayList<studentModel> studentModalArrayList;
    com.myclass.app.searchStudentAdapter searchStudentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Search");

        search = findViewById(R.id.search);
        search.setQueryHint("Search here");
        searchStudent = findViewById(R.id.searchStudent);
       // serch_bar = findViewById(R.id.search_bar);
        studentModalArrayList = new ArrayList<studentModel>();
       // search.setQuery(home.getValue(),false);
      /*  if(!home.getValue().isEmpty()){
            ProgressDialog pd = new ProgressDialog(search.this);
            pd.setTitle("");
            pd.setMessage("Loading...");
            pd.show();
            StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_searchStudent, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        pd.dismiss();
                        studentModalArrayList.clear();
                        Log.d("TAG", "onResponse: " + response);
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            // on below line we are extracting data from our json object.
                            studentModalArrayList.add(new studentModel(object.getString("stid"), object.getString("name"), object.getString("cont"), object.getString("email"),object.getString("join_date")));
                            // passing array list to our adapter class.
                            searchStudentAdapter = new searchStudentAdapter(studentModalArrayList,search.this);
                        }
                        searchStudent.setLayoutManager(new LinearLayoutManager(search.this));
                        // setting adapter to our recycler view.
                        searchStudent.setAdapter(searchStudentAdapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                        pd.dismiss();
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

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(search.this);
                    String tch_id = sharedPreferences.getString("tch_id","");
                    Log.d(TAG, "getParams: "+tch_id);

                    data.put("search", home.getValue());
                    data.put("tch_id",tch_id);
                    Log.d(TAG, "getParams: "+data);
                    return data;
                }
            };
            sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
            Volley.newRequestQueue(search.this).add(sr);
        }*/

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                    ProgressDialog pd = new ProgressDialog(search.this);
                    pd.setTitle("");
                    pd.setMessage("Loading...");
                    pd.show();
                    StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_searchStudent, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                pd.dismiss();
                                studentModalArrayList.clear();
                                Log.d("TAG", "onResponse: " + response);
                                JSONArray array = new JSONArray(response);
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.getJSONObject(i);
                                    // on below line we are extracting data from our json object.
                                    studentModalArrayList.add(new studentModel(object.getString("stid"), object.getString("name"), object.getString("cont"), object.getString("email"),object.getString("join_date")));
                                    // passing array list to our adapter class.
                                    searchStudentAdapter = new searchStudentAdapter(studentModalArrayList,search.this);
                                }
                                searchStudent.setLayoutManager(new LinearLayoutManager(search.this));
                                // setting adapter to our recycler view.
                                searchStudent.setAdapter(searchStudentAdapter);
                            } catch (Exception e) {
                                e.printStackTrace();

                                pd.dismiss();

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

                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(search.this);
                            String tch_id = sharedPreferences.getString("tch_id","");
                            Log.d(TAG, "getParams: "+tch_id);

                            data.put("search", search.getQuery().toString());
                            data.put("tch_id",tch_id);
                            Log.d(TAG, "getParams: "+data);
                            return data;
                        }
                    };
                    sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
                    Volley.newRequestQueue(search.this).add(sr);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                startActivity(new Intent(search.this,home.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}