package com.myclass.app;

import android.content.ContentValues;
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

public class materialAdapter extends RecyclerView.Adapter<materialAdapter.ViewHolder> {

    private ArrayList<materialModel> materialModelArrayList;
    private Context context;
    OnDeleteItemListener listener;
    String name,des;
    EditText materialName,materialDes;

    public materialAdapter(ArrayList<materialModel> materialModelArrayList, Context context,
                           OnDeleteItemListener listener) {
        this.materialModelArrayList = materialModelArrayList;
        this.context = context;
        this.listener = listener;
    }
    @NonNull
    @Override
    public materialAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout file on below line.
        View view = LayoutInflater.from(context).inflate(R.layout.card_material, parent, false);
        return new materialAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull materialAdapter.ViewHolder holder, int position) {

        materialModel materialModel = materialModelArrayList.get(position);
        holder.materialName.setText(materialModel.getMaterialName());
        holder.materialDes.setText(materialModel.getMaterialDes());
      //  holder.materialFile.setText(materialModel.getMaterialFile());

      /*  holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context,holder.menu);
                popupMenu.inflate(R.menu.hw_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.pdf:
                                String pdf = URLs.URL_materialfile+materialModel.getMaterialFile();
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf));
                                context.startActivity(browserIntent);
                                break;
                            case R.id.editHw:
                                AlertDialog.Builder dialog1 = new AlertDialog.Builder(context);
                                LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
                                View layout = inflater.inflate(R.layout.edit_material, null, false);
                                materialName = (EditText)layout.findViewById(R.id.ematerialName);
                                materialDes = (EditText)layout.findViewById(R.id.ematerialDes);

                                materialName.setText(materialModel.getMaterialName());
                                materialDes.setText(materialModel.getMaterialDes());

                                Button editButton = (Button)layout.findViewById(R.id.editHwButton);
                                Button cancelButton = (Button)layout.findViewById(R.id.cancelButton);
                                dialog1.setTitle("Edit Material");
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
                                        editMaterial(materialModel.getMaterialId(),alertDialog1, position);
                                    }
                                });
                                alertDialog1.show();
                                break;
                            case R.id.deleteHw:
                                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                                inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                                layout = inflater.inflate(R.layout.delete, null, false);
                                TextView masseage = (TextView)layout.findViewById(R.id.studentNameDelete);
                                masseage.setText("Do you want to delete this Material"+materialModel.getMaterialName()+"?");
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
                                        deleteMaterial(materialModel.getMaterialId(),alertDialog, position);
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
        holder.readMoreMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.des, null, false);
                TextView des = (TextView)layout.findViewById(R.id.des);
                Button closeButton = (Button)layout.findViewById(R.id.close);
                dialog.setTitle("Description");
                des.setText(materialModel.getMaterialDes());
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
        holder.materialName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pdf = URLs.URL_materialfile+materialModel.getMaterialFile();
                Intent intent = new Intent(context, pdf.class);
                intent.putExtra("url",pdf);
                intent.putExtra("action","Material");
                context.startActivity(intent);
            }
        });
        holder.materialFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pdf = URLs.URL_materialfile+materialModel.getMaterialFile();
                Intent intent = new Intent(context, pdf.class);
                intent.putExtra("url",pdf);
                intent.putExtra("action","Material");
                context.startActivity(intent);
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf));
//                context.startActivity(browserIntent);
            }
        });
        holder.editMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog1 = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.edit_material, null, false);
                materialName = (EditText)layout.findViewById(R.id.ematerialName);
                materialDes = (EditText)layout.findViewById(R.id.ematerialDes);

                materialName.setText(materialModel.getMaterialName());
                materialDes.setText(materialModel.getMaterialDes());

                Button editButton = (Button)layout.findViewById(R.id.editHwButton);
                Button cancelButton = (Button)layout.findViewById(R.id.cancelButton);
                dialog1.setTitle("Edit Material");
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
                        editMaterial(materialModel.getMaterialId(),alertDialog1, position);
                    }
                });
                alertDialog1.show();
            }
        });
        holder.deleteMaterial.setOnClickListener(new View.OnClickListener() {
            String materialid = materialModel.getMaterialId();

            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.delete, null, false);
                TextView masseage = (TextView)layout.findViewById(R.id.studentNameDelete);
                masseage.setText("Do you want to delete this material "+materialModel.getMaterialName()+"?");
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
                        deleteMaterial(materialid,alertDialog,position);
                    }
                });
                alertDialog.show();
            }

        });

    }

    private void editMaterial(String materialId, AlertDialog alertDialog1, int position) {
        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_materialEdit, new Response.Listener<String>() {
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
                        materialModelArrayList.remove(position);
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

                name = materialName.getText().toString();
                des = materialDes.getText().toString();

                data.put("id",materialId);
                data.put("name",name);
                data.put("des",des);
                Log.d(TAG, "getParams: "+data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(context).add(sr);

    }

    private void deleteMaterial(String materialId, AlertDialog alertDialog, int position) {

        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_materialDelete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("TAG", "onResponse: " + response);
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("message");
                    if (message.equals("success")) {
                        Log.d(ContentValues.TAG, "onResponse: material deleted");
                        alertDialog.dismiss();

                        listener.delete();
                        materialModelArrayList.remove(position);
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
                data.put("id",materialId);
                Log.d(ContentValues.TAG, "getParams: "+data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(context).add(sr);
    }

    @Override
    public int getItemCount() {
        return materialModelArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView materialName, materialDes,readMoreMaterial;
        ImageView deleteMaterial,editMaterial,menu,materialFile;
        LinearLayout materialView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             materialName = itemView.findViewById(R.id.maName);
             materialDes = itemView.findViewById(R.id.maDes);
           materialFile = itemView.findViewById(R.id.pdf);
          //   menu = itemView.findViewById(R.id.materialMenu);
             readMoreMaterial = itemView.findViewById(R.id.ReadMoreMa);
            deleteMaterial = itemView.findViewById(R.id.Delete);
            editMaterial = itemView.findViewById(R.id.Update);
//            materialView = itemView.findViewById(R.id.materialView);

        }
    }
}
