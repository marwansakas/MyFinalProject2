package com.example.hp1.myfinalproject.JavaClasses;


import com.example.hp1.myfinalproject.JavaClasses.Date;

public class VolunteerHours {
    private String place,action;
    private int hours;
    private Date date;
    private String bmp;

    public VolunteerHours(){}

    public VolunteerHours(String place, String action, int hours, Date date, String bmp) {
        this.place = place;
        this.action = action;
        this.hours = hours;
        this.date = date;
        this.bmp = bmp;
    }



    public String getPlace() {
        return place;
    }

    public String getAction() {
        return action;
    }

    public int getHours() {
        return hours;
    }

    public Date getDate() {
        return date;
    }

    public String getBmp() {
        return bmp;
    }

    @Override
    public String toString() {
        return super.toString();
    }


}
