package com.example.kidsbrain.service;

import com.example.kidsbrain.model.AuthenticationResponse;
import com.example.kidsbrain.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface UserService {

    @POST("user/login")
    public Call<AuthenticationResponse> login(@Body User user);

    @POST("user/signup")
    public Call<AuthenticationResponse> signUp(@Body User user);
}
