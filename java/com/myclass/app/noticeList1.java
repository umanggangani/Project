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


public class noticeList1 extends Fragment {

    private ArrayList<noticeModel> noticeModalArrayList;
    private com.myclass.app.noticeAdapter noticeAdapter;
    private RecyclerView noticeList1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice_list1, container, false);
        noticeList1 = view.findViewById(R.id.noticeList);
        noticeModalArrayList = new ArrayList<noticeModel>();
        noticelist();
        return view;
    }
    private void noticelist() {
        ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setTitle("");
        pd.setMessage("Loading...");
        pd.show();
        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_noticeList, new Response.Listener<String>() {
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
                        noticeAdapter = new noticeAdapter(noticeModalArrayList, getActivity(), new OnDeleteItemListener() {
                            @Override
                            public void delete() {
                                noticelist();
                            }
                        });
                        // setting layout manager to our recycler view.
                        noticeList1.setLayoutManager(new LinearLayoutManager(getActivity()));
                        // setting adapter to our recycler view.
                        noticeList1.setAdapter(noticeAdapter);
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
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String tch_id = sharedPreferences.getString("tch_id","");
                Log.d(TAG, "getParams: "+tch_id);
                data.put("tch_id",tch_id);
                Log.d(TAG, "f: "+data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(getActivity()).add(sr);
    }
}