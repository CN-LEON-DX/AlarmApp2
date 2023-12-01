package com.example.alarmapp.Base;

public class Alarm {
    private String time_alarm;
    private int id;
    private Boolean isTurnOn =true;
    private String message;
    private String sound;
    private String repeat;
    private boolean isVibrate;
    private boolean isRepeat=false;

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
}
