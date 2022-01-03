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

public class videoAdapter extends RecyclerView.Adapter<videoAdapter.ViewHolder>{
    private ArrayList<videoModel> videoModelArrayList;
    private Context context;
    OnDeleteItemListener listener;
    String name,des,link,date,sTime,eTime;
    EditText videoName,videoDes,videoLink,videoDate,videosTime,videoeTime;
    private int mYear, mMonth, mDay, mHour, mMinute;

    public videoAdapter(ArrayList<videoModel> videoModelArrayList, Context context,OnDeleteItemListener listener) {
        this.videoModelArrayList = videoModelArrayList;
        this.context = context;
        this.listener = listener;
    }
    @NonNull
    @Override
    public videoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout file on below line.
        View view = LayoutInflater.from(context).inflate(R.layout.card_video, parent, false);
        return new videoAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        videoModel videoModel = videoModelArrayList.get(position);
        holder.videoName.setText(videoModel.getVideoName());
        holder.videoDes.setText(videoModel.getVideoDes());
//    holder.videoLink.setText(videoModel.getVideoLink());
        holder.videoDate.setText(videoModel.getVideoDate());
        holder.videoTime.setText(videoModel.getVideoSTime()+" to "+videoModel.getVideoETime());
//        holder.videoSTime.setText(videoModel.getVideoSTime());
//        holder.videoETime.setText(videoModel.getVideoETime());

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
                                View layout = inflater.inflate(R.layout.edit_video, null, false);
                                videoName = (EditText)layout.findViewById(R.id.evideoName);
                                videoDes = (EditText)layout.findViewById(R.id.evideoDes);
                                videoLink = (EditText)layout.findViewById(R.id.evideoLink);
                                videoDate = (EditText)layout.findViewById(R.id.evideoDate);
                                videosTime = (EditText)layout.findViewById(R.id.estartTime);
                                videoeTime = (EditText)layout.findViewById(R.id.eendTime);

                                videoName.setText(videoModel.getVideoName());
                                videoDes.setText(videoModel.getVideoDes());
                                videoLink.setText(videoModel.getVideoLink());
                                videoDate.setText(videoModel.getVideoDate());
                                videosTime.setText(videoModel.getVideoSTime());
                                videoeTime.setText(videoModel.getVideoETime());



                                videosTime.setOnClickListener(new View.OnClickListener() {
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

                                                        videosTime.setText(hourOfDay + ":" + minute);
                                                    }
                                                }, mHour, mMinute, false);
                                        timePickerDialog.show();

                                    }
                                });
                                videoeTime.setOnClickListener(new View.OnClickListener() {
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

                                                        videoeTime.setText(hourOfDay + ":" + minute);
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


                                        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
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

                                Button editButton = (Button)layout.findViewById(R.id.editHwButton);
                                Button cancelButton = (Button)layout.findViewById(R.id.cancelButton);
                                dialog1.setTitle("Edit Video Details");
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
                                        editVideo(videoModel.getVideoId(),alertDialog1, position);
                                    }
                                });
                                alertDialog1.show();
                                break;
                            case R.id.deleteMeet:
                                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                                inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                                layout = inflater.inflate(R.layout.delete, null, false);
                                TextView masseage = (TextView)layout.findViewById(R.id.studentNameDelete);
                                masseage.setText("Do you want to delete this video"+videoModel.getVideoName()+"?");
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
                                        deleteVideo(videoModel.getVideoId(),alertDialog, position);
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
        holder.videoName.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         Intent intent = new Intent(context,video_player.class);
         intent.putExtra("link",videoModel.getVideoLink());
         context.startActivity(intent);
     }
 });
        holder.editVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog1 = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.edit_video, null, false);
                videoName = (EditText)layout.findViewById(R.id.evideoName);
                videoDes = (EditText)layout.findViewById(R.id.evideoDes);
                videoLink = (EditText)layout.findViewById(R.id.evideoLink);
                videoDate = (EditText)layout.findViewById(R.id.evideoDate);
                videosTime = (EditText)layout.findViewById(R.id.estartTime);
                videoeTime = (EditText)layout.findViewById(R.id.eendTime);

                videoName.setText(videoModel.getVideoName());
                videoDes.setText(videoModel.getVideoDes());
                videoLink.setText(videoModel.getVideoLink());
                videoDate.setText(videoModel.getVideoDate());
                videosTime.setText(videoModel.getVideoSTime());
                videoeTime.setText(videoModel.getVideoETime());

                videosTime.setOnClickListener(new View.OnClickListener() {
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

                                        videosTime.setText(hourOfDay + ":" + minute);
                                    }
                                }, mHour, mMinute, false);
                        timePickerDialog.show();

                    }
                });
                videoeTime.setOnClickListener(new View.OnClickListener() {
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

                                        videoeTime.setText(hourOfDay + ":" + minute);
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


                        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
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

                Button editButton = (Button)layout.findViewById(R.id.editHwButton);
                Button cancelButton = (Button)layout.findViewById(R.id.cancelButton);
                dialog1.setTitle("Edit Video Details");
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
                        editVideo(videoModel.getVideoId(),alertDialog1, position);
                    }
                });
                alertDialog1.show();
            }
        });
        holder.readMoreVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.des, null, false);
                TextView des = (TextView)layout.findViewById(R.id.des);
                Button closeButton = (Button)layout.findViewById(R.id.close);
                dialog.setTitle("Description");
                des.setText(videoModel.getVideoDes());
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
        holder.deleteVideo.setOnClickListener(new View.OnClickListener() {
            String videoid = videoModel.getVideoId();
            @Override
            public void onClick(View v) {  AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.delete, null, false);
                TextView masseage = (TextView)layout.findViewById(R.id.studentNameDelete);
                masseage.setText("Do you want to delete this video "+videoModel.getVideoName()+"?");
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
                        deleteVideo(videoid,alertDialog,position);
                    }
                });
                alertDialog.show();
            }
        });
        holder.videoLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,video_player.class);
                intent.putExtra("link",videoModel.getVideoLink());
                context.startActivity(intent);
            }
        });

    }
    private void deleteVideo(String videoid, AlertDialog alertDialog,int position) {

        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_videoDelete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("TAG", "onResponse: " + response);
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("message");
                    if (message.equals("success")) {
                        Log.d(TAG, "onResponse: notice deleted");
                        alertDialog.dismiss();

                        listener.delete();
                        videoModelArrayList.remove(position);
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
                data.put("id",videoid);
                Log.d(TAG, "getParams: "+data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(context).add(sr);

    }

    private void editVideo(String videoId, AlertDialog alertDialog1, int position) {
        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_videoEdit, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("TAG", "onResponse: " + response);
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("message");
                    if (message.equals("success")) {
                        Log.d(TAG, "onResponse: Video Edit");

                        alertDialog1.dismiss();
                        listener.delete();
                        videoModelArrayList.remove(position);
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

                name = videoName.getText().toString();
                des = videoDes.getText().toString();
                link = videoLink.getText().toString();
                date = videoDate.getText().toString();
                sTime = videosTime.getText().toString();
                eTime = videoeTime.getText().toString();

                data.put("id",videoId);
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

        return videoModelArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView videoName, videoDes,videoSTime,videoETime,readMoreVideo,videoTime,videoDate;
        ImageView deleteVideo,editVideo,menu,videoLink;
        LinearLayout videoView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            videoName = itemView.findViewById(R.id.vName);
            videoDes = itemView.findViewById(R.id.vDes);
            videoLink = itemView.findViewById(R.id.videoLink);
            videoDate = itemView.findViewById(R.id.vDate);
            /*videoSTime = itemView.findViewById(R.id.vSTime);
            videoETime = itemView.findViewById(R.id.vETime);*/

           // menu = itemView.findViewById(R.id.menuVideo);
            readMoreVideo = itemView.findViewById(R.id.ReadMoreVideo);
            videoTime = itemView.findViewById(R.id.vTime);
            deleteVideo = itemView.findViewById(R.id.Delete);
            editVideo  = itemView.findViewById(R.id.Update);
        }
    }
}
