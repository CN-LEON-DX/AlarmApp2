package com.example.alarmapp.Adapter.adapter;



import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarmapp.Adapter.broadcast.Broadcast_Alarm_Receiver;
import com.example.alarmapp.Adapter.item.Alarm;
import com.example.alarmapp.R;

import java.util.Calendar;
import java.util.List;

public class Alarm_Recycler_Adapter extends RecyclerView.Adapter<Alarm_Recycler_Adapter.AlarmViewHolder> {
    private List<Alarm> alarmList;
    private Context context;
    private static  final String CHANNEL_ID = "remind_channel";

    public Alarm_Recycler_Adapter(Context context, List<Alarm> alarmList) {
        this.context = context;
        this.alarmList = alarmList;
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_alarm_recycler, parent, false);
        return new AlarmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        holder.setData(alarmList.get(position));
        holder.eventClickSwitch(context, alarmList.get(position));
    }

    @Override
    public int getItemCount() {
        return alarmList.isEmpty() ? 0 : alarmList.size();
    }

    class AlarmViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTime;
        private TextView tvMessage;
        private SwitchCompat switchCompat;


        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            switchCompat = itemView.findViewById(R.id.switch_item_alarm);
        }

        public void setData(Alarm alarm) {
            if (alarm.getTime_alarm() != null && alarm.getMessage() != null) {
                tvTime.setText(alarm.getTime_alarm());
                tvMessage.setText(alarm.getMessage());
            }
        }

        void eventClickSwitch(Context context, Alarm alarm) {
            switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        // lastest version
                        tvTime.setTextColor(ContextCompat.getColor(context, R.color.turn_on_color_alarm));
                        tvMessage.setTextColor(ContextCompat.getColor(context, R.color.turn_on_color_alarm));
                        Toast.makeText(context, "Báo thức tại " + tvTime.getText() + "\n" + tvMessage.getText(), Toast.LENGTH_SHORT).show();
                        setAlarm(alarm);
                    } else {
                        tvTime.setTextColor(ContextCompat.getColor(context, R.color.turn_off_color_alarm));
                        tvMessage.setTextColor(ContextCompat.getColor(context, R.color.turn_off_color_alarm));
                        cancelAlarm(alarm);
                    }
                }
            });
        }

        private void setAlarm(Alarm alarm) {
            // time sample: 09:08
            String time = tvTime.getText().toString().trim();
            String str[] = time.split(":");
            int hour = Integer.parseInt(str[0]);
            int minute = Integer.parseInt(str[1]);


            Intent intent = new Intent(context, Broadcast_Alarm_Receiver.class);
            intent.putExtra("message", tvMessage.getText().toString().trim());


            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarm.getId(), intent , PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);


            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);


            // thiêt lập để lặp lại hằng ngày !
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
            );

            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);


            createNotificationChannel();

        }

        private void createNotificationChannel() {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Nhắc nhở hàng ngày",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            //Toast.makeText(context, "NBotification 1", Toast.LENGTH_SHORT).show();

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }


        // Hàm huyr báo thức
        public void cancelAlarm(Alarm alarm){
            Intent intent = new Intent(context, Broadcast_Alarm_Receiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarm.getId(), intent, PendingIntent.FLAG_IMMUTABLE);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);
            Toast.makeText(context, "Bạn vừa hủy báo thức \n" + tvMessage.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
