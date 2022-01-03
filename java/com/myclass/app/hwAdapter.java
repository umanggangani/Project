package com.myclass.app;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class hwAdapter  extends RecyclerView.Adapter<hwAdapter.ViewHolder> {

private ArrayList<hwModel> hwModelArrayList;
private Context context;
OnDeleteItemListener listener;
String name,des,date;
EditText hwName,hwDes,hwDueDate;
int mYear,mMonth,mDay;

    public hwAdapter(ArrayList<hwModel> hwModelArrayList, Context context,
    OnDeleteItemListener listener) {
        this.hwModelArrayList = hwModelArrayList;
        this.context = context;
        this.listener = listener;
        }
    @NonNull
    @Override
    public hwAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout file on below line.
        View view = LayoutInflater.from(context).inflate(R.layout.card_hw, parent, false);
        return new hwAdapter.ViewHolder(view);
        }
    @Override
    public void onBindViewHolder(@NonNull @NotNull hwAdapter.ViewHolder holder, int position) {

        hwModel hwModel = hwModelArrayList.get(position);

        holder.hwName.setText(hwModel.getHwName());
        holder.hwDes.setText(hwModel.getHwDes());
        holder.hwDueDate.setText(hwModel.getHwDueDate());
       // holder.hwDate.setText(hwModel.getHwDate());

       // holder.hwFile.setText(hwModel.getHwFile());

     /*   holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context,holder.menu);
                popupMenu.inflate(R.menu.hw_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){
                                case R.id.pdf:
                                    String pdf = URLs.URL_homeworkfile+hwModel.getHwFile();
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf));
                                    context.startActivity(browserIntent);
                                    break;
                                case R.id.editHw:
                                    AlertDialog.Builder dialog1 = new AlertDialog.Builder(context);
                                    LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
                                    View layout = inflater.inflate(R.layout.edit_homework, null, false);
                                    hwName = (EditText)layout.findViewById(R.id.ehwName);
                                    hwDes = (EditText)layout.findViewById(R.id.ehwDes);
                                    hwDueDate = (EditText)layout.findViewById(R.id.ehwDueDate);

                                    hwName.setText(hwModel.getHwName());
                                    hwDes.setText(hwModel.getHwDes());
                                    hwDueDate.setText(hwModel.getHwDueDate());

                                    hwDueDate.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            final Calendar c = Calendar.getInstance();
                                            mYear = c.get(Calendar.YEAR);
                                            mMonth = c.get(Calendar.MONTH);
                                            mDay = c.get(Calendar.DAY_OF_MONTH);


                                            DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                                                    new DatePickerDialog.OnDateSetListener() {
                                                        @Override
                                                        public void onDateSet(DatePicker view, int year,
                                                                              int monthOfYear, int dayOfMonth) {
                                                            hwDueDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                                        }
                                                    }, mYear, mMonth, mDay);
                                            datePickerDialog.show();
                                        }
                                    });

                                    Button editButton = (Button)layout.findViewById(R.id.editHwButton);
                                    Button cancelButton = (Button)layout.findViewById(R.id.cancelButton);
                                    dialog1.setTitle("Edit H.W");
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
                                            editHw(hwModel.getHwId(),alertDialog1, position);
                                        }
                                    });
                                    alertDialog1.show();
                                    break;
                                case R.id.deleteHw:
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                                    inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                                    layout = inflater.inflate(R.layout.delete, null, false);
                                    TextView masseage = (TextView)layout.findViewById(R.id.studentNameDelete);
                                    masseage.setText("Do you want to delete this Homework "+hwModel.getHwName()+"?");
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
                                            deleteHw(hwModel.getHwId(),alertDialog, position);
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

         holder.readMoreHw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.des, null, false);
                TextView des = (TextView)layout.findViewById(R.id.des);
                Button closeButton = (Button)layout.findViewById(R.id.close);
                dialog.setTitle("Description");
                des.setText(hwModel.getHwDes());
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
         holder.delete.setOnClickListener(new View.OnClickListener() {

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onClick(View v)
        {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.delete, null, false);
            TextView masseage = (TextView)layout.findViewById(R.id.studentNameDelete);
            masseage.setText("Do you want to delete this Homework "+hwModel.getHwName()+"?");
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
                    deleteHw(hwModel.getHwId(),alertDialog, position);
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
            View layout = inflater.inflate(R.layout.edit_homework, null, false);
            hwName = (EditText)layout.findViewById(R.id.ehwName);
            hwDes = (EditText)layout.findViewById(R.id.ehwDes);
            hwDueDate = (EditText)layout.findViewById(R.id.ehwDate);

            hwName.setText(hwModel.getHwName());
            hwDes.setText(hwModel.getHwDes());
            hwDueDate.setText(hwModel.getHwDueDate());

            hwDueDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    hwDueDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            });

            Button editButton = (Button)layout.findViewById(R.id.editHwButton);
            Button cancelButton = (Button)layout.findViewById(R.id.cancelButton);
            dialog1.setTitle("Edit H.W");
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
                    editHw(hwModel.getHwId(),alertDialog1, position);
                }
            });
            alertDialog1.show();
        }
    });
         holder.hwName.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String pdf = URLs.URL_homeworkfile+hwModel.getHwFile();
                 Intent intent = new Intent(context, pdf.class);
                 intent.putExtra("url",pdf);
                 intent.putExtra("action","Home Work");
                 context.startActivity(intent);
             }
         });
         holder.pdf.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String pdf = URLs.URL_homeworkfile+hwModel.getHwFile();
            Intent intent = new Intent(context, pdf.class);
            intent.putExtra("url",pdf);
            intent.putExtra("action","Home Work");
            context.startActivity(intent);
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf));
//                context.startActivity(browserIntent);
        }
    });
         holder.delete.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                 LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                 View layout = inflater.inflate(R.layout.delete, null, false);
                 TextView masseage = (TextView)layout.findViewById(R.id.studentNameDelete);
                 masseage.setText("Do you want to delete this Homework "+hwModel.getHwName()+"?");
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
                         deleteHw(hwModel.getHwId(),alertDialog, position);
                     }
                 });
                 alertDialog.show();
             }
         });

    }

    private void deleteHw(String hwid, AlertDialog alertDialog, int position) {

        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_homeworkDelete, new Response.Listener<String>() {
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
                        hwModelArrayList.remove(position);
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
                data.put("id",hwid);
                Log.d(TAG, "getParams: "+data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(context).add(sr);

    }
    private void editHw(String hwid, AlertDialog alertDialog1, int position) {

        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_homeworkEdit, new Response.Listener<String>() {
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
                        hwModelArrayList.remove(position);
                        notifyItemRemoved(position);
                        notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse: " + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                name = hwName.getText().toString();
                des = hwDes.getText().toString();
                date = hwDueDate.getText().toString();
                data.put("id",hwid);
                data.put("name",name);
                data.put("des",des);
                data.put("date",date);
                Log.d(TAG, "getParams: "+data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(context).add(sr);

    }
@Override
public int getItemCount() {
        return hwModelArrayList.size();
        }
public class ViewHolder extends RecyclerView.ViewHolder {

    private TextView hwName, hwDes, hwDueDate, hwFile,hwDate,readMoreHw;
    ImageView menu,delete,edit,pdf;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        hwName = itemView.findViewById(R.id.hwName);
        hwDes = itemView.findViewById(R.id.hwDes);
        hwDueDate = itemView.findViewById(R.id.hwDueDate);
        hwFile = itemView.findViewById(R.id.hwFile);
        hwDate = itemView.findViewById(R.id.hwDate);

       // menu = itemView.findViewById(R.id.hwMenu);
        readMoreHw = itemView.findViewById(R.id.ReadMoreHw);
        delete = itemView.findViewById(R.id.deleteHw);
        edit = itemView.findViewById(R.id.editHw);
        pdf = itemView.findViewById(R.id.pdf);
    }
  }
}
