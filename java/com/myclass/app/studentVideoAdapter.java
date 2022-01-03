package com.myclass.app;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

import static android.content.ContentValues.TAG;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class studentVideoAdapter  extends RecyclerView.Adapter<studentVideoAdapter.ViewHolder> {
    private ArrayList<videoModel> videoModelArrayList;
    private Context context;

    public studentVideoAdapter(ArrayList<videoModel> videoModalArrayList, Context context) {
        this.videoModelArrayList = videoModalArrayList;
        this.context = context;
    }

    @NotNull
    @Override
    public studentVideoAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_student_video, parent, false);
        return new studentVideoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull studentVideoAdapter.ViewHolder holder, int position) {
        videoModel videoModel = videoModelArrayList.get(position);
        holder.videoName.setText(videoModel.getVideoName());
        holder.videoDes.setText(videoModel.getVideoDes());
        holder.videoDate.setText(videoModel.getVideoDate());
        holder.time.setText(videoModel.getVideoSTime()+" to "+videoModel.getVideoETime());

        holder.readMorevideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.des, null, false);
                TextView des = (TextView)layout.findViewById(R.id.des);
                Button closeButton = (Button)layout.findViewById(R.id.close);
                dialog.setTitle("Description");
                des.setText(videoModel.getVideoDes());
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
        holder.videoName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,video_player.class);
                intent.putExtra("link",videoModel.getVideoLink());
                context.startActivity(intent);
                Log.d(TAG, "onClick: "+ videoModel.getVideoLink());
            }
        });
        holder.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,video_player.class);
                intent.putExtra("link",videoModel.getVideoLink());
                context.startActivity(intent);
            }
    });

    }

    @Override
    public int getItemCount() {
        return videoModelArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView videoName, videoDes, videoDate,readMorevideo,time;
        ImageView link;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            videoName = itemView.findViewById(R.id.svName);
            videoDes = itemView.findViewById(R.id.svDes);
            videoDate = itemView.findViewById(R.id.svDate);
            time = itemView.findViewById(R.id.svTime);
            readMorevideo = itemView.findViewById(R.id.sReadMoreVideo);
            link = itemView.findViewById(R.id.svideoLink);
        }
    }
}
