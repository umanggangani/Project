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

public class studentNoticeAdepter extends RecyclerView.Adapter<studentNoticeAdepter.ViewHolder>  {

    private ArrayList<noticeModel> noticeModelArrayList;
    private Context context;

    public studentNoticeAdepter(ArrayList<noticeModel> noticeModelArrayList, Context context) {
        this.noticeModelArrayList = noticeModelArrayList;
        this.context = context;
    }

    @NotNull
    @Override
    public studentNoticeAdepter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_student_notice, parent, false);
        return new studentNoticeAdepter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull studentNoticeAdepter.ViewHolder holder, int position) {

        noticeModel noticeModel = noticeModelArrayList.get(position);
        holder.noticeName.setText(noticeModel.getNoticeName());
        holder.noticeDes.setText(noticeModel.getNoticeDes());
        holder.noticeDate.setText(noticeModel.getNoticeDate());

        holder.readMoreNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.des, null, false);
                TextView des = (TextView)layout.findViewById(R.id.des);
                Button closeButton = (Button)layout.findViewById(R.id.close);
                dialog.setTitle("Description");
                des.setText(noticeModel.getNoticeDes());
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
        holder.noticeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pdf = URLs.URL_noticefile+noticeModel.getNoticeFile();
                Intent intent = new Intent(context, pdf.class);
                intent.putExtra("url",pdf);
                intent.putExtra("action","Notice");
                context.startActivity(intent);
            }
        });
        holder.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pdf = URLs.URL_noticefile+noticeModel.getNoticeFile();
                Intent intent = new Intent(context, pdf.class);
                intent.putExtra("url",pdf);
                intent.putExtra("action","Notice");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noticeModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView noticeName, noticeDes, noticeDate,readMoreNotice;
        ImageView link;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noticeName = itemView.findViewById(R.id.snName);
            noticeDes = itemView.findViewById(R.id.snDes);
            noticeDate = itemView.findViewById(R.id.snDate);
            readMoreNotice = itemView.findViewById(R.id.sReadMoreNotice);
            link = itemView.findViewById(R.id.snoticeLink);
        }
    }
}
