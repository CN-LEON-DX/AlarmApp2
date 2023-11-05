package com.example.alarmapp.Model.item;

public class Clock {
    private String country;
    private String gmt;
    private String time;

    public Clock(String country, String gmt, String time) {
        this.country = country;
        this.gmt = gmt;
        this.time = time;
    }

    public String getGmt() {
        return gmt;
    }

    public void setGmt(String gmt) {
        this.gmt = gmt;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
