package com.example.hp1.myfinalproject;


public class CalendarEventDetails {

    String details;
    Date date;
    HourClock hourClock;

    public CalendarEventDetails() {
    }

    public CalendarEventDetails( Date date, HourClock hourClock,String details) {
        this.details = details;
        this.date = date;
        this.hourClock = hourClock;
    }

    public String getDetails() {
        return details;
    }

    public Date getDate() {
        return date;
    }

    public HourClock getHourClock() {
        return hourClock;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setHourClock(HourClock hourClock) {
        this.hourClock = hourClock;
    }
}
