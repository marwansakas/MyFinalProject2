package com.example.hp1.myfinalproject;


public class CalendarEventDetails {

    String details;
    Date date;

    public CalendarEventDetails() {}

    public CalendarEventDetails(String details, Date date) {

        this.details = details;
        this.date = date;
    }


    public void setDetails(String details) {
        this.details = details;
    }

    public String getDetails() {

        return details;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {

        return date;
    }


    public String returnDate() {
        return date.getYear()+"/"+date.getMonth()+"/"+date.getDay();
    }
}
