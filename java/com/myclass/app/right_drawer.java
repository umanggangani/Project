package com.myclass.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class right_drawer extends Fragment {

    private ArrayList<omrModel> omrModalArrayList;
    private omrAdapter omrAdapter;
    private RecyclerView omrList;
    Button submit;
    String id,user,stu_id,c_id;
    String ans="",Ans;
    int num,mark;
    ProgressDialog pd;
    SharedPreferences sharedPreferences;
    private static final String TAG = "right_drawer";

    public right_drawer(String id,int num,String user,String Ans) {
        this.id = id;
        this.num = num;
        this.user = user;
        this.Ans = Ans;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_right_drawer, container, false);
        omrModalArrayList = new ArrayList<>();
        omrList = view.findViewById(R.id.omrList);
        submit = view.findViewById(R.id.submitOmr);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        stu_id = sharedPreferences.getString("stu_id","");
        c_id = sharedPreferences.getString("c_id","");

        pd = new ProgressDialog(getActivity());
        omrList(num);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submit.setEnabled(false);

                if(user.equals("Teacher")){
                    for(int i = 0; i< num ; i++ ){
                        ans += omrModalArrayList.get(i).getAns();
                    }
                   addTestAns(id,ans);
                   Log.d(TAG, "onClick: "+ans);
                }
                else
                {

                    char[] ch = new char[num];
                    for(int i = 0; i< num ; i++ ){
                        ch[i] = Ans.charAt(i);
                        Log.d(TAG, "onClick: "+Ans.charAt(i) +" "+ omrModalArrayList.get(i).getAns());
                        if(omrModalArrayList.get(i).getAns().equals(String.valueOf(Ans.charAt(i))))
                        {
                            mark++;
                        }
                        ans += omrModalArrayList.get(i).getAns();
                    }
                    Log.d(TAG, "onClick: Ans "+Ans+" "+num + " "+ ans);
                    Log.d(TAG, "onClick: mark " + mark);
                    submitTest(id,mark);
                }
            }
        });
        return view;
    }
    private void omrList(int num) {

        for (int i = 1; i <= num; i++) {
            // on below line we are extracting data from our json object.
            omrModalArrayList.add(new omrModel(i,"0"));
            // passing array list to our adapter class.
            omrAdapter = new omrAdapter(omrModalArrayList, getActivity());
            // setting layout manager to our recycler view.
            omrList.setLayoutManager(new LinearLayoutManager(getActivity()));
            // setting adapter to our recycler view.
            omrList.setAdapter(omrAdapter);
        }
    }
    private void addTestAns(String id,String ans) {
        pd.setTitle("");
        pd.setMessage("Loading...");
        pd.show();
        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_addTestAns, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    pd.dismiss();
                    Log.d("TAG", "onResponse: " + response);
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("message");

                    if (message.equals("Test Added Successfuly")) {
                        Toast.makeText(getActivity(), "Test added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), testList.class);
                        startActivity(intent);
                        getActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    pd.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse: " + error);
                pd.dismiss();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();

                data.put("ans",ans);
                data.put("id", id);
                Log.d(TAG, "getParams: "+ data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(getActivity()).add(sr);
    }

    private void submitTest(String id, int mark ){
        pd.setTitle("");
        pd.setMessage("Loading...");
        pd.show();
        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_student_submitTest, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    pd.dismiss();
                    Log.d("TAG", "onResponse: " + response);
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("message");

                    if (message.equals("Submit")) {
                        Toast.makeText(getActivity(), "Test Submit and Mark" + mark, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    pd.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse: " + error);
                pd.dismiss();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();

                data.put("mark",String.valueOf(mark));
                data.put("id", id);
                data.put("stu_id",stu_id);
                data.put("c_id",c_id);
                Log.d(TAG, "getParams: "+ data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(getActivity()).add(sr);

    }
}