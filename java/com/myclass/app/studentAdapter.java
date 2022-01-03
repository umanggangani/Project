package com.myclass.app;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

public class studentAdapter extends RecyclerView.Adapter<studentAdapter.ViewHolder>{

    private ArrayList<studentModel> studentModelArrayList;
    private Context context;
    OnDeleteItemListener listener;
    EditText studentName,studentContact,studentEmail;
    String name,contact,email,date;


    public studentAdapter(ArrayList<studentModel> studentModelArrayList, Context context,
                          OnDeleteItemListener listener) {
        this.studentModelArrayList = studentModelArrayList;
        this.context = context;
        this.listener = listener;
    }
    @NonNull
    @Override
    public studentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout file on below line.
        View view = LayoutInflater.from(context).inflate(R.layout.card_student, parent, false);
        return new studentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        studentModel studentModel = studentModelArrayList.get(position);
        // on below line we are setting data to our text view.
        holder.studentName.setText(studentModel.getStudentName());
        holder.studentCont.setText(studentModel.getStudentCont());
        holder.studentEmail.setText(studentModel.getStudentEmail());
        holder.date.setText(studentModel.getStudentDate());

      /*  holder.menu.setOnClickListener(new View.OnClickListener() {
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
                                View layout = inflater.inflate(R.layout.edit_student, null, false);
                                studentName = (EditText)layout.findViewById(R.id.estudentName);
                                studentContact = (EditText)layout.findViewById(R.id.estudentContact);
                                studentEmail = (EditText)layout.findViewById(R.id.estudentEmail);

                                studentName.setText(studentModel.getStudentName());
                                studentContact.setText(studentModel.getStudentCont());
                                studentEmail.setText(studentModel.getStudentEmail());

                                Button editButton = (Button)layout.findViewById(R.id.editHwButton);
                                Button cancelButton = (Button)layout.findViewById(R.id.cancelButton);
                                dialog1.setTitle("Edit Student Data");
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
                                        editStudent(studentModel.getS_id(),alertDialog1, position);
                                    }
                                });
                                alertDialog1.show();
                                break;
                            case R.id.deleteMeet:
                                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                                inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                                layout = inflater.inflate(R.layout.delete, null, false);
                                TextView masseage = (TextView)layout.findViewById(R.id.studentNameDelete);
                                masseage.setText("Do you want to delete this Student"+studentModel.getStudentName()+"?");
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
                                        deleteStudent(studentModel.getS_id(),alertDialog, position);
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
        });*/

        holder.delete.setOnClickListener(new View.OnClickListener() {
            String sid = studentModel.getS_id();

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) 
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                    View layout = inflater.inflate(R.layout.delete, null, false);
                    TextView masseage = (TextView)layout.findViewById(R.id.studentNameDelete);
                    masseage.setText("Do you want to delete this Student"+studentModel.getStudentName()+"?");
                    Button deleteButton = (Button)layout.findViewById(R.id.deleteButton);
                    Button cancelButton = (Button) layout.findViewById(R.id.cancelButton);
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
                            deleteStudent(studentModel.getS_id(),alertDialog, position);
                        }
                    });
                    alertDialog.show();
                }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog1 = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.edit_student, null, false);
                studentName = (EditText)layout.findViewById(R.id.estudentName);
                studentContact = (EditText)layout.findViewById(R.id.estudentContact);
                studentEmail = (EditText)layout.findViewById(R.id.estudentEmail);

                studentName.setText(studentModel.getStudentName());
                studentContact.setText(studentModel.getStudentCont());
                studentEmail.setText(studentModel.getStudentEmail());

                Button editButton = (Button)layout.findViewById(R.id.editHwButton);
                Button cancelButton = (Button)layout.findViewById(R.id.cancelButton);
                dialog1.setTitle("Edit Student Data");
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
                        editStudent(studentModel.getS_id(),alertDialog1, position);
                    }
                });
                alertDialog1.show();
            }
        });

    }

    private void deleteStudent(String sid, AlertDialog alertDialog,int position) {

        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_studentDelete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("TAG", "onResponse: " + response);
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("message");
                    if (message.equals("success")) {
                        Log.d(TAG, "onResponse: Student deleted");
                        alertDialog.dismiss();
                        listener.delete();
                        studentModelArrayList.remove(position);
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
                data.put("stid",sid);
                Log.d(TAG, "getParams: "+data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(context).add(sr);
    }

    private void editStudent(String studentId, AlertDialog alertDialog1, int position) {
        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_studentEdit, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("TAG", "onResponse: " + response);
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("message");
                    if (message.equals("success")) {
                        Log.d(TAG, "onResponse: Student Edit");

                        alertDialog1.dismiss();
                        listener.delete();
                        studentModelArrayList.remove(position);
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

                name = studentName.getText().toString();
                contact = studentContact.getText().toString();
                email = studentEmail.getText().toString();

                data.put("id",studentId);
                data.put("name",name);
                data.put("cont",contact);
                data.put("email",email);
                data.put("pass","Abc@123");
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

        private TextView studentName, studentCont, studentEmail,date;
        ImageView delete,edit,menu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.sName);
            studentCont = itemView.findViewById(R.id.sCont);
            studentEmail = itemView.findViewById(R.id.sEmail);
            date = itemView.findViewById(R.id.sDate);

 //           menu = itemView.findViewById(R.id.studentMenu);
            delete = itemView.findViewById(R.id.Delete);
            edit = itemView.findViewById(R.id.Update);
   //         studentView = itemView.findViewById(R.id.studentView);

        }
    }
}
