package com.example.alarmapp.Base;

public class Clock {
    private String city;
    private String gmt;
    private String time;

    public Clock() {
    }

    public Clock(String city, String gmt, String time) {
        this.city = city;
        this.gmt = gmt;
        this.time = time;
    }

    public Clock(String city, String time) {
        this.city = city;
        this.time = time;
    }
    public String getGmt() {
        return gmt;
    }

    public void setGmt(String gmt) {
        this.gmt = gmt;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
