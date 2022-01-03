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

public class student_categoties_list extends AppCompatActivity {

    private RecyclerView list;
    ProgressDialog pd;
    TextView classNameTextView;
    SharedPreferences sharedPreferences;
    String c_id,className,cat;

    private ArrayList<hwModel> hwModalArrayList;
    private com.myclass.app.studentHomeWorkAdapter studentHomeWorkAdapter;

    private ArrayList<videoModel> videoModalArrayList;
    private com.myclass.app.studentVideoAdapter studentVideoAdapter;

    private ArrayList<materialModel> materialModalArrayList;
    private com.myclass.app.studentMaterialAdapter studentMaterialAdapter;

    private ArrayList<meetModel> meetModalArrayList;
    private com.myclass.app.studentMeetAdapter studentMeetAdapter;

    private ArrayList<noticeModel> noticeModalArrayList;
    private com.myclass.app.studentNoticeAdepter studentNoticeAdepter;

    private ArrayList<testModel> testModalArrayList;
    private com.myclass.app.studentTestAdapter studentTestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_categoties_list);

        Intent intent = getIntent();
        cat = intent.getStringExtra("cat");
        list = findViewById(R.id.list);
        classNameTextView = findViewById(R.id.className);
        pd = new ProgressDialog(student_categoties_list.this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(student_categoties_list.this);
        c_id = sharedPreferences.getString("c_id","");
        className = sharedPreferences.getString("className","");

        getSupportActionBar().setTitle(cat +" ("+className+")");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(cat.equals("Home Work"))
        {
            hwModalArrayList = new ArrayList<hwModel>();
            hwlist(c_id);
        }
        else if(cat.equals("Video"))
        {
            videoModalArrayList = new ArrayList<videoModel>();
            videolist(c_id);
        }
        else if(cat.equals("Material"))
        {
            materialModalArrayList = new ArrayList<materialModel>();
            materiallist(c_id);
        }
        else if(cat.equals("Meet"))
        {
            meetModalArrayList = new ArrayList<meetModel>();
            meetlist(c_id);
        }
        else if(cat.equals("Test"))
        {
            testModalArrayList = new ArrayList<testModel>();
            testlist(c_id);
        }
        else if(cat.equals("Notice"))
        {
            noticeModalArrayList = new ArrayList<noticeModel>();
            noticelist(c_id);
        }
    }

    private void hwlist(String id) {
        pd.setTitle("");
        pd.setMessage("Loading...");
        pd.show();
        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_student_homework, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    pd.dismiss();
                    hwModalArrayList.clear();
                    Log.d("TAG", "hw list: " + response);
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        // on below line we are extracting data from our json object.
                        hwModalArrayList.add(new hwModel(object.getString("id"),object.getString("name"),object.getString("des"), object.getString("file"), object.getString("due_date"),object.getString("due_date")));
                        // passing array list to our adapter class.
                    }
                    studentHomeWorkAdapter = new studentHomeWorkAdapter(hwModalArrayList,student_categoties_list.this);
                    // setting layout manager to our recycler view.
                    list.setLayoutManager(new LinearLayoutManager(student_categoties_list.this));
                    // setting adapter to our recycler view.
                    list.setAdapter(studentHomeWorkAdapter);

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
                Log.d(TAG, "getParams: "+id);
                data.put("c_id",id);
                Log.d(TAG, "f: "+data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(student_categoties_list.this).add(sr);
    }
    private void videolist(String id) {
        pd.setTitle("");
        pd.setMessage("Loading...");
        pd.show();
        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_student_video, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    pd.dismiss();
                    videoModalArrayList.clear();
                    Log.d("TAG", "onResponse: " + response);
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        // on below line we are extracting data from our json object.
                        videoModalArrayList.add(new videoModel(object.getString("id"), object.getString("name"),object.getString("des"), object.getString("link"), object.getString("date"),object.getString("stime"),object.getString("etime")));
                        // passing array list to our adapter class.
                    }
                    studentVideoAdapter = new studentVideoAdapter(videoModalArrayList,student_categoties_list.this);
                    // setting layout manager to our recycler view.
                    list.setLayoutManager(new LinearLayoutManager(student_categoties_list.this));
                    // setting adapter to our recycler view.
                    list.setAdapter(studentVideoAdapter);
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
        Volley.newRequestQueue(student_categoties_list.this).add(sr);
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
                    studentMaterialAdapter = new studentMaterialAdapter(materialModalArrayList, student_categoties_list.this);
                    // setting layout manager to our recycler view.
                    list.setLayoutManager(new LinearLayoutManager(student_categoties_list.this));
                    // setting adapter to our recycler view.
                    list.setAdapter(studentMaterialAdapter);
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
        Volley.newRequestQueue(student_categoties_list.this).add(sr);
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
                    studentMeetAdapter = new studentMeetAdapter(meetModalArrayList, student_categoties_list.this);
                    // setting layout manager to our recycler view.
                    list.setLayoutManager(new LinearLayoutManager(student_categoties_list.this));
                    // setting adapter to our recycler view.
                    list.setAdapter(studentMeetAdapter);
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
        Volley.newRequestQueue(student_categoties_list.this).add(sr);
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
                        studentNoticeAdepter = new studentNoticeAdepter(noticeModalArrayList, student_categoties_list.this);
                        // setting layout manager to our recycler view.
                        list.setLayoutManager(new LinearLayoutManager(student_categoties_list.this));
                        // setting adapter to our recycler view.
                        list.setAdapter(studentNoticeAdepter);
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
        Volley.newRequestQueue(student_categoties_list.this).add(sr);
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
                    studentTestAdapter = new studentTestAdapter(testModalArrayList, student_categoties_list.this);
                    // setting layout manager to our recycler view.
                    list.setLayoutManager(new LinearLayoutManager(student_categoties_list.this));
                    // setting adapter to our recycler view.
                    list.setAdapter(studentTestAdapter);
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
        Volley.newRequestQueue(student_categoties_list.this).add(sr);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                startActivity(new Intent(student_categoties_list.this,student_home.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}