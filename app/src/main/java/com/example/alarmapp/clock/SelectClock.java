package com.example.alarmapp.clock;

public class SelectClock {
    private String country;
    private String hour,minute;
    private String gmt;

    public SelectClock(String country, String hour, String minute, String gmt) {
        this.country = country;
        this.hour = hour;
        this.minute = minute;
        this.gmt = gmt;
    }
    public SelectClock(){
        this.country="Hà Nội";
        this.hour="00";
        this.minute="00";
        this.gmt="GMT +7:00";
    }
    public String getCountry() {
        return country;
    }

    public String getHour() {
        return hour;
    }

    public String getMinute() {
        return minute;
    }

    public String getGmt() {
        return gmt;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public void setGmt(String gmt) {
        this.gmt = gmt;
    }
}
