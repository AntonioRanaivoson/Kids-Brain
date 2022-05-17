package com.example.kidsbrain.service;

import com.example.kidsbrain.model.Video;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface VideoService {
    @GET("video")
    Call<List<Video>> getAllVideo(@Header("Authorization") String token);
}
