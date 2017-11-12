package com.example.hp1.myfinalproject;


public class HourClock {
    int hour,minute,second;

    public HourClock(){}

    public HourClock(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public String convertToString(){
        return hour+":"+minute+":"+second;
    }
}
