package com.myclass.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class studentClassNameAdapter extends RecyclerView.Adapter<studentClassNameAdapter.ViewHolder> {

    private ArrayList<studentClassModel> studentClassModelArrayList;
    private Context context;
    private StudentOnClassSelectListener listener;
    SharedPreferences sharedPreferences;
    boolean selectClass = true;
    private int lastCheckedPos = 0;

    public studentClassNameAdapter(ArrayList<studentClassModel> studentClassModelArrayList, Context context,StudentOnClassSelectListener listener) {
        this.studentClassModelArrayList = studentClassModelArrayList;
        this.context = context;
        this.listener = listener;
    }

    @NotNull
    @Override
    public studentClassNameAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_student_class, parent, false);
        return new studentClassNameAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull studentClassNameAdapter.ViewHolder holder, int position) {

        studentClassModel studentClassModel = studentClassModelArrayList.get(position);
        holder.className.setText(studentClassModel.getStudentClassName());
        holder.teacherName.setText(studentClassModel.getStudentTeacherName());
        if (selectClass) {
            if(position == 0) {
                listener.selectClassName(studentClassModel.getStudentClassName());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("c_id",studentClassModel.getStudentClassId());
                editor.putString("className",studentClassModel.getStudentClassName());
                editor.commit();
            }
        }
        if(lastCheckedPos == position){
            holder.classId.setCardBackgroundColor(Color.parseColor("#0099ff"));
            holder.teacherName.setTextColor(Color.parseColor("#ffffff"));
        } else {
            holder.classId.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.teacherName.setTextColor(Color.parseColor("#3F51B5"));
        }
        holder.classId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("c_id",studentClassModel.getStudentClassId());
                editor.putString("className",studentClassModel.getStudentClassName());
                editor.commit();
                listener.selectClassName(studentClassModel.getStudentClassName());
                lastCheckedPos = position;
                selectClass = false;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentClassModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView className,teacherName;
        CardView classId;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            className =itemView.findViewById(R.id.sClassName);
            teacherName = itemView.findViewById(R.id.sClassTeacherName);
            classId = itemView.findViewById(R.id.changeClassId);
        }
    }
}
