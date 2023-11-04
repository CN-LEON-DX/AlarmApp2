package com.example.alarmapp.Model.adapter.item;

public class Clock {
    private String city;
    private String today;
    private String time;

    public Clock(String city, String time) {
        this.city = city;
        this.time = time;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
