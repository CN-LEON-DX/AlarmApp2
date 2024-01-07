package com.example.alarmapp.Base;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Clock implements Parcelable {

    private String city;
    private String timeDifferences;
    private String timeZone;
    private boolean isChecked=false;

    public Clock() {
    }

    public Clock( String city, String timeDifferences, String timeZone) {
        this.city = city;
        this.timeDifferences = timeDifferences;
        this.timeZone = timeZone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTimeDifferences() {
        return timeDifferences;
    }

    public void setTimeDifferences(String timeDifferences) {
        this.timeDifferences = timeDifferences;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
    public static String calculateTime(String timeZone){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
        Date currentDate =calendar.getTime();
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        dateFormat.setTimeZone(calendar.getTimeZone());
        return  dateFormat.format(currentDate);
    }
    public String getFormattedTime(int timeDifferences){
        String timeHoChiMinh = calculateTime("Asia/Ho_Chi_Minh");
        int hour = Integer.parseInt(timeHoChiMinh.substring(0,2));
        String time;
        if(timeDifferences>=0) time="+"+String.valueOf(timeDifferences);
        else time=String.valueOf(timeDifferences);
        if(hour+timeDifferences>=24)
            return "hôm sau, "+ time+" giờ";
        else if (hour+timeDifferences<=0) {
            return "hôm qua, " + time+" giờ";
            }else return "hôm nay, "+ time+" giờ";
    }
    protected Clock(Parcel in){
        city=in.readString();
        timeDifferences=in.readString();
        timeZone=in.readString();
        isChecked=in.readByte()!=0;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(city);
        dest.writeString(timeDifferences);
        dest.writeString(timeZone);
        dest.writeByte((byte) (isChecked ? 1 : 0));
    }
    public static final Creator<Clock> CREATOR = new Creator<Clock>() {
        @Override
        public Clock createFromParcel(Parcel in) {
            return new Clock(in);
        }
        @Override
        public Clock[] newArray(int size) {
            return new Clock[size];
        }
    };
}
