package com.myclass.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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

public class requestedStudentAdapter extends RecyclerView.Adapter<requestedStudentAdapter.ViewHolder>{

    private ArrayList<studentModel> studentModelArrayList;
    private Context context;
    OnDeleteItemListener listener;
    String name,contact,email,date,id;
    ProgressDialog pd;

    public requestedStudentAdapter(ArrayList<studentModel> studentModelArrayList, Context context, OnDeleteItemListener listener) {
        this.studentModelArrayList = studentModelArrayList;
        this.context = context;
        this.listener = listener;
    }

    @NotNull
    @Override
    public requestedStudentAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_requested_student_list, parent, false);
        return new requestedStudentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull requestedStudentAdapter.ViewHolder holder, int position) {

        studentModel studentModel = studentModelArrayList.get(position);
        // on below line we are setting data to our text view.
        holder.name.setText(studentModel.getStudentName());
        id = studentModel.getS_id();
        holder.yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request(id,"1");
                holder.card.setBackgroundColor(Color.parseColor("#bfff80"));
            }
        });
        holder.no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request(id,"2");
                holder.card.setBackgroundColor(Color.parseColor("#ff8080"));
            }
        });
    }

    private void request(String id ,String status) {
        pd = new ProgressDialog(context);
        pd.setTitle("");
        pd.setMessage("Loading...");
        pd.show();
        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_request, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    pd.dismiss();
                    Log.d("TAG", "onResponse: " + response);
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("message");
                    if (message.equals("success")) {
                        Log.d(TAG, "onResponse: "+response);
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
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                String cid = sharedPreferences.getString("classId","");
                Log.d(TAG, "getParams: "+cid);
                data.put("cid",cid);
                data.put("stu_id",id);
                data.put("status",status);
                Log.d(TAG, "getParams: "+data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(context).add(sr);
    }

    @Override
    public int getItemCount() {
        return studentModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView yes,no;
        ConstraintLayout card;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.requestedStudentName);
            yes = itemView.findViewById(R.id.yes);
            no = itemView.findViewById(R.id.no);
            card = itemView.findViewById(R.id.requestedStudentCard);
        }
    }
}
