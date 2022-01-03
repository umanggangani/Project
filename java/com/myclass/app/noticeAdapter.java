package com.myclass.app;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

public class noticeAdapter extends RecyclerView.Adapter<noticeAdapter.ViewHolder> {

    private ArrayList<noticeModel> noticeModelArrayList;
    private Context context;
    OnDeleteItemListener listener;
    EditText noticeName,noticeDes;
    String name,des;

    public noticeAdapter(ArrayList<noticeModel> noticeModelArrayList, Context context,OnDeleteItemListener listener) {
        this.noticeModelArrayList = noticeModelArrayList;
        this.context = context;
        this.listener = listener;
    }
    @NonNull
    @Override
    public noticeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout file on below line.
        View view = LayoutInflater.from(context).inflate(R.layout.card_notice, parent, false);
        return new noticeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        noticeModel noticeModel = noticeModelArrayList.get(position);
        holder.noticeName.setText(noticeModel.getNoticeName());
        holder.noticeDes.setText(noticeModel.getNoticeDes());
//        holder.noticeFile.setText(noticeModel.getNoticeFile());
//        holder.noticeDate.setText(noticeModel.getNoticeDate());

//        holder.noticeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String pdf = URLs.URL_noticefile+noticeModel.getNoticeFile();
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf));
//                context.startActivity(browserIntent);
//
//                Log.d(TAG, "onClick: "+pdf);
//            }
//        });

//        holder.menu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PopupMenu popupMenu = new PopupMenu(context,holder.menu);
//                popupMenu.inflate(R.menu.hw_menu);
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()){
//                            case R.id.pdf:
//                                String pdf = URLs.URL_noticefile+noticeModel.getNoticeFile();
//                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf));
//                                context.startActivity(browserIntent);
//                                break;
//                            case R.id.editHw:
//                                AlertDialog.Builder dialog1 = new AlertDialog.Builder(context);
//                                LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
//                                View layout = inflater.inflate(R.layout.edit_notice, null, false);
//                                noticeName = (EditText)layout.findViewById(R.id.enoticeName);
//                                noticeDes = (EditText)layout.findViewById(R.id.enoticeDes);
//
//                                noticeName.setText(noticeModel.getNoticeName());
//                                noticeDes.setText(noticeModel.getNoticeDes());
//
//                                Button editButton = (Button)layout.findViewById(R.id.editNoticeButton);
//                                Button cancelButton = (Button)layout.findViewById(R.id.cancelButton);
//                                dialog1.setTitle("Edit Notice");
//                                dialog1.setView(layout);
//                                AlertDialog alertDialog1 = dialog1.create();
//
//                                cancelButton.setOnClickListener(new View.OnClickListener()
//                                {
//                                    @Override
//                                    public void onClick(View v)
//                                    {
//                                        alertDialog1.dismiss();
//                                    }
//                                });
//                                editButton.setOnClickListener(new View.OnClickListener()
//                                {
//                                    @Override
//                                    public void onClick(View v)
//                                    {
//                                        editNotice(noticeModel.getNoticeId(),alertDialog1, position);
//                                    }
//                                });
//                                alertDialog1.show();
//                                break;
//                            case R.id.deleteHw:
//                                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
//                                inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
//                                layout = inflater.inflate(R.layout.delete, null, false);
//                                TextView masseage = (TextView)layout.findViewById(R.id.studentNameDelete);
//                                masseage.setText("Do you want to delete this Notice"+noticeModel.getNoticeName()+"?");
//                                Button deleteButton = (Button)layout.findViewById(R.id.deleteButton);
//                                cancelButton = (Button) layout.findViewById(R.id.cancelButton);
//                                dialog.setTitle("Delete");
//                                dialog.setView(layout);
//                                AlertDialog alertDialog = dialog.create();
//
//                                cancelButton.setOnClickListener(new View.OnClickListener()
//                                {
//                                    @Override
//                                    public void onClick(View v)
//                                    {
//                                        alertDialog.dismiss();
//                                    }
//                                });
//                                deleteButton.setOnClickListener(new View.OnClickListener()
//                                {
//                                    @Override
//                                    public void onClick(View v)
//                                    {
//                                        deleteNotice(noticeModel.getNoticeId(),alertDialog, position);
//                                    }
//                                });
//                                alertDialog.show();
//                                break;
//                        }
//                        return false;
//                    }
//                });
//                popupMenu.show();
//            }
//        });

        holder.readMoreNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.des, null, false);
                TextView des = (TextView)layout.findViewById(R.id.des);
                Button closeButton = (Button)layout.findViewById(R.id.close);
                dialog.setTitle("Description");
                des.setText(noticeModel.getNoticeDes());
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
        holder.noticeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pdf = URLs.URL_noticefile+noticeModel.getNoticeFile();
                Intent intent = new Intent(context, pdf.class);
                intent.putExtra("url",pdf);
                intent.putExtra("action","Notice");
                context.startActivity(intent);
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf));
//                context.startActivity(browserIntent);
            }
        });

        holder.noticeFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pdf = URLs.URL_noticefile+noticeModel.getNoticeFile();
                Intent intent = new Intent(context, pdf.class);
                intent.putExtra("url",pdf);
                intent.putExtra("action","Notice");
                context.startActivity(intent);
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf));
//                context.startActivity(browserIntent);
            }
        });

        holder.editNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog1 = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.edit_notice, null, false);
                noticeName = (EditText)layout.findViewById(R.id.enoticeName);
                noticeDes = (EditText)layout.findViewById(R.id.enoticeDes);

                noticeName.setText(noticeModel.getNoticeName());
                noticeDes.setText(noticeModel.getNoticeDes());

                Button editButton = (Button)layout.findViewById(R.id.editNoticeButton);
                Button cancelButton = (Button)layout.findViewById(R.id.cancelButton);
                dialog1.setTitle("Edit Notice");
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
                        editNotice(noticeModel.getNoticeId(),alertDialog1, position);
                    }
                });
                alertDialog1.show();
            }
        });

        holder.deleteNotice.setOnClickListener(new View.OnClickListener() {
            String noticeid = noticeModel.getNoticeId();
            @Override
            public void onClick(View v) {  AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.delete, null, false);
                TextView masseage = (TextView)layout.findViewById(R.id.studentNameDelete);
                masseage.setText("Do you want to delete this Notice "+noticeModel.getNoticeName()+"?");
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
                        deleteNotice(noticeid,alertDialog,position);
                    }
                });
                alertDialog.show();
            }
        });
    }
    private void deleteNotice(String noticeId, AlertDialog alertDialog,int position) {

        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_noticeDelete, new Response.Listener<String>() {
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
                        noticeModelArrayList.remove(position);
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
                data.put("id",noticeId);
                Log.d(TAG, "getParams: "+data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(context).add(sr);

    }
    private void editNotice(String noticeId, AlertDialog alertDialog1, int position) {
        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_noticeEdit, new Response.Listener<String>() {
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
                        noticeModelArrayList.remove(position);
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

                name = noticeName.getText().toString();
                des = noticeDes.getText().toString();

                data.put("id",noticeId);
                data.put("name",name);
                data.put("des",des);
                Log.d(TAG, "getParams: "+data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(context).add(sr);

    }
    @Override
    public int getItemCount() {
        return noticeModelArrayList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView noticeName, noticeDes, noticeDate,readMoreNotice;
        ImageView deleteNotice,editNotice,menu, noticeFile;
        LinearLayout noticeView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noticeName = itemView.findViewById(R.id.nName);
            noticeDes = itemView.findViewById(R.id.nDes);

            readMoreNotice = itemView.findViewById(R.id.ReadMoreNotice);
            deleteNotice = itemView.findViewById(R.id.Delete);
            editNotice = itemView.findViewById(R.id.Update);
            noticeFile = itemView.findViewById(R.id.pdf);

//           menu = itemView.findViewById(R.id.noticeMenu);
//           noticeDate = itemView.findViewById(R.id.noticelDate);
//           noticeView = itemView.findViewById(R.id.noticeView);

        }
    }
}

