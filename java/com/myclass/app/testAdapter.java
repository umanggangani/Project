package com.myclass.app;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class testAdapter extends RecyclerView.Adapter<testAdapter.ViewHolder>{

    private ArrayList<testModel> testModelArrayList;
    private Context context;
    OnDeleteItemListener listener;
    EditText testName,testDes,testDate,testsTime,testeTime,testMark,testNum;
    String name,des,date,sTime,eTime,mark,num;
    private int mYear, mMonth, mDay, mHour, mMinute;

    public testAdapter(ArrayList<testModel> testModelArrayList, Context context,OnDeleteItemListener listener) {
        this.testModelArrayList = testModelArrayList;
        this.context = context;
        this.listener = listener;
    }
    @NonNull
    @Override
    public testAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout file on below line.
        View view = LayoutInflater.from(context).inflate(R.layout.card_test, parent, false);
        return new testAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull testAdapter.ViewHolder holder, int position) {

        testModel testModel = testModelArrayList.get(position);
        holder.testName.setText(testModel.getTestName());
        holder.testDes.setText(testModel.getTestDes());
        holder.testMark.setText(testModel.getTestMark()+" & "+testModel.getTestNum());
        //  holder.meetLink.setText(meetModel.getMeetLink());
        holder.testDate.setText(testModel.getTestDate());
        holder.testTime.setText(testModel.getTestSTime()+" to "+testModel.getTestETime());
//        holder.meetSTime.setText(meetModel.getMeetSTime());
//        holder.meetETime.setText(meetModel.getMeetETime());

    /*    holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context,holder.menu);
                popupMenu.inflate(R.menu.meet_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){

                            case R.id.editMeet:
                                AlertDialog.Builder dialog1 = new AlertDialog.Builder(context);
                                LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
                                View layout = inflater.inflate(R.layout.edit_meet, null, false);
                                meetName = (EditText)layout.findViewById(R.id.emeetName);
                                meetDes = (EditText)layout.findViewById(R.id.emeetDes);
                                meetLink = (EditText)layout.findViewById(R.id.emeetLink);
                                meetDate = (EditText)layout.findViewById(R.id.emeetDate);
                                meetsTime = (EditText)layout.findViewById(R.id.estartTime);
                                meeteTime = (EditText)layout.findViewById(R.id.eendTime);

                                meetName.setText(meetModel.getMeetName());
                                meetDes.setText(meetModel.getMeetDes());
                                meetLink.setText(meetModel.getMeetLink());
                                meetDate.setText(meetModel.getMeetDate());
                                meetsTime.setText(meetModel.getMeetSTime());
                                meeteTime.setText(meetModel.getMeetETime());

                                meetsTime.setOnClickListener(new View.OnClickListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.N)
                                    @Override
                                    public void onClick(View v) {
                                        final Calendar c = Calendar.getInstance();
                                        mHour = c.get(Calendar.HOUR_OF_DAY);
                                        mMinute = c.get(Calendar.MINUTE);

                                        // Launch Time Picker Dialog
                                        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                                                new TimePickerDialog.OnTimeSetListener() {
                                                    @Override
                                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                                          int minute) {

                                                        meetsTime.setText(hourOfDay + ":" + minute);
                                                    }
                                                }, mHour, mMinute, false);
                                        timePickerDialog.show();

                                    }
                                });
                                meeteTime.setOnClickListener(new View.OnClickListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.N)
                                    @Override
                                    public void onClick(View v) {
                                        final Calendar c = Calendar.getInstance();
                                        mHour = c.get(Calendar.HOUR_OF_DAY);
                                        mMinute = c.get(Calendar.MINUTE);

                                        // Launch Time Picker Dialog
                                        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                                                new TimePickerDialog.OnTimeSetListener() {

                                                    @Override
                                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                                          int minute) {

                                                        meeteTime.setText(hourOfDay + ":" + minute);
                                                    }
                                                }, mHour, mMinute, false);
                                        timePickerDialog.show();
                                    }
                                });
                                meetDate.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        final java.util.Calendar c = java.util.Calendar.getInstance();
                                        mYear = c.get(java.util.Calendar.YEAR);
                                        mMonth = c.get(java.util.Calendar.MONTH);
                                        mDay = c.get(java.util.Calendar.DAY_OF_MONTH);


                                        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                                                new DatePickerDialog.OnDateSetListener() {

                                                    @Override
                                                    public void onDateSet(DatePicker view, int year,
                                                                          int monthOfYear, int dayOfMonth) {
                                                        meetDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                                    }
                                                }, mYear, mMonth, mDay);
                                        datePickerDialog.show();
                                    }
                                });

                                Button editButton = (Button)layout.findViewById(R.id.editHwButton);
                                Button cancelButton = (Button)layout.findViewById(R.id.cancelButton);
                                dialog1.setTitle("Edit Meet Details");
                                dialog1.setView(layout);
                                AlertDialog alertDialog1 = dialog1.create();

                                cancelButton.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        alertDialog1.dismiss();
                                    }
                                });
                                editButton.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        editMeet(meetModel.getMeetId(),alertDialog1, position);
                                    }
                                });
                                alertDialog1.show();
                                break;
                            case R.id.deleteMeet:
                                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                                inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                                layout = inflater.inflate(R.layout.delete, null, false);
                                TextView masseage = (TextView)layout.findViewById(R.id.studentNameDelete);
                                masseage.setText("Do you want to delete this meet"+meetModel.getMeetName()+"?");
                                Button deleteButton = (Button)layout.findViewById(R.id.deleteButton);
                                cancelButton = (Button) layout.findViewById(R.id.cancelButton);
                                dialog.setTitle("Delete");
                                dialog.setView(layout);
                                AlertDialog alertDialog = dialog.create();

                                cancelButton.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        alertDialog.dismiss();
                                    }
                                });
                                deleteButton.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        deleteMeet(meetModel.getMeetId(),alertDialog, position);
                                    }
                                });
                                alertDialog.show();
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
*/
        holder.readMoreTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.des, null, false);
                TextView des = (TextView)layout.findViewById(R.id.des);
                Button closeButton = (Button)layout.findViewById(R.id.close);
                dialog.setTitle("Description");
                des.setText(testModel.getTestDes());
                dialog.setView(layout);
                AlertDialog alertDialog = dialog.create();

                closeButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        holder.testName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pdf = URLs.URL_testfile+testModel.getTestFile();
                Intent intent = new Intent(context, pdf.class);
                intent.putExtra("url",pdf);
                intent.putExtra("action","Test");
                context.startActivity(intent);
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf));
//                context.startActivity(browserIntent);
            }
        });
        holder.viewTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pdf = URLs.URL_testfile+testModel.getTestFile();
                Intent intent = new Intent(context, pdf.class);
                intent.putExtra("url",pdf);
                intent.putExtra("action","Test");
                context.startActivity(intent);
//                Log.d(TAG, "onClick: "+pdf);
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf));
//                context.startActivity(browserIntent);
            }
        });
        holder.editTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog1 = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.edit_test, null, false);
                testName = (EditText)layout.findViewById(R.id.etestName);
                testDes = (EditText)layout.findViewById(R.id.etestDes);
                testMark = (EditText)layout.findViewById(R.id.etestMark);
                testNum = (EditText)layout.findViewById(R.id.etestNum);
                testDate = (EditText)layout.findViewById(R.id.etestDate);
                testsTime = (EditText)layout.findViewById(R.id.estartTime);
                testeTime = (EditText)layout.findViewById(R.id.eendTime);

                testName.setText(testModel.getTestName());
                testDes.setText(testModel.getTestDes());
                testMark.setText(testModel.getTestMark());
                testNum.setText(testModel.getTestNum());
                testDate.setText(testModel.getTestDate());
                testsTime.setText(testModel.getTestSTime());
                testeTime.setText(testModel.getTestETime());

                testsTime.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View v) {
                        final Calendar c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR_OF_DAY);
                        mMinute = c.get(Calendar.MINUTE);

                        // Launch Time Picker Dialog
                        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute) {
                                        testsTime.setText(hourOfDay + ":" + minute);
                                    }
                                }, mHour, mMinute, false);
                        timePickerDialog.show();

                    }
                });
                testeTime.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View v) {
                        final Calendar c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR_OF_DAY);
                        mMinute = c.get(Calendar.MINUTE);

                        // Launch Time Picker Dialog
                        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                                new TimePickerDialog.OnTimeSetListener() {

                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute) {

                                        testeTime.setText(hourOfDay + ":" + minute);
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


                        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
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

                Button editButton = (Button)layout.findViewById(R.id.editTestButton);
                Button cancelButton = (Button)layout.findViewById(R.id.cancelButton);
                dialog1.setTitle("Edit Test Details");
                dialog1.setView(layout);
                AlertDialog alertDialog1 = dialog1.create();

                cancelButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        alertDialog1.dismiss();
                    }
                });
                editButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        editTest(testModel.getTestId(),alertDialog1, position);
                    }
                });
                alertDialog1.show();
            }
        });

        holder.deleteTest.setOnClickListener(new View.OnClickListener() {
            String testid = testModel.getTestId();

            @Override
            public void onClick(View v) {  AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.delete, null, false);
                TextView masseage = (TextView)layout.findViewById(R.id.studentNameDelete);
                masseage.setText("Do you want to delete this Meet "+testModel.getTestName()+"?");
                Button deleteButton = (Button)layout.findViewById(R.id.deleteButton);
                Button cancelButton = (Button)layout.findViewById(R.id.cancelButton);
                dialog.setTitle("Delete");
                dialog.setView(layout);
                AlertDialog alertDialog = dialog.create();

                cancelButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        alertDialog.dismiss();
                    }
                });
                deleteButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        deleteTest(testid,alertDialog,position);
                    }
                });
                alertDialog.show();
            }
        });
    }
    private void deleteTest(String testid, AlertDialog alertDialog,int position) {
        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_testDelete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("TAG", "onResponse: " + response);
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("message");
                    if (message.equals("success")) {
                        Log.d(TAG, "onResponse: test deleted");
                        alertDialog.dismiss();

                        listener.delete();
                        testModelArrayList.remove(position);
                        notifyItemRemoved(position);
                        notifyDataSetChanged();
                    }
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
                data.put("id",testid);
                Log.d(TAG, "getParams: "+data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(context).add(sr);
    }

    private void editTest(String testId, AlertDialog alertDialog1, int position) {
        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_testEdit, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("TAG", "onResponse: " + response);
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("message");
                    if (message.equals("success")) {
                        Log.d(TAG, "onResponse: Test Edit");

                        alertDialog1.dismiss();
                        listener.delete();
                        testModelArrayList.remove(position);
                        notifyItemRemoved(position);
                        notifyDataSetChanged();

                    }
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

                name = testName.getText().toString();
                des = testDes.getText().toString();
                mark = testMark.getText().toString();
                num = testNum.getText().toString();
                date = testDate.getText().toString();
                sTime = testsTime.getText().toString();
                eTime = testeTime.getText().toString();

                data.put("id",testId);
                data.put("name",name);
                data.put("des",des);
                data.put("date",date);
                data.put("mark",mark);
                data.put("num",num);
                data.put("stime",sTime);
                data.put("etime",eTime);
                Log.d(TAG, "getParams: "+data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(context).add(sr);
    }
    @Override
    public int getItemCount() {
        return testModelArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView testName, testDes,testDate,testSTime,testETime,testTime,readMoreTest,testMark;
        ImageView deleteTest,editTest,menu,viewTest;
        LinearLayout testView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            testName = itemView.findViewById(R.id.tName);
            testDes = itemView.findViewById(R.id.tDes);
            testDate = itemView.findViewById(R.id.tDate);
            testMark = itemView.findViewById(R.id.tMark);
//            meetSTime = itemView.findViewById(R.id.mSTime);
//            meetETime = itemView.findViewById(R.id.mETime);
            testTime = itemView.findViewById(R.id.tTime);
            readMoreTest = itemView.findViewById(R.id.ReadMoreTest);
//            menu = itemView.findViewById(R.id.menuMeet);
            viewTest = itemView.findViewById(R.id.pdf);
            deleteTest = itemView.findViewById(R.id.Delete);
            editTest = itemView.findViewById(R.id.Update);
//            meetView = itemView.findViewById(R.id.meetView);
        }
    }
}
