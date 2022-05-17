package com.example.kidsbrain.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.kidsbrain.R;
import com.example.kidsbrain.activity.CustomAdapter;
import com.example.kidsbrain.model.Video;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

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
        Uri ur =  Uri.parse(dataList.get(position).getUrl());
        //Bitmap thumb = ThumbnailUtils.createVideoThumbnail(dataList.get(position).getUrl(), MediaStore.Video.Thumbnails.MICRO_KIND);
        //holder.itemImage.setImageBitmap(thumb);
        RequestOptions requestOptions = new RequestOptions();
        Glide.with(context)
                .asBitmap()
                .load(dataList.get(position).getUrl())
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
