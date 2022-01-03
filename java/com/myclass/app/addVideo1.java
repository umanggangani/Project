package com.myclass.app;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
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


public class addVideo1 extends Fragment {

    EditText videoName,videoDes,videoLink,startTime,endTime,videoDate;
    Spinner sVideo;
    Button addVideo;
    RecyclerView classList;
    String Id;
    private com.myclass.app.classNameAdapter classNameAdapter;
    private ArrayList<Classes> classesArrayList = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<String>();
    String videoNameInput,videoDesInput,videoLinkInput,sVideoInput,startTimeInput,endTimeInput,videoDateInput;
    private int mYear, mMonth, mDay, mHour, mMinute;
    ProgressDialog pd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_video1, container, false);

       // sVideo =view.findViewById(R.id.sVideo);
        videoName = view.findViewById(R.id.videoName);
        videoDes = view.findViewById(R.id.videoDes);
        videoLink =view.findViewById(R.id.videoLink);
        addVideo =view.findViewById(R.id.addVideo);
        startTime = view.findViewById(R.id.startTime);
        endTime = view.findViewById(R.id.endTime);
        videoDate= view.findViewById(R.id.videoDate);
        classList = view.findViewById(R.id.classlist);
        pd = new ProgressDialog(getActivity());

        populateSpinner();
        startTime.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                startTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

            }
        });
        endTime.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                endTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });
        videoDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final java.util.Calendar c = java.util.Calendar.getInstance();
                mYear = c.get(java.util.Calendar.YEAR);
                mMonth = c.get(java.util.Calendar.MONTH);
                mDay = c.get(java.util.Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                videoDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        addVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckAllFields()){
                    userAddVideo(Id);
                }
            }
        });
    /*    sVideo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sVideoInput = classesArrayList.get(position).getC_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

       return view;
    }
    private boolean CheckAllFields() {

        if (videoName.length() == 0) {
            videoName.setError("Please enter name");
            return false;
        }
        if (videoDes.length() == 0) {
            videoDes.setError("Please enter des");
            return false;
        }
        if (videoLink.length() == 0) {
            videoLink.setError("Please enter link");
            return false;
        }
        if (videoDate.length() == 0) {
            videoDate.setError("Please enter videoDate");
            return false;
        }
        if (startTime.length() == 0) {
            startTime.setError("Please enter startTime");
            return false;
        }
        if (endTime.length() == 0) {
            endTime.setError("Please enter endTime");
            return false;
        }
        return true;
    }
    private void populateSpinner() {
        pd.setTitle("");
        pd.setMessage("Loading...");
        pd.show();
        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_spinner, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    pd.dismiss();
                    Log.d("TAG", "onResponse: " + response);
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        classesArrayList.add(new Classes(object.getString("cid"), object.getString("name")));
                        classNameAdapter = new classNameAdapter(classesArrayList, getActivity(), new OnClassSelectListener() {
                            @Override
                            public void selectClass(String c_id) {
                                Id = c_id;
                            }
                        });
                        classList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                        classList.setAdapter(classNameAdapter);
                    }

                  /*  for (int j =  0; j < classesArrayList.size(); j++) {
                        names.add(classesArrayList.get(j).getName());
                    }

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, names);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    sVideo.setAdapter(spinnerArrayAdapter);
*/
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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String tch_id = sharedPreferences.getString("tch_id","");
                Log.d("TAG", "getParams: "+tch_id);
                data.put("tch_id",tch_id);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(getActivity()).add(sr);
    }
     private void userAddVideo(String id) {
         pd.setTitle("");
         pd.setMessage("Loading...");
         pd.show();
        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_addVideo, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    pd.dismiss();
                    Log.d("TAG", "onResponse: " + response);
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("message");

                    if (message.equals("Video Added Sucessfuly")) {
                        Toast.makeText(getActivity(), "Video added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), videoList.class);
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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();

                videoNameInput = videoName.getText().toString();
                videoDesInput = videoDes.getText().toString();
                videoLinkInput = videoLink.getText().toString();
                startTimeInput = startTime.getText().toString();
                endTimeInput = endTime.getText().toString();
                videoDateInput = videoDate.getText().toString();

                data.put("name",videoNameInput);
                data.put("des", videoDesInput);
                data.put("link", videoLinkInput);
                data.put("c_id",id);
                data.put("stime",startTimeInput);
                data.put("etime",endTimeInput);
                data.put("date",videoDateInput);
                Log.d("TAG", "getParams: "+data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(getActivity()).add(sr);
    }
}