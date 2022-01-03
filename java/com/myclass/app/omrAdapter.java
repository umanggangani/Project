package com.myclass.app;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class omrAdapter extends RecyclerView.Adapter<omrAdapter.ViewHolder> {
    private ArrayList<omrModel> omrModelArrayList;
    private Context context;

    private static final String TAG = "omrAdapter";

    public omrAdapter(ArrayList<omrModel> omrModalArrayList, Context context) {
        this.context = context;
        this.omrModelArrayList= omrModalArrayList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.omr, parent, false);
        return new omrAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        omrModel omrModel = omrModelArrayList.get(position);
        holder.num.setText(String.valueOf(omrModel.getNum()));

     if(omrModel.getAns().equals("0")) {
         holder.a.setChecked(false);
         holder.b.setChecked(false);
         holder.c.setChecked(false);
         holder.d.setChecked(false);
     }else if(omrModel.getAns().equals("A"))
     {
         holder.a.setChecked(true);
         holder.b.setChecked(false);
         holder.c.setChecked(false);
         holder.d.setChecked(false);
     }
     else if(omrModel.getAns().equals("B"))
     {
         holder.b.setChecked(true);
         holder.a.setChecked(false);
         holder.c.setChecked(false);
         holder.d.setChecked(false);
     }
     else if(omrModel.getAns().equals("C"))
     {
         holder.c.setChecked(true);
         holder.a.setChecked(false);
         holder.c.setChecked(false);
         holder.d.setChecked(false);
     }
     else if(omrModel.getAns().equals("D"))
     {
         holder.d.setChecked(true);
         holder.a.setChecked(false);
         holder.b.setChecked(false);
         holder.c.setChecked(false);
     }

        holder.a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+omrModel.getNum()+holder.a.getText().toString());
                holder.b.setChecked(false);
                holder.c.setChecked(false);
                holder.d.setChecked(false);
                omrModel.setAns("A");
            }
        });
        holder.b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+omrModel.getNum()+holder.b.getText().toString());
                holder.a.setChecked(false);
                holder.c.setChecked(false);
                holder.d.setChecked(false);
                omrModel.setAns("B");
            }
        });
        holder.c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+omrModel.getNum()+holder.c.getText().toString());
                holder.a.setChecked(false);
                holder.b.setChecked(false);
                holder.d.setChecked(false);
                omrModel.setAns("C");
            }
        });
        holder.d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+omrModel.getNum()+holder.d.getText().toString());
                holder.a.setChecked(false);
                holder.b.setChecked(false);
                holder.c.setChecked(false);
                omrModel.setAns("D");
            }
        });

    }

    @Override
    public int getItemCount() {
        return omrModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RadioGroup rg;
        RadioButton a,b,c,d;
        TextView num;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            num = itemView.findViewById(R.id.num);
            rg = itemView.findViewById(R.id.rg);
            a = itemView.findViewById(R.id.a);
            b = itemView.findViewById(R.id.b);
            c = itemView.findViewById(R.id.c);
            d = itemView.findViewById(R.id.d);

        }
    }
}
