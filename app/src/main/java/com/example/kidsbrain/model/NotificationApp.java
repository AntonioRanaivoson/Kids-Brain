package com.example.kidsbrain.model;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class NotificationApp extends Application {
    public static final  String CHANNEL_1_ID = "channel1";
    @Override
    public void onCreate() {
        super.onCreate();
        this.createNotificationChannels();
    }
    private void createNotificationChannels()  {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Kids Brain",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("C'est Kids Brain");
            NotificationManager manager = this.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }
}
