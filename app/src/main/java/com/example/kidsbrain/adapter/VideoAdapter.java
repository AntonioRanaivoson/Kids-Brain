package com.example.kidsbrain.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.brightcove.player.model.DeliveryType;
import com.brightcove.player.view.BrightcoveExoPlayerVideoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.resource.bitmap.ParcelFileDescriptorBitmapDecoder;
import com.bumptech.glide.load.resource.bitmap.VideoBitmapDecoder;
import com.bumptech.glide.request.RequestOptions;
import com.example.kidsbrain.R;
import com.example.kidsbrain.activity.video.VideoPlay;
import com.example.kidsbrain.model.Video;


import java.net.URI;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.CustomViewHolder> {

    private List<Video> dataList;
    private Context context;

    public VideoAdapter(Context context,List<Video> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    public Context getContext() {
        return context;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        ImageView itemImage;
        TextView programTitle;
        TextView programDescription;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            itemImage = mView.findViewById(R.id.videoViewFlag);
            programTitle = mView.findViewById(R.id.videoTitleView);
            programDescription = mView.findViewById(R.id.videoDescView);

        }
    }
    public void filterList(List<Video> filterllist) {
        dataList = filterllist;
        notifyDataSetChanged();
    }
    @Override
    public VideoAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.video_row, parent, false);
        return new VideoAdapter.CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoAdapter.CustomViewHolder holder, int position) {
        holder.programTitle.setText(dataList.get(position).getTitre());
        holder.programDescription.setText(dataList.get(position).getDescription());
        View view = holder.mView;
        view.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(view.getContext(), VideoPlay.class);
                        Bundle b = new Bundle();
                        b.putString("url", dataList.get(holder.getAdapterPosition()).getUrl());
                        intent.putExtras(b);
                        view.getContext().startActivity(intent);
                    }
                }
        );

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.frame(7000000);
        Glide.with(context)
                .asBitmap()
                .load(dataList.get(position).getUrl())
                .thumbnail()
                .apply(requestOptions)
                .centerCrop()
                .placeholder(R.color.black)
                .into(holder.itemImage);

    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }
}
