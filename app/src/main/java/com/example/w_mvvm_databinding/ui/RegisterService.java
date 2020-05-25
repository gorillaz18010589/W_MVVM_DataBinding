package com.example.w_mvvm_databinding.ui;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.w_mvvm_databinding.R;

public class RegisterService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.v("hank","onCreate()");
        createRegisterNotification();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("hank","onStartCommand()");

        return super.onStartCommand(intent, flags, startId);
    }

    public void createRegisterNotification(){

      Notification notification = new NotificationCompat.Builder(this,"registerChannelId")
              .setContentTitle("註冊信件已啟動")
              .setContentText("請開啟APP")
              .build();

      startForeground(1,notification);

      if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("registerChannelId", "頻道名字", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager =  getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
      }


      Log.v("hank","createRegisterNotification()");

    }

    @Override
    public void onDestroy() {
        Log.v("hank","onDestroy()");
        super.onDestroy();
    }
}
