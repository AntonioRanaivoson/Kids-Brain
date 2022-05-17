package com.example.kidsbrain.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kidsbrain.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        SharedPreferences sharedPreferences= this.getSharedPreferences("userIdentity", Context.MODE_PRIVATE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sharedPreferences!=null){
                    String token =  sharedPreferences.getString("token","");
                    if(token!=""){
                        startActivity(new Intent(MainActivity.this,Homes.class));
                    }else{
                        startActivity(new Intent(MainActivity.this,Login.class));
                    }
                }
                finish();
            }
        },3000);

    }
}