package com.example.alarmapp.Base;

public class StopWatch {
    private String indexOf, timeRecord, timeAdd;

    public StopWatch(String indexOf, String timeRecord, String timeAdd) {
        this.indexOf = indexOf;
        this.timeRecord = timeRecord;
        this.timeAdd = timeAdd;
    }

    public StopWatch() {
    }

    public String getIndexOf() {
        return indexOf;
    }

    public void setIndexOf(String indexOf) {
        this.indexOf = indexOf;
    }

    public String getTimeRecord() {
        return timeRecord;
    }

    public void setTimeRecord(String timeRecord) {
        this.timeRecord = timeRecord;
    }

    public String getTimeAdd() {
        return timeAdd;
    }

    public void setTimeAdd(String timeAdd) {
        this.timeAdd = timeAdd;
    }
}
