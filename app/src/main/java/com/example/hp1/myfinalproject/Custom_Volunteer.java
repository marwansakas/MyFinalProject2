package com.example.hp1.myfinalproject;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp1.myfinalproject.JavaClasses.Date;
import com.example.hp1.myfinalproject.JavaClasses.VolunteerHours;

import java.util.ArrayList;

public class Custom_Volunteer extends ArrayAdapter<VolunteerHours>{

    //this Class has been commented all over

    TextView tvPlace,tvAction,tvDate,tvHourss;
    ImageView imsigneture;

    public Custom_Volunteer(Context context, ArrayList<VolunteerHours> volunteerHoursArrayList) {
        super(context,R.layout.custom_volunteer , volunteerHoursArrayList);
    }

    /**
     * set all the received info in the right places
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater sexy=LayoutInflater.from(getContext());
        View customview=sexy.inflate(R.layout.custom_volunteer,parent,false);

        tvPlace=(TextView) customview.findViewById(R.id.tvplace);//initialize tvPlace
        tvAction=(TextView)customview.findViewById(R.id.tvAction);//initialize tvAction
        tvDate=(TextView)customview.findViewById(R.id.tvdate);//initialize date
        tvHourss=(TextView)customview.findViewById(R.id.tvhours);//initialize tvHours
        imsigneture=(ImageView) customview.findViewById(R.id.imageView3);//initialize image signnturexd

        String place=getItem(position).getPlace();//get the place
        String Thingsdone=getItem(position).getAction();//get the Action
        Date date=getItem(position).getDate();//git the date
        int hours=getItem(position).getHours();//get the number of ours
        Bitmap bitmap;
        bitmap=StringToBitMap(getItem(position).getBmp());//convert String to bitmap

        tvPlace.setText(place+" ");//show Place
        tvAction.setText(Thingsdone+" ");//Show Action
        tvHourss.setText(hours+" ");//Show hours
        tvDate.setText(date.getDay()+"/"+date.getMonth()+"/"+date.getYear());//show date
        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 75, 75, true);////create bitmap
        imsigneture.setImageBitmap(scaled);//show bitmap in imageView

        return customview;//return the result of the row in the custom listview
    }

    /**
     *  converts String to bitmap
     * @param encodedString the String from firbase that ws once a bitmap
     * @return
     */
    public Bitmap StringToBitMap(String encodedString){
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }



    }
