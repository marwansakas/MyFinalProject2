package com.example.hp1.myfinalproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hp1.myfinalproject.JavaClasses.News;

import java.util.ArrayList;


public class CustomNews extends ArrayAdapter<News> {

    //this Class has been commented all over

    TextView tvTitle,tvFirst_Sentence;

    public CustomNews(@NonNull Context context, ArrayList<News> news) {
        super(context,R.layout.custom_news,news);
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

        String title=getItem(position).getTitle();
        String firstSentence=getItem(position).getFirst_Sentence();

        tvTitle.setText(title);
        tvFirst_Sentence.setText(firstSentence);

        return customNewsView;
    }
}
