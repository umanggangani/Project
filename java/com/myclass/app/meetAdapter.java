package com.myclass.app;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
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

public class meetAdapter extends RecyclerView.Adapter<meetAdapter.ViewHolder> {

    private ArrayList<meetModel> meetModelArrayList;
    private Context context;
    OnDeleteItemListener listener;
    EditText meetName,meetDes,meetLink,meetDate,meetsTime,meeteTime;
    String name,des,link,date,sTime,eTime;
    private int mYear, mMonth, mDay, mHour, mMinute;

    public meetAdapter(ArrayList<meetModel> meetModelArrayList, Context context,OnDeleteItemListener listener) {
        this.meetModelArrayList = meetModelArrayList;
        this.context = context;
        this.listener = listener;
    }
    @NonNull
    @Override
    public meetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout file on below line.
        View view = LayoutInflater.from(context).inflate(R.layout.card_meet, parent, false);
        return new meetAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull meetAdapter.ViewHolder holder, int position) {

        meetModel meetModel = meetModelArrayList.get(position);
        holder.meetName.setText(meetModel.getMeetName());
        holder.meetDes.setText(meetModel.getMeetDes());
      //  holder.meetLink.setText(meetModel.getMeetLink());
        holder.meetDate.setText(meetModel.getMeetDate());
        holder.meetTime.setText(meetModel.getMeetSTime()+" to "+meetModel.getMeetETime());
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
        holder.readMoreMeet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.des, null, false);
                TextView des = (TextView)layout.findViewById(R.id.des);
                Button closeButton = (Button)layout.findViewById(R.id.close);
                dialog.setTitle("Description");
                des.setText(meetModel.getMeetDes());
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
        holder.meetName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+meetModel.getMeetLink());
            }
        });
        holder.meetLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+meetModel.getMeetLink());
            }
        });
        holder.editMeet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });

        holder.deleteMeet.setOnClickListener(new View.OnClickListener() {
            String meetid = meetModel.getMeetId();

            @Override
            public void onClick(View v) {  AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.delete, null, false);
                TextView masseage = (TextView)layout.findViewById(R.id.studentNameDelete);
                masseage.setText("Do you want to delete this Meet "+meetModel.getMeetName()+"?");
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
                        deleteMeet(meetid,alertDialog,position);
                    }
                });
                alertDialog.show();
            }
        });
    }
    private void deleteMeet(String meetid, AlertDialog alertDialog,int position) {

        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_meetingDelete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("TAG", "onResponse: " + response);
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("message");
                    if (message.equals("success")) {
                        Log.d(TAG, "onResponse: meet deleted");
                        alertDialog.dismiss();

                        listener.delete();
                        meetModelArrayList.remove(position);
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
                data.put("id",meetid);
                Log.d(TAG, "getParams: "+data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(context).add(sr);

    }

    private void editMeet(String materialId, AlertDialog alertDialog1, int position) {
        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_meetEdit, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("TAG", "onResponse: " + response);
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("message");
                    if (message.equals("success")) {
                        Log.d(TAG, "onResponse: Hw Edit");

                        alertDialog1.dismiss();
                        listener.delete();
                        meetModelArrayList.remove(position);
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

                name = meetName.getText().toString();
                des = meetDes.getText().toString();
                link = meetLink.getText().toString();
                date = meetDate.getText().toString();
                sTime = meetsTime.getText().toString();
                eTime = meeteTime.getText().toString();

                data.put("id",materialId);
                data.put("name",name);
                data.put("des",des);
                data.put("link",link);
                data.put("date",date);
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

        return meetModelArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView meetName, meetDes,meetDate,meetSTime,meetETime,meetTime,readMoreMeet;
        ImageView deleteMeet,editMeet,menu,meetLink;
        LinearLayout meetView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            meetName = itemView.findViewById(R.id.mName);
            meetDes = itemView.findViewById(R.id.mDes);
            meetLink = itemView.findViewById(R.id.meetLink);
            meetDate = itemView.findViewById(R.id.mDate);
//            meetSTime = itemView.findViewById(R.id.mSTime);
//            meetETime = itemView.findViewById(R.id.mETime);
            meetTime = itemView.findViewById(R.id.mTime);
            readMoreMeet = itemView.findViewById(R.id.ReadMoreMeet);
//            menu = itemView.findViewById(R.id.menuMeet);

            deleteMeet = itemView.findViewById(R.id.Delete);
            editMeet = itemView.findViewById(R.id.Update);
//            meetView = itemView.findViewById(R.id.meetView);
        }
    }
}
