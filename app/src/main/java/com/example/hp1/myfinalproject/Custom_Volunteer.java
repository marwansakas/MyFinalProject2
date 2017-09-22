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

import java.util.ArrayList;

public class Custom_Volunteer extends ArrayAdapter<Rows>{

    TextView tvPlace,tvAction,tvDate,tvHourss;
    ImageView imsigneture;

    public Custom_Volunteer(Context context, ArrayList<Rows> RowsArrayList) {
        super(context,R.layout.custom_volunteer ,RowsArrayList);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater sexy=LayoutInflater.from(getContext());
        View customview=sexy.inflate(R.layout.custom_volunteer,parent,false);
        String place=getItem(position).getPlace();
        String Thingsdone=getItem(position).getAction();
        Date date=getItem(position).getDate();
        int hours=getItem(position).getHours();
        Bitmap bitmap;
        bitmap=StringToBitMap(getItem(position).bmp);
        tvPlace=(TextView) customview.findViewById(R.id.tvplace);
        tvAction=(TextView)customview.findViewById(R.id.tvAction);
        tvDate=(TextView)customview.findViewById(R.id.tvdate);
        tvHourss=(TextView)customview.findViewById(R.id.tvhours);
        imsigneture=(ImageView) customview.findViewById(R.id.imageView3);
        tvPlace.setText(place+" ");
        tvAction.setText(Thingsdone+" ");
        tvHourss.setText(hours+" ");
        tvDate.setText(date.getDay()+"/"+date.getMonth()+1+"/"+date.getYear());
        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 75, 75, true);
        imsigneture.setImageBitmap(scaled);

        return customview;
    }

    public Bitmap StringToBitMap(String encodedString){
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }

    }
