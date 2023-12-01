package com.example.alarmapp.Adapter;



import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarmapp.Base.Clock;
import com.example.alarmapp.Broadcast.Broadcast_Alarm_Receiver;
import com.example.alarmapp.Base.Alarm;
import com.example.alarmapp.Database.AlarmDataBase;
import com.example.alarmapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Alarm_Recycler_Adapter extends RecyclerView.Adapter<Alarm_Recycler_Adapter.AlarmViewHolder> {
    private List<Alarm> alarmList;
    private Context context;
    private AlarmDataBase alarmDataBase;
    private static  final String CHANNEL_ID = "remind_channel";

    public Alarm_Recycler_Adapter(Context context, List<Alarm> alarmList,AlarmDataBase dataBase) {
        this.context = context;
        this.alarmList = alarmList;
        this.alarmDataBase=dataBase;
    }
    public Alarm getAlarm(int position){
        return alarmList.get(position);
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
    public void removeAlarm(int position) {
        if (position >= 0 && position < alarmList.size()) {
            Alarm removedAlarm = alarmList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, alarmList.size());
            cancelAlarm(removedAlarm);
        }
    }

    public void addAlarm(int posititon){
        notifyItemInserted(posititon);
    }
    // Hàm huy báo thức
    public void cancelAlarm(Alarm alarm){
        Intent intent = new Intent(context, Broadcast_Alarm_Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, Integer.parseInt(alarm.getId()), intent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    /*
    * ViewHolder cho Adapter
    **/
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


        public boolean checkTurnOn(Alarm alarm){
            return alarm.getTurnOn();
        }
        public void setData(Alarm alarm) {
            if (alarm.getTime_alarm() != null && alarm.getMessage() != null) {
                tvTime.setText(alarm.getTime_alarm());
                if (checkTurnOn(alarm)) {
                    tvTime.setTextColor(ContextCompat.getColor(context, R.color.turn_on_color_alarm));
                    tvMessage.setTextColor(ContextCompat.getColor(context, R.color.turn_on_color_alarm));
                }else{
                    tvTime.setTextColor(ContextCompat.getColor(context, R.color.turn_off_color_alarm));
                    tvMessage.setTextColor(ContextCompat.getColor(context, R.color.turn_off_color_alarm));
                }
                tvMessage.setText(alarm.getMessage());
                switchCompat.setChecked(alarm.getTurnOn());
            }
        }
        public void onClick(View view) {
            // Handle item click here
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Alarm clickedAlarm = alarmList.get(position);
                // Do something with the clicked alarm, e.g., open a new activity or show a dialog
                Toast.makeText(context, "Item clicked: " + clickedAlarm.getMessage(), Toast.LENGTH_SHORT).show();
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
                    alarmDataBase.updateDatabase(alarm);
                }
            });
        }

        private void setAlarm(Alarm alarm) {
            String time = tvTime.getText().toString().trim();
            String str[] = time.split(":");
            int hour = Integer.parseInt(str[0]);
            int minute = Integer.parseInt(str[1]);

            Intent intent = new Intent(context, Broadcast_Alarm_Receiver.class);
            intent.putExtra("message", tvMessage.getText().toString().trim());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, Integer.parseInt(alarm.getId()), intent , PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
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
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, Integer.parseInt(alarm.getId()), intent, PendingIntent.FLAG_IMMUTABLE);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);
            Toast.makeText(context, "Bạn vừa hủy báo thức \n" + tvMessage.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

}
