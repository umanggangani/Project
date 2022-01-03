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

public class studentHomeWorkAdapter extends RecyclerView.Adapter<studentHomeWorkAdapter.ViewHolder>  {

    private ArrayList<hwModel> hwModelArrayList;
    private Context context;

    public studentHomeWorkAdapter(ArrayList<hwModel> hwModelArrayList, Context context) {
        this.hwModelArrayList = hwModelArrayList;
        this.context = context;
    }

    @NotNull
    @Override
    public studentHomeWorkAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_student_homework, parent, false);
        return new studentHomeWorkAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        hwModel hwModel = hwModelArrayList.get(position);
        holder.hwName.setText(hwModel.getHwName());
        holder.hwDes.setText(hwModel.getHwDes());
        holder.hwDate.setText(hwModel.getHwDate());

        holder.readMorehw.setOnClickListener(new View.OnClickListener() {
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
        holder.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pdf = URLs.URL_homeworkfile+hwModel.getHwFile();
                Intent intent = new Intent(context, pdf.class);
                intent.putExtra("url",pdf);
                intent.putExtra("action","Home Work");
                context.startActivity(intent);
            }
        });
    }
    
    @Override
    public int getItemCount() {
        return hwModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView hwName, hwDes, hwDate,readMorehw;
        ImageView link;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hwName = itemView.findViewById(R.id.shwName);
            hwDes = itemView.findViewById(R.id.shwDes);
            hwDate = itemView.findViewById(R.id.shwDate);
            readMorehw = itemView.findViewById(R.id.sReadMoreHw);
            link = itemView.findViewById(R.id.shwLink);

        }
    }
}

