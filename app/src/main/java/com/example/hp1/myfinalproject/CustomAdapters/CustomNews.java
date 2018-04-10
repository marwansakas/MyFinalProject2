package com.example.hp1.myfinalproject.CustomAdapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp1.myfinalproject.JavaClasses.News;
import com.example.hp1.myfinalproject.R;

import java.util.ArrayList;


public class CustomNews extends ArrayAdapter<News> {

    //this Class has been commented all over

    TextView tvTitle,tvFirst_Sentence;
    ImageView imageView;

    public CustomNews(@NonNull Context context, ArrayList<News> news) {
        super(context, R.layout.custom_news,news);
    }

    /**
     * set all the received info in the right places
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater newsInflate=LayoutInflater.from(getContext());
        View customNewsView=newsInflate.inflate(R.layout.custom_news,parent,false);

        tvTitle=(TextView)customNewsView.findViewById(R.id.tvTitle);
        tvFirst_Sentence=(TextView)customNewsView.findViewById(R.id.tvFirst_Sentence);
        imageView=(ImageView)customNewsView.findViewById(R.id.imageView);


        String title=getItem(position).getTitle();
        String preview=getItem(position).getPreview();
        String strImage=getItem(position).getImage();

        tvTitle.setText(title);
        tvFirst_Sentence.setText(preview);
        Bitmap bitmap=StringToBitMap(strImage);
        imageView.setImageBitmap(bitmap);

        return customNewsView;
    }

    public Bitmap StringToBitMap(String encodedString){
        byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
        Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        return bitmap;
    }
}
