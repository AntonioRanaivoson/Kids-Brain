package com.example.kidsbrain.service;

import com.example.kidsbrain.model.AuthenticationResponse;
import com.example.kidsbrain.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface UserService {

    @POST("user/login")
    public Call<AuthenticationResponse> login(@Body User user);

    @POST("user/signup")
    public Call<AuthenticationResponse> signUp(@Body User user);

    @GET("user/{userId}")
    public Call<User> getUserConnected(@Path("userId") String login, @Header("Authorization") String token);
}
