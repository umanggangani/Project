package com.myclass.app;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class classNameAdapter extends RecyclerView.Adapter<classNameAdapter.ViewHolder> {

    private ArrayList<Classes> classesArrayList;
    private Context context;
    private OnClassSelectListener listener;
    private int lastCheckedPos = 0;
    boolean selectClass = true;

    public classNameAdapter(ArrayList<Classes> classesArrayList, Context context,OnClassSelectListener listener) {
        this.classesArrayList = classesArrayList;
        this.context = context;
        this.listener =listener;
    }
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.class_name, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Classes classes = classesArrayList.get(position);
        holder.className.setText(classes.name);

        if (selectClass) {
            if(position == 0) {
                listener.selectClass(classes.c_id);
            }
        }

        if(lastCheckedPos == position){
            holder.classCard.setCardBackgroundColor(Color.parseColor("#0099ff"));
        } else {
            holder.classCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        holder.classCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.selectClass(classes.getC_id());
                lastCheckedPos = position;
                Log.d(TAG, "onClick: " + classes.getC_id());
                notifyDataSetChanged();
                selectClass = false;
            }
        });
    }
    @Override
    public int getItemCount() {
        return classesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView className;
        CardView classCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            classCard = itemView.findViewById(R.id.classCard);
            className = itemView.findViewById(R.id.classNameList);
        }
    }
}
