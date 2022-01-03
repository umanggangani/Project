package com.myclass.app;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
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

public class classList1 extends Fragment  {

    private ArrayList<classModel> classModalArrayList;
    private classAdapter classAdapter;
    private RecyclerView classList1;
    String tch_id;
    public classList1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_class_list_data, container, false);
       classModalArrayList = new ArrayList<>();
       classList1 = view.findViewById(R.id.classList);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        tch_id = sharedPreferences.getString("tch_id","");
       classlist(tch_id);
       return view;
    }
    private void classlist(String Id) {
        ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setTitle("");
        pd.setMessage("Loading...");
        pd.show();
        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_classList, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    pd.dismiss();
                    classModalArrayList.clear();
                    Log.d("TAG", "onResponse: " + response);
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        // on below line we are extracting data from our json object.
                        classModalArrayList.add(new classModel(object.getString("cid"),object.getString("name"), object.getString("des"), object.getString("date"),object.getString("joincode")));
                        // passing array list to our adapter class.
                        classAdapter = new classAdapter(classModalArrayList, getActivity(), new OnDeleteItemListener() {
                            @Override
                            public void delete() {
                                classlist(tch_id);
                            }
                        });
                        // setting layout manager to our recycler view.
                        classList1.setLayoutManager(new LinearLayoutManager(getActivity()));
                        // setting adapter to our recycler view.
                        classList1.setAdapter(classAdapter);
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

                Log.d(TAG, "getParams: "+Id);
                data.put("tch_id", Id);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(getActivity()).add(sr);
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
