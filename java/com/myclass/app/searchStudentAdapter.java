package com.myclass.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class searchStudentAdapter extends RecyclerView.Adapter<searchStudentAdapter.ViewHolder> {

    private ArrayList<studentModel> studentModelArrayList;
    private Context context;

    public searchStudentAdapter(ArrayList<studentModel> studentModelArrayList, Context context) {
        this.studentModelArrayList = studentModelArrayList;
        this.context = context;
    }

    @NotNull
    @Override
    public searchStudentAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_student_search, parent, false);
        return new searchStudentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull searchStudentAdapter.ViewHolder holder, int position) {

        studentModel studentModel = studentModelArrayList.get(position);
        // on below line we are setting data to our text view.
        holder.studentName.setText(studentModel.getStudentName());
        holder.studentCont.setText(studentModel.getStudentCont());
        holder.studentEmail.setText(studentModel.getStudentEmail());
    }

    @Override
    public int getItemCount() {
        return studentModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView studentName, studentCont, studentEmail;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.sName);
            studentCont = itemView.findViewById(R.id.sCont);
            studentEmail = itemView.findViewById(R.id.sEmail);
        }
    }
}
