package com.example.kidsbrain.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.kidsbrain.R;

public class Homes extends AppCompatActivity {
    //This will be used to switch between Fragments
    private BottomNavigationView bottomNavView;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homes);
        ActionBar bar= getSupportActionBar();
        bar.setTitle("Kid Brain");
        bar.show();
        sharedPreferences= this.getSharedPreferences("userIdentity", Context.MODE_PRIVATE);
        //Initialize the BottomNavigationView and add listener to it
        bottomNavView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavView, navController);
    }
    @Override
    protected void onPostResume(){
        super.onPostResume();
        if(sharedPreferences!=null){
            String token =  sharedPreferences.getString("token","");
            if(token==""){
                startActivity(new Intent(Homes.this,Login.class));
            }
        }
    }
}