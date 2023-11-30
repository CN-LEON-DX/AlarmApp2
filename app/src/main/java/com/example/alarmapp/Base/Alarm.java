package com.example.alarmapp.Base;

public class Alarm {
    private String time_alarm;
    private String id;
    private Boolean isTurnOn =true;
    private String message;

    public Alarm(String id, String time_alarm, Boolean isTurnOn, String message) {
        this.time_alarm = time_alarm;
        this.id = id;
        this.isTurnOn = isTurnOn;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getTurnOn() {
        return isTurnOn;
    }

    public void setTurnOn(Boolean turnOn) {
        isTurnOn = turnOn;
    }
}
