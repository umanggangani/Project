package com.myclass.app;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;


public class addTest1 extends Fragment {
    EditText testName,testDes,startTime,endTime,testDate,testFile,testMark,testNum;
    Spinner sTest;
    Button addTest;
    RecyclerView classList;
    String Id;
    ImageView select;
    private com.myclass.app.classNameAdapter classNameAdapter;
    private ArrayList<Classes> classesArrayList = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<String>();
    String testNameInput,testDesInput,stestInput,startTimeInput,endTimeInput,testDateInput,testMarkInput,testNumInput;
    private int mYear, mMonth, mDay, mHour, mMinute;
    ProgressDialog pd;

    private int PICK_PDF_REQUEST = 1;
    private Uri pdfPath;
    private RequestQueue rQueue;
    String displayName = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_add_test1,container,false);

        // sVideo =view.findViewById(R.id.sVideo);
        testName = view.findViewById(R.id.testName);
        testDes = view.findViewById(R.id.testDes);
        testFile =view.findViewById(R.id.testFile);
        addTest =view.findViewById(R.id.addTest);
        startTime = view.findViewById(R.id.startTime);
        endTime = view.findViewById(R.id.endTime);
        testDate= view.findViewById(R.id.testDate);
        classList = view.findViewById(R.id.classlist);
        pd = new ProgressDialog(getActivity());
        select = view.findViewById(R.id.select);
        testMark = view.findViewById(R.id.testMark);
        testNum = view.findViewById(R.id.testNum);
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
        testDate.setOnClickListener(new View.OnClickListener() {
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
                                testDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        addTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckAllFields()){
                    userAddTest(displayName, pdfPath,Id);
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

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Pdf"),PICK_PDF_REQUEST);
            }
        });
        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data)
    {
        if(requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK){
            pdfPath = data.getData();
            String uriString = pdfPath.toString();
            Log.d(TAG, "onActivityResult: " + uriString);
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();

            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = getActivity().getContentResolver().query(pdfPath, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        Log.d("nameeeee>>> ", displayName);
                        testFile.setText(displayName);
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
                Log.d("nameeeee>>>>  ", displayName);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private boolean CheckAllFields() {

        if (testName.length() == 0) {
            testName.setError("Please enter name");
            return false;
        }
        if (testDes.length() == 0) {
            testDes.setError("Please enter des");
            return false;
        }
        if (testFile.length() == 0) {
            testFile.setError("Please enter link");
            return false;
        }
        if (testDate.length() == 0) {
            testDate.setError("Please enter test date");
            return false;
        }
        if (testMark.length() == 0) {
            testMark.setError("Please enter test mark");
            return false;
        }
        if (testNum.length() == 0) {
            testNum.setError("Please enter number of question");
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
    private void userAddTest(final String pdfname, Uri pdffile,String id) {
        pd.setTitle("");
        pd.setMessage("Loading...");
        pd.show();
        InputStream iStream = null;
        try {
                iStream = getActivity().getContentResolver().openInputStream(pdffile);
                final byte[] inputData = getBytes(iStream);

                VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.URL_addTest,
                        new Response.Listener<NetworkResponse>() {
                            @Override
                            public void onResponse(NetworkResponse response) {
                                Log.d("ressssssoo",new String(response.data));
                                rQueue.getCache().clear();
                                try {
                                    pd.dismiss();
                                    JSONObject jsonObject = new JSONObject(new String(response.data));
                                    //  jsonObject.toString().replace("\\\\","");
                                    String message = jsonObject.getString("message");
                                    String id = jsonObject.getString("testid");
                                    if (message.equals("Test Added Successfuly")) {
                                        String pdf = URLs.URL_testfile+jsonObject.getString("file");
                                        Intent intent = new Intent(getContext(), testPdf.class);
                                        intent.putExtra("url",pdf);
                                        intent.putExtra("id",id);
                                        intent.putExtra("num",testNum.getText().toString());
                                        intent.putExtra("action","Test");
                                        intent.putExtra("Ans","0");
                                        intent.putExtra("user","Teacher");
                                        startActivity(intent);
                                        Toast.makeText(getActivity(), "Test added", Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(getActivity(), testList.class);
//                                        startActivity(intent);
                                        getActivity().finish();
                                    } else {
                                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    pd.dismiss();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pd.dismiss();
                                Log.d("TAG", "onErrorResponse: " + error);
                            }
                        }) {

                    @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();

                testNameInput = testName.getText().toString();
                testDesInput = testDes.getText().toString();
                startTimeInput = startTime.getText().toString();
                endTimeInput = endTime.getText().toString();
                testDateInput = testDate.getText().toString();
                testMarkInput = testMark.getText().toString();
                testNumInput = testNum.getText().toString();

                data.put("name",testNameInput);
                data.put("des", testDesInput);
                data.put("c_id",id);
                data.put("stime",startTimeInput);
                data.put("etime",endTimeInput);
                data.put("date",testDateInput);
                data.put("mark",testMarkInput);
                data.put("num",testNumInput);
                Log.d("TAG", "getParams: "+data);
                return data;
            }
                    @Override
                    protected Map<String, DataPart> getByteData() {
                        Map<String, DataPart> data = new HashMap<>();
                        data.put("file", new DataPart(pdfname ,inputData));
                        Log.d(TAG, "getParams: "+data);
                        return data;
                    }
                };

            volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            rQueue = Volley.newRequestQueue(getActivity());
            rQueue.add(volleyMultipartRequest);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}