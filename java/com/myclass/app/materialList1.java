package com.myclass.app;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

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


public class materialList1 extends Fragment {
    private ArrayList<materialModel> materialModalArrayList;
    private com.myclass.app.materialAdapter materialAdapter;
    private com.myclass.app.classNameAdapter classNameAdapter;
    private Spinner malClass;
    private String Id;
    private RecyclerView maList1,classList;
    private ArrayList<Classes> classesArrayList = new ArrayList<>();
    ProgressDialog pd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_material_list1, container, false);

       // malClass = view.findViewById(R.id.malClass);
        maList1 = view.findViewById(R.id.maList);
        classList = view.findViewById(R.id.classlist);
        materialModalArrayList = new ArrayList<materialModel>();
        pd = new ProgressDialog(getActivity());
        populateSpinner();

    /*    malClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Id = classesArrayList.get(position).getC_id();
                materiallist(Id);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/

        return view;
    }
    private void populateSpinner() {
        pd.setTitle("");
        pd.setMessage("Loading...");
        pd.show();
        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_spinner, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("TAG", "onResponse: " + response);
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        classesArrayList.add(new Classes(object.getString("cid"), object.getString("name")));
                        classNameAdapter = new classNameAdapter(classesArrayList, getActivity(), new OnClassSelectListener() {
                            @Override
                            public void selectClass(String c_id) {
                                Id = c_id;
                                materiallist(c_id);
                            }
                        });
                        classList.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
                        classList.setAdapter(classNameAdapter);
                    }
                   /* for (int j =  0; j < classesArrayList.size(); j++) {
                        names.add(classesArrayList.get(j).getName());
                    }
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, names);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    malClass.setAdapter(spinnerArrayAdapter);*/
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
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String tch_id = sharedPreferences.getString("tch_id","");
                Log.d(TAG, "getParams: "+tch_id);
                data.put("tch_id",tch_id);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(getActivity()).add(sr);
    }

    private void materiallist(String id) {

        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_materialList, new Response.Listener<String>() {
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
                    materialAdapter = new materialAdapter(materialModalArrayList, getActivity(), new OnDeleteItemListener() {
                        @Override
                        public void delete() {
                            materiallist(Id);

                        }
                    });
                    // setting layout manager to our recycler view.
                    maList1.setLayoutManager(new LinearLayoutManager(getActivity()));
                    // setting adapter to our recycler view.
                    maList1.setAdapter(materialAdapter);
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
        Volley.newRequestQueue(getActivity()).add(sr);
    }
}