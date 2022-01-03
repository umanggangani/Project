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

public class studentTestAdapter extends RecyclerView.Adapter<studentTestAdapter.ViewHolder> {

    private ArrayList<testModel> testModelArrayList;
    private Context context;

    public studentTestAdapter(ArrayList<testModel> testModelArrayList, Context context) {
        this.testModelArrayList = testModelArrayList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_student_test, parent, false);
        return new studentTestAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        testModel testModel = testModelArrayList.get(position);

        holder.testName.setText(testModel.getTestName());
        holder.testDes.setText(testModel.getTestDes());
        holder.testMark.setText(testModel.getTestGetMark()+"/"+testModel.getTestMark());
        holder.testDate.setText(testModel.getTestDate());
        holder.testTime.setText(testModel.getTestSTime()+" to "+testModel.getTestETime());
        holder.readMoreTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.des, null, false);
                TextView des = (TextView)layout.findViewById(R.id.des);
                Button closeButton = (Button)layout.findViewById(R.id.close);
                dialog.setTitle("Description");
                des.setText(testModel.getTestDes());
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
        holder.testName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pdf = URLs.URL_testfile+testModel.getTestFile();
                Intent intent = new Intent(context, testPdf.class);
                intent.putExtra("url",pdf);
                intent.putExtra("num",testModel.getTestNum());
                intent.putExtra("action","Test");
                intent.putExtra("id",testModel.getTestId());
                intent.putExtra("ans",testModel.getTestAns());
                intent.putExtra("user","Student");
                context.startActivity(intent);
            }
        });
        holder.viewTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pdf = URLs.URL_testfile+testModel.getTestFile();
                Intent intent = new Intent(context, testPdf.class);
                intent.putExtra("url",pdf);
                intent.putExtra("num",testModel.getTestNum());
                intent.putExtra("action","Test");
                intent.putExtra("id",testModel.getTestId());
                intent.putExtra("ans",testModel.getTestAns());
                intent.putExtra("user","Student");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return testModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView testName, testDes,testDate,testSTime,testETime,testTime,readMoreTest,testMark;
        ImageView viewTest;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            testName = itemView.findViewById(R.id.stestName);
            testDes = itemView.findViewById(R.id.stestDes);
            testDate = itemView.findViewById(R.id.stestDate);
            testMark = itemView.findViewById(R.id.stestMark);
            readMoreTest = itemView.findViewById(R.id.sReadMoreTest);
            testTime = itemView.findViewById(R.id.stestTime);
            viewTest = itemView.findViewById(R.id.stestLink);

        }
    }
}
