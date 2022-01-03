package com.myclass.app;

import android.app.ProgressDialog;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class classAdapter extends RecyclerView.Adapter<classAdapter.ViewHolder> {

    private ArrayList<classModel> classModelArrayList;
    private Context context;
    OnDeleteItemListener listener;
    EditText className,classDes;
    String name,des;

    public classAdapter(ArrayList<classModel> classModelArrayList, Context context,OnDeleteItemListener listener) {
        this.classModelArrayList = classModelArrayList;
        this.context = context;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout file on below line.
        View view = LayoutInflater.from(context).inflate(R.layout.class_card, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // getting data from our array list in our modal class.
        classModel classModel = classModelArrayList.get(position);
        // on below line we are setting data to our text view.
        holder.className.setText(classModel.getClassName());
        holder.classDes.setText(classModel.getClassDes());
        holder.classCode.setText(classModel.getClassCode());
        //holder.classDate.setText(classModel.getClassDate());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.delete, null, false);
                TextView masseage = (TextView)layout.findViewById(R.id.studentNameDelete);
                masseage.setText("Do you want to delete this Class "+classModel.getClassName()+"?");
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
                        deleteClass(classModel.getC_id(),alertDialog, position);
                    }
                });
                alertDialog.show();
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog1 = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.edit_class, null, false);
                className = (EditText)layout.findViewById(R.id.eclassName);
                classDes = (EditText)layout.findViewById(R.id.eclassDes);

                className.setText(classModel.getClassName());
                classDes.setText(classModel.getClassDes());

                Button editButton = (Button)layout.findViewById(R.id.editHwButton);
                Button cancelButton = (Button)layout.findViewById(R.id.cancelButton);
                dialog1.setTitle("Edit Class");
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
                        editClass(classModel.getC_id(),alertDialog1, position);
                    }
                });
                alertDialog1.show();
            }
        });
        holder.readMoreClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.des, null, false);
                TextView des = (TextView)layout.findViewById(R.id.des);
                Button closeButton = (Button)layout.findViewById(R.id.close);
                dialog.setTitle("Description");
                des.setText(classModel.getClassDes());
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
       /* holder.menu.setOnClickListener(new View.OnClickListener() {
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
                                View layout = inflater.inflate(R.layout.edit_class, null, false);
                                className = (EditText)layout.findViewById(R.id.eclassName);
                                classDes = (EditText)layout.findViewById(R.id.eclassDes);

                                className.setText(classModel.getClassName());
                                classDes.setText(classModel.getClassDes());

                                Button editButton = (Button)layout.findViewById(R.id.editHwButton);
                                Button cancelButton = (Button)layout.findViewById(R.id.cancelButton);
                                dialog1.setTitle("Edit Class");
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
                                        editClass(classModel.getC_id(),alertDialog1, position);
                                    }
                                });
                                alertDialog1.show();
                                break;
                            case R.id.deleteMeet:
                                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                                inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                                layout = inflater.inflate(R.layout.delete, null, false);
                                TextView masseage = (TextView)layout.findViewById(R.id.studentNameDelete);
                                masseage.setText("Do you want to delete this Class "+classModel.getClassName()+"?"
                                );
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
                                        deleteClass(classModel.getC_id(),alertDialog, position);
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

    }
    private void editClass(String materialId, AlertDialog alertDialog, int position) {
        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_classEdit, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("TAG", "onResponse: " + response);
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("message");
                    if (message.equals("success")) {
                        Log.d(TAG, "onResponse: Hw Edit");
                        alertDialog.dismiss();
                        listener.delete();
                        classModelArrayList.remove(position);
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

                name = className.getText().toString();
                des = classDes.getText().toString();

                data.put("c_id",materialId);
                data.put("name",name);
                data.put("des",des);
                Log.d(TAG, "getParams: "+data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(context).add(sr);

    }

    private void deleteClass(String classId, AlertDialog alertDialog, int position) {
        ProgressDialog pd = new ProgressDialog(context);
        pd.setTitle("");
        pd.setMessage("Loading...");
        pd.show();
        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_classDelete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    pd.dismiss();
                    Log.d("TAG", "onResponse: " + response);
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("message");
                    if (message.equals("Class Deleted")) {
                        Log.d(TAG, "onResponse: Class deleted");

                        alertDialog.dismiss();
                        listener.delete();
                        classModelArrayList.remove(position);
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
                data.put("cid",classId);
                Log.d(TAG, "getParams: "+data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(context).add(sr);
    }

    @Override
    public int getItemCount() {
        return classModelArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView className, classDes,classCode, classDate,readMoreClass;
        private ImageView menu,delete,edit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            className = itemView.findViewById(R.id.cName);
            classDes = itemView.findViewById(R.id.cDes);
            classCode = itemView.findViewById(R.id.cCode);
            delete = itemView.findViewById(R.id.Delete);
            edit = itemView.findViewById(R.id.Update);
            readMoreClass = itemView.findViewById(R.id.ReadMoreClass);

          // classDate = itemView.findViewById(R.id.cDate);
         // menu = itemView.findViewById(R.id.menuClass);
        }
    }
}
