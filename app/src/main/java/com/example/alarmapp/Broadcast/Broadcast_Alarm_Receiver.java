package com.example.alarmapp.Broadcast;

import static android.os.Build.VERSION_CODES.R;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.alarmapp.R;

public class Broadcast_Alarm_Receiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "remind_channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(com.example.alarmapp.R.drawable.notification_alarm)
                .setContentTitle("Bao thuc")
                .setContentText(intent.getStringExtra("message"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1, builder.build());
    }
}
