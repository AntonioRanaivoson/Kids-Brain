package com.example.kidsbrain.activity.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kidsbrain.R;
import com.example.kidsbrain.adapter.VideoAdapter;
import com.example.kidsbrain.instance.RetrofitClientInstance;
import com.example.kidsbrain.model.Video;
import com.example.kidsbrain.service.VideoService;

import java.util.ArrayList;
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
                  Toast.makeText(getContext(), "Veuillez-vous reconnecter", Toast.LENGTH_SHORT).show();
              }
          });
        setHasOptionsMenu(true);
        SearchView searchView = (SearchView) view.findViewById(R.id.search_bar);
        TextView text = (TextView) view.findViewById(R.id.textView);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setVisibility(View.GONE);
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                text.setVisibility(View.VISIBLE);
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
        return view;
    }

    private void filter(String text) {
       List<Video> filteredlist = new ArrayList<Video>();
        for (Video item : videos) {
            if (item.getTitre().toLowerCase().contains(text.toLowerCase()) || item.getDescription().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(getContext(), "Aucun résultat..", Toast.LENGTH_SHORT).show();
        } else {
            adapter.filterList(filteredlist);
        }
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
