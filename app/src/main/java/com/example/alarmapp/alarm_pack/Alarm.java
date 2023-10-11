package com.example.alarmapp.alarm_pack;

public class Alarm {
    private String time_alarm;
    private String message;

    public Alarm(String time_alarm, String message) {
        this.time_alarm = time_alarm;
        this.message = message;
    }

    public Alarm() {
        time_alarm = "0";
        message = "Báo thức";
    }

    public String getTime_alarm() {
        return time_alarm;
    }

    public void setTime_alarm(String time_alarm) {
        this.time_alarm = time_alarm;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
