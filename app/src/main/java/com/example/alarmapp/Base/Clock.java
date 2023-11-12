package com.example.alarmapp.Base;

public class Clock {
    int id;
    private String city;
    private String timeDifferences;
    private String timeZone;

    public Clock() {
    }

    public Clock(int id, String city, String timeDifferences, String timeZone) {
        this.id = id;
        this.city = city;
        this.timeDifferences = timeDifferences;
        this.timeZone = timeZone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
