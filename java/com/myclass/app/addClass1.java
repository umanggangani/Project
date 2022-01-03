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
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class addClass1 extends Fragment {

    EditText className,classDes;
    Button addClasses;
    String classNameInput,classDesInput;

    public addClass1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_class1, container, false);
        className = (EditText) view.findViewById(R.id.className);
        classDes = (EditText) view.findViewById(R.id.classDes);
        addClasses = (Button) view.findViewById(R.id.addClass);

        addClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckAllFields()) {
                    userCreateClass();
                }
            }
        });
        return view;
    }
    private void userCreateClass() {
        ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setTitle("");
        pd.setMessage("Loading...");
        pd.show();
        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_createClass, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    pd.dismiss();
                    Log.d("TAG", "onResponse: " + response);
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("message");
                    if (message.equals("Class Add Sucessfuly")) {
                        Toast.makeText(getActivity(), "Class created", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(),classList.class);
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
                pd.dismiss();
                Log.d("TAG", "onErrorResponse: " + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                classNameInput = className.getText().toString();
                classDesInput = classDes.getText().toString();

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String tch_id = sharedPreferences.getString("tch_id","");
                Log.d(TAG, "getParams: "+tch_id);

                data.put("name",classNameInput);
                data.put("des",classDesInput);
                data.put("tch_id",tch_id);
                Log.d(TAG, "getParams: "+data);
                return data;
            }
        };
//        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(getActivity()).add(sr);
    }

    private boolean CheckAllFields() {


        if (className.length() == 0) {
            className.setError("Please enter name");
            return false;
        }
        if (classDes.length() == 0) {
            classDes.setError("Please enter Description");
            return false;
        }
        return true;
    }
}