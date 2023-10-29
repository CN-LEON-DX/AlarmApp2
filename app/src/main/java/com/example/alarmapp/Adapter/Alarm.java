package com.example.alarmapp.Adapter;

public class Alarm {
    private String time_alarm;
    private int id;
    private String message;

    public Alarm(String time_alarm, int id, String message) {
        this.time_alarm = time_alarm;
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
