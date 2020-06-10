package com.example.vseek.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vseek.R;
import com.example.vseek.models.Video;

import java.util.ArrayList;

public class ThumbnailListAdapter extends RecyclerView.Adapter<ThumbnailListAdapter.ViewHolder> {

    ArrayList<Video> videoArrayList;
    Context context;
    OnThumbnailClickListener onThumbnailClickListener;
    public ThumbnailListAdapter(ArrayList<Video> videoArrayList, Context context, OnThumbnailClickListener onThumbnailClickListener){
        this.videoArrayList=videoArrayList;
        this.context=context;
        this.onThumbnailClickListener=onThumbnailClickListener;
    }

    @NonNull
    @Override
    public ThumbnailListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.thumbnail_list_item,parent,false);
        ViewHolder vh=new ViewHolder(view,onThumbnailClickListener);
        return vh;    }

    @Override
    public void onBindViewHolder(@NonNull ThumbnailListAdapter.ViewHolder holder, int position) {



        holder.setView(position);

    }

    @Override
    public int getItemCount() {
        return videoArrayList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView imageName;
        ImageView thumbnail;
        OnThumbnailClickListener onThumbnailClickListener;

        public ViewHolder(@NonNull View itemView,OnThumbnailClickListener onThumbnailClickListener) {
            super(itemView);
            //imageName=itemView.findViewById(R.id.image_name);
            thumbnail=itemView.findViewById(R.id.thumbnail);
            itemView.setOnClickListener(this);
            this.onThumbnailClickListener=onThumbnailClickListener;
        }

        public void setView(int position){
            //imageName.setText(videoArrayList.get(position).getName());
            Glide.with(context)
                    .asBitmap().fitCenter().centerCrop()
                    .load(videoArrayList.get(position).getUri()) // or URI/path
                    .into(thumbnail);

        }

        @Override
        public void onClick(View view) {
            this.onThumbnailClickListener.onThumbnailClick(getAdapterPosition());
        }

    }

    public interface OnThumbnailClickListener{

        void onThumbnailClick(int position);
    }


}
