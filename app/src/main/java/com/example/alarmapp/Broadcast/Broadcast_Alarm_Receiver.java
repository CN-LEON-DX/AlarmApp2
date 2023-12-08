package com.example.alarmapp.Broadcast;

import static android.os.Build.VERSION_CODES.M;
import static android.os.Build.VERSION_CODES.R;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.alarmapp.R;

public class Broadcast_Alarm_Receiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "remind_channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        createNotification(context, intent);
        String sound=intent.getStringExtra("nameSound");
        playSound(sound,context);
    }

    private void createNotification(Context context, Intent intent) {
        createNotificationChannel(context);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setSmallIcon(com.example.alarmapp.R.drawable.notification_alarm);
        builder.setContentTitle("Báo thức");
        builder.setContentText(intent.getStringExtra("message"));
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        if(intent.getBooleanExtra("isVibrate",false)) {
            builder.setVibrate(new long[]{1000, 3000});
            setVibrate(context);
        }
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1, builder.build());
    }
    private void createNotificationChannel(Context context) {
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Nhắc nhở hàng ngày",
                NotificationManager.IMPORTANCE_DEFAULT
        );
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }
    private void playSound(String nameSound, Context context){
        MediaPlayer mediaPlayer=null;
        switch (nameSound){
            case "mặc định":
                mediaPlayer = MediaPlayer.create(context, com.example.alarmapp.R.raw.sound_alarm_ring);
                break;
            case  "nhạc mặc định của điện thoại samsung":
                mediaPlayer = MediaPlayer.create(context, com.example.alarmapp.R.raw.sound_default_samsung);
                break;
            case  "nhạc gà kêu":
                mediaPlayer = MediaPlayer.create(context, com.example.alarmapp.R.raw.sound_chicken);
                break;
        }
        if (mediaPlayer != null) {
            mediaPlayer.start(); // Start playing the sound
            MediaPlayer finalMediaPlayer = mediaPlayer;
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    // Release resources after completion
                    finalMediaPlayer.release();
                }
            });
        }
    }
    @SuppressLint("ObsoleteSdkInt")
    private void setVibrate(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (vibrator != null) {
                long[] pattern = {500, 500}; // Mẫu rung: rung 500ms, nghỉ 500ms
                vibrator.vibrate(VibrationEffect.createWaveform(pattern, 0)); // Bắt đầu mẫu từ đầu
                // Lập lịch dừng rung sau 30 giây
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        vibrator.cancel(); // Dừng rung
                    }
                }, 30000); // 30 giây
            }
        } else {
            // Deprecated in API 26 (Oreo)
            if (vibrator != null) {
                long[] pattern = {500, 500}; // Mẫu rung: rung 500ms, nghỉ 500ms
                vibrator.vibrate(pattern, 0); // Bắt đầu mẫu từ đầu
                // Lập lịch dừng rung sau 30 giây
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        vibrator.cancel(); // Dừng rung
                    }
                }, 30000); // 30 giây
            }
        }
    }


}
