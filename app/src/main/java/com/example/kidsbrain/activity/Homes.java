package com.example.kidsbrain.activity;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.kidsbrain.R;
import com.example.kidsbrain.model.NotificationApp;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Homes extends AppCompatActivity {
    //This will be used to switch between Fragments
    private BottomNavigationView bottomNavView;
    private SharedPreferences sharedPreferences;
    private NotificationManagerCompat notificationManagerCompat;
    private  Notification notification ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homes);
        ActionBar bar= getSupportActionBar();
        bar.setTitle("Kids Brain");
        bar.show();
        getSupportActionBar().hide();
        sharedPreferences= this.getSharedPreferences("userIdentity", Context.MODE_PRIVATE);
        //Initialize the BottomNavigationView and add listener to it
        bottomNavView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavView, navController);
        notificationManagerCompat = NotificationManagerCompat.from(this);
        sendOnChannel1(  );
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
    private void sendOnChannel1()  {
        notification = new NotificationCompat.Builder(this, NotificationApp.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.exo_notification_small_icon)
                .setContentTitle("Kids Brain")
                .setContentText("En cours...")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setOngoing(true)
                .build();
        int notificationId = 1;
        notificationManagerCompat.notify(notificationId, notification);
    }
}