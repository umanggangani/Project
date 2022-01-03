package com.myclass.app;

import android.content.Context;
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

public class studentMeetAdapter extends RecyclerView.Adapter<studentMeetAdapter.ViewHolder>  {

    private ArrayList<meetModel> meetModelArrayList;
    private Context context;

    public studentMeetAdapter(ArrayList<meetModel> meetModelArrayList, Context context) {
        this.meetModelArrayList = meetModelArrayList;
        this.context = context;
    }
    @NotNull
    @Override
    public studentMeetAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_student_meet, parent, false);
        return new studentMeetAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull studentMeetAdapter.ViewHolder holder, int position) {

        meetModel meetModel = meetModelArrayList.get(position);
        holder.meetName.setText(meetModel.getMeetName());
        holder.meetDes.setText(meetModel.getMeetDes());
        holder.meetDate.setText(meetModel.getMeetDate());
        holder.time.setText(meetModel.getMeetSTime()+" to "+meetModel.getMeetETime());

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
     /*   holder.meetName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             
            }
        });
        holder.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return meetModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView meetName, meetDes, meetDate,readMoreMeet,time;
        ImageView link;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            meetName = itemView.findViewById(R.id.smName);
            meetDes = itemView.findViewById(R.id.smDes);
            meetDate = itemView.findViewById(R.id.smDate);
            readMoreMeet = itemView.findViewById(R.id.sReadMoreMeet);
            link = itemView.findViewById(R.id.smeetLink);
            time = itemView.findViewById(R.id.smTime);

        }
    }
}
