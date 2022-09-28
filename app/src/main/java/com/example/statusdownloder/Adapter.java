package com.example.statusdownloder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Picture;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    Context context;
    ArrayList<Model> fileslist;

    public Adapter(Context context, ArrayList<Model> fileslist) {
        this.context = context;
        this.fileslist = fileslist;
    }

    @NonNull

    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {

        final Model model=fileslist.get(position);
        if (model.getUri().toString().endsWith(".mp4"))
        {
            holder.play.setVisibility(View.VISIBLE);
        }
        else{
            holder.play.setVisibility(View.INVISIBLE);
        }
        Glide.with(context).load(model.getUri()).into(holder.mainstatus);

        holder.mainstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.getUri().toString().endsWith(".mp4")){
                    final String path=fileslist.get(position).getPath();
                    String destpath= Environment.getExternalStorageDirectory().getAbsolutePath()+constant.SAVE_FOLDER_NAME;
                    Intent intent = new Intent(context, Video.class);
                    intent.putExtra("DEST_PATH_VIDEO",destpath);
                    intent.putExtra("FILE_VIDEO", path);
                    intent.putExtra("FILENAME_VIDEO",model.getFilename());
                    intent.putExtra("URI_VIDEO",model.getUri().toString());
                    context.startActivity(intent);

                }else {
                    final String path=fileslist.get(position).getPath();
                    String destpath= Environment.getExternalStorageDirectory().getAbsolutePath()+constant.SAVE_FOLDER_NAME;
                    Intent intent = new Intent(context, picture.class);
                    intent.putExtra("DEST_PATH",destpath);
                    intent.putExtra("FILE", path);
                    intent.putExtra("FILENAME",model.getFilename());
                    intent.putExtra("URI",model.getUri().toString());
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return fileslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mainstatus,play;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mainstatus=itemView.findViewById(R.id.thumbnailofstatus);
            play=itemView.findViewById(R.id.play);
        }
    }
}
