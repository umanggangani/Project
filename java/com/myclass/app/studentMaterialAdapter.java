package com.myclass.app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class studentMaterialAdapter extends RecyclerView.Adapter<studentMaterialAdapter.ViewHolder> {

    private ArrayList<materialModel> materialModelArrayList;
    private Context context;

    public studentMaterialAdapter(ArrayList<materialModel> materialModelArrayList, Context context) {
        this.materialModelArrayList = materialModelArrayList;
        this.context = context;
    }
    
    
    @NotNull
    @Override
    public studentMaterialAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_student_material, parent, false);
        return new studentMaterialAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull studentMaterialAdapter.ViewHolder holder, int position) {
            materialModel materialModel = materialModelArrayList.get(position);
        holder.materialName.setText(materialModel.getMaterialName());
        holder.materialDes.setText(materialModel.getMaterialDes());
        holder.materialDate.setText(materialModel.getMaterialDate());

        holder.readMorematerial.setOnClickListener(new View.OnClickListener() {
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
        holder.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pdf = URLs.URL_materialfile+materialModel.getMaterialFile();
                Intent intent = new Intent(context, pdf.class);
                intent.putExtra("url",pdf);
                intent.putExtra("action","Material");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return materialModelArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView materialName, materialDes, materialDate,readMorematerial;
        ImageView link;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            materialName = itemView.findViewById(R.id.smaName);
            materialDes = itemView.findViewById(R.id.smaDes);
            materialDate = itemView.findViewById(R.id.smaDate);
            readMorematerial = itemView.findViewById(R.id.sReadMoreMaterial);
            link = itemView.findViewById(R.id.smaterialLink);

        }
    }
}
