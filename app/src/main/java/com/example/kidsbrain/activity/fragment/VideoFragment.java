package com.example.kidsbrain.activity.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kidsbrain.R;
import com.example.kidsbrain.activity.CustomAdapter;
import com.example.kidsbrain.activity.TestRetro;
import com.example.kidsbrain.adapter.VideoAdapter;
import com.example.kidsbrain.instance.RetrofitClientInstance;
import com.example.kidsbrain.model.RetroPhoto;
import com.example.kidsbrain.model.Video;
import com.example.kidsbrain.service.GetDataService;
import com.example.kidsbrain.service.UserService;
import com.example.kidsbrain.service.VideoService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoFragment extends Fragment {
    private VideoAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDoalog;
    SharedPreferences sharedPreferences;
    String token;
    List<Video> videos;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getContext().getSharedPreferences("userIdentity", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token","");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        /*Create handle for the RetrofitInstance interface*/
        VideoService service = RetrofitClientInstance.getRetrofitInstance().create(VideoService.class);
          progressDoalog = new ProgressDialog(getContext());
          progressDoalog.setMessage("Chargement....");
          progressDoalog.show();
          service.getAllVideo("Bearer " + token).enqueue(new Callback<List<Video>>() {
              @Override
              public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {
                  progressDoalog.dismiss();
                  videos = response.body();
                  generateDataList(videos, view);
              }

              @Override
              public void onFailure(Call<List<Video>> call, Throwable t) {
                  progressDoalog.dismiss();
                  Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
              }
          });

        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(List<Video> videoList,View view) {
        recyclerView = view.findViewById(R.id.videoRecyclerView);
        adapter = new VideoAdapter(getContext(),videoList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getLayoutDirection());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
}
