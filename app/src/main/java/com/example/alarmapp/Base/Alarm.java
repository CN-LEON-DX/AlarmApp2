package com.example.alarmapp.Base;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.alarmapp.Broadcast.Broadcast_Alarm_Receiver;

import java.util.Calendar;

public class Alarm implements Parcelable {
    private String time_alarm;
    private int id;
    private Boolean isTurnOn ;
    private String message;
    private String sound;
    private String repeat;
    private boolean isVibrate;
    private boolean isRepeat ;
    private boolean isChecked=false;

    public Alarm(String time_alarm, int id, Boolean isTurnOn, String message, String sound, String repeat, boolean isVibrate, boolean isRepeat) {
        this.time_alarm = time_alarm;
        this.id = id;
        this.isTurnOn = isTurnOn;
        this.message = message;
        this.sound = sound;
        this.repeat = repeat;
        this.isVibrate = isVibrate;
        this.isRepeat = isRepeat;
    }

    public String getTime_alarm() {
        return time_alarm;
    }

    public void setTime_alarm(String time_alarm) {
        this.time_alarm = time_alarm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getTurnOn() {
        return isTurnOn;
    }

    public void setTurnOn(Boolean turnOn) {
        isTurnOn = turnOn;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public boolean isVibrate() {
        return isVibrate;
    }

    public void setVibrate(boolean vibrate) {
        isVibrate = vibrate;
    }

    public boolean isRepeat() {
        return isRepeat;
    }

    public void setRepeat(boolean repeat) {
        isRepeat = repeat;
    }
    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public void createAlarm(Context context) {
        if(this.isTurnOn) {
            //get time
            String[] timeArray = this.time_alarm.split(":");
            int hour = Integer.parseInt(timeArray[0]);
            int minute = Integer.parseInt(timeArray[1]);
            //call Broadcast
            Intent intent = new Intent(context, Broadcast_Alarm_Receiver.class);
            intent.putExtra("message", this.message + " " + this.time_alarm);
            intent.putExtra("nameSound",this.sound);
            intent.putExtra("isVibrate",this.isVibrate);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, this.id, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            /* set  alarm */
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);
            setNextDay(calendar);
            //set repeat alarm

            // set repeat alarm
            if (this.repeat != null) { // Check if repeat is not null
                String[] repeatAlarm = this.repeat.split(", ");
                for (int i = 0; i < repeatAlarm.length; ++i) {
                    setRepeatAlarm(repeatAlarm[i], alarmManager, calendar, pendingIntent);
                }
            }else Toast.makeText(context,"repeat null", Toast.LENGTH_SHORT).show();
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }
    private void setNextDay(Calendar calendar){
        Calendar currentTime=Calendar.getInstance();
        if (currentTime.after(calendar)) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }
    private void setRepeat(AlarmManager alarmManager,Calendar calendar,PendingIntent pendingIntent){
        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY*7,
                pendingIntent
        );
    }
    private void setRepeatAlarm(String day,AlarmManager alarmManager,Calendar calendar,PendingIntent pendingIntent){
        switch (day){
            case "mỗi ngày":
                alarmManager.setRepeating(
                        AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY,
                        pendingIntent
                );
                break;
            case "chỉ một lần":
                break;
            case "thứ 2":
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                setRepeat(alarmManager,calendar,pendingIntent);
                break;
            case "thứ 3":
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                setRepeat(alarmManager,calendar,pendingIntent);
                break;
            case "thứ 4":
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                setRepeat(alarmManager,calendar,pendingIntent);
                break;
            case "thứ 5":
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                setRepeat(alarmManager,calendar,pendingIntent);
                break;
            case "thứ 6":
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                setRepeat(alarmManager,calendar,pendingIntent);
                break;
            case "thứ 7":
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                setRepeat(alarmManager,calendar,pendingIntent);
                break;
            case "chủ nhật":
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                setRepeat(alarmManager,calendar,pendingIntent);
                break;
        }
    }
    public void cancelAlarm(Context context){
        Intent intent = new Intent(context, Broadcast_Alarm_Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, this.id, intent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
    protected Alarm(Parcel in) {
        time_alarm = in.readString();
        id = in.readInt();
        isTurnOn = in.readByte() != 0;
        message = in.readString();
        sound = in.readString();
        repeat = in.readString();
        isVibrate = in.readByte() != 0;
        isRepeat = in.readByte() != 0;
        isChecked = in.readByte() !=0;
    }
    public static final Creator<Alarm> CREATOR = new Creator<Alarm>() {
        @Override
        public Alarm createFromParcel(Parcel in) {
            return new Alarm(in);
        }

        @Override
        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(time_alarm);
        dest.writeInt(id);
        dest.writeByte((byte) (isTurnOn ? 1 : 0));
        dest.writeString(message);
        dest.writeString(sound);
        dest.writeString(repeat);
        dest.writeByte((byte) (isVibrate ? 1 : 0));
        dest.writeByte((byte) (isRepeat ? 1 : 0));
        dest.writeByte((byte) (isChecked? 1 : 0));
    }
}
